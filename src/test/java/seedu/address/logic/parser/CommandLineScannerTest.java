package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;

import seedu.address.commons.util.SubstringRange;

public class CommandLineScannerTest {

    @Test
    public void constructor() {
        Collection<String> flagPrefixes = Arrays.asList("a", "b");
        CommandLineScanner scanner = new CommandLineScanner("hello world!", flagPrefixes);
        assertEquals("hello world!", scanner.getInput());
        assertEquals(flagPrefixes, scanner.getFlagPrefixes());
        assertEquals(0, scanner.getInputPosition());
        assertEquals("hello world!", scanner.getRemainingInput());
    }

    @Test
    public void copyConstructor() {
        Collection<String> flagPrefixes = Arrays.asList("c", "d");
        CommandLineScanner scanner = new CommandLineScanner("hello world!", flagPrefixes);
        CommandLineScanner copy = new CommandLineScanner(scanner);
        assertEquals("hello world!", copy.getInput());
        assertEquals(flagPrefixes, scanner.getFlagPrefixes());
        assertEquals(0, copy.getInputPosition());
        assertEquals("hello world!", copy.getRemainingInput());
    }

    @Test
    public void nextUnquotedString_skipsWhitespace() {
        CommandLineScanner scanner = new CommandLineScanner(" \t\r\n\"pineapple\" \t\r\n");
        assertEquals(Optional.of("\"pineapple\""), scanner.nextUnquotedString());
        assertEquals(15, scanner.getInputPosition());
        assertEquals(" \t\r\n", scanner.getRemainingInput());
        assertEquals(Optional.empty(), scanner.nextUnquotedString());
        assertEquals(19, scanner.getInputPosition());
        assertEquals("", scanner.getRemainingInput());
    }

    @Test
    public void nextQuotedString_skipsWhitespace() {
        CommandLineScanner scanner;
        scanner = new CommandLineScanner(" \t\r\n\"pineapple\" \t\r\n");
        assertEquals(Optional.of("pineapple"), scanner.nextQuotedString());
        assertEquals(15, scanner.getInputPosition());
        assertEquals(" \t\r\n", scanner.getRemainingInput());
        assertEquals(Optional.empty(), scanner.nextUnquotedString());
        assertEquals(19, scanner.getInputPosition());
        assertEquals("", scanner.getRemainingInput());
        scanner = new CommandLineScanner(" \t\r\n");
        assertEquals(Optional.empty(), scanner.nextQuotedString());
        assertEquals(4, scanner.getInputPosition());
    }

    @Test
    public void nextQuotedString_requiresQuotesOnBothEnds() {
        CommandLineScanner scanner;
        scanner = new CommandLineScanner("\"apple\"");
        assertEquals(Optional.of("apple"), scanner.nextQuotedString());
        scanner = new CommandLineScanner("\"apple");
        assertEquals(Optional.empty(), scanner.nextQuotedString());
        scanner = new CommandLineScanner("apple\"");
        assertEquals(Optional.empty(), scanner.nextQuotedString());
        scanner = new CommandLineScanner("\"a\"\"b\"");
        assertEquals(Optional.of("a"), scanner.nextQuotedString());
        assertEquals(Optional.of("b"), scanner.nextQuotedString());
    }

    @Test
    public void nextQuotedString_escapeQuotes() {
        CommandLineScanner scanner = new CommandLineScanner("\"escaped \\\"quo\\tes\\\"\"");
        assertEquals(Optional.of("escaped \"quo\\tes\""), scanner.nextQuotedString());
    }

    @Test
    public void nextQuotedString_empty() {
        CommandLineScanner scanner;
        scanner = new CommandLineScanner("\"\"");
        assertEquals(Optional.of(""), scanner.nextQuotedString());
        scanner = new CommandLineScanner("");
        assertEquals(Optional.empty(), scanner.nextQuotedString());
    }

    @Test
    public void nextArgument_unquoted_skipsWhitespace() {
        Optional<CommandLineScanner.Argument> expected, actual;
        CommandLineScanner scanner = new CommandLineScanner(" \t\r\napple \t\r\n");
        expected = Optional.of(new CommandLineScanner.Argument("apple", 4, 9, false));
        actual = scanner.nextArgument();
        assertEquals(expected, actual);
        assertEquals(9, scanner.getInputPosition());
        expected = Optional.empty();
        actual = scanner.nextArgument();
        assertEquals(expected, actual);
        assertEquals(13, scanner.getInputPosition());
    }

    @Test
    public void nextArgument_unquoted_stopsAtAnyFlag() {
        Collection<String> flagPrefixes = Arrays.asList("a-", "bc-");
        CommandLineScanner scanner;
        scanner = new CommandLineScanner("   a-xxx", flagPrefixes);
        assertEquals(Optional.empty(), scanner.nextArgument());
        scanner = new CommandLineScanner("bc-xxx", flagPrefixes);
        assertEquals(Optional.empty(), scanner.nextArgument());
    }

    @Test
    public void nextArgument_quoted_skipsWhitespace() {
        Optional<CommandLineScanner.Argument> expected, actual;
        CommandLineScanner scanner = new CommandLineScanner(" \t\r\n\"quoted\" \t\r\n");
        expected = Optional.of(new CommandLineScanner.Argument("quoted", 4, 12, true));
        actual = scanner.nextArgument();
        assertEquals(expected, actual);
        assertEquals(12, scanner.getInputPosition());
        expected = Optional.empty();
        actual = scanner.nextArgument();
        assertEquals(expected, actual);
        assertEquals(16, scanner.getInputPosition());
    }

    @Test
    public void nextArgument_quoted_doesNotCareAboutFlags() {
        Optional<CommandLineScanner.Argument> expected, actual;
        Collection<String> flagPrefixes = Arrays.asList("a-");
        CommandLineScanner scanner = new CommandLineScanner("\"a-not a flag\"", flagPrefixes);
        expected = Optional.of(new CommandLineScanner.Argument("a-not a flag", 0, 14, true));
        actual = scanner.nextArgument();
        assertEquals(expected, actual);
    }

    @Test
    public void nextRestArgument_skipsWhitespaceUntilAnyFlag() {
        CommandLineScanner.Argument expected, actual;
        Collection<String> flagPrefixes = Arrays.asList("a-", "bc-");
        CommandLineScanner scanner;
        scanner = new CommandLineScanner(" \t\r\na-xxx", flagPrefixes);
        expected = new CommandLineScanner.Argument("", 4, 4, false);
        actual = scanner.nextRestArgument();
        assertEquals(expected, actual);
        assertEquals(4, scanner.getInputPosition());
    }

    @Test
    public void nextRestArgument_unquoted_returnsSubstringUntilAnyFlag() {
        CommandLineScanner.Argument expected, actual;
        Collection<String> flagPrefixes = Arrays.asList("a-", "bc-");
        CommandLineScanner scanner;
        scanner = new CommandLineScanner(" a \"b\"  \\\" \t\r\na-", flagPrefixes);
        expected = new CommandLineScanner.Argument("a \"b\"  \\\"", 1, 10, false);
        actual = scanner.nextRestArgument();
        assertEquals(expected, actual);
        assertEquals(14, scanner.getInputPosition());
        scanner = new CommandLineScanner(" a \"b\"  \\\" \t\r\nbc-", flagPrefixes);
        expected = new CommandLineScanner.Argument("a \"b\"  \\\"", 1, 10, false);
        actual = scanner.nextRestArgument();
        assertEquals(expected, actual);
        assertEquals(14, scanner.getInputPosition());
    }

    @Test
    public void nextRestArgument_unquoted_scansUntilEnd() {
        CommandLineScanner.Argument expected, actual;
        CommandLineScanner scanner = new CommandLineScanner(" a b \t\r\n");
        expected = new CommandLineScanner.Argument("a b", 1, 4, false);
        actual = scanner.nextRestArgument();
        assertEquals(expected, actual);
        assertEquals(8, scanner.getInputPosition());
    }

    @Test
    public void nextRestArgument_quoted_onlyReturnsQuoted() {
        CommandLineScanner.Argument expected, actual;
        Collection<String> flagPrefixes = Arrays.asList("a-", "bc-");
        CommandLineScanner scanner;
        scanner = new CommandLineScanner(" \"a- bc-\" d", flagPrefixes);
        expected = new CommandLineScanner.Argument("a- bc-", 1, 9, true);
        actual = scanner.nextRestArgument();
        assertEquals(expected, actual);
        assertEquals(9, scanner.getInputPosition());
    }

    @Test
    public void nextFlag_skipsWhitespaceUntilEnd() throws Exception {
        Optional<CommandLineScanner.Flag> expected, actual;
        CommandLineScanner scanner = new CommandLineScanner(" \t\r\n");
        expected = Optional.empty();
        actual = scanner.nextFlag();
        assertEquals(expected, actual);
        assertEquals(4, scanner.getInputPosition());
    }

    @Test
    public void nextFlag_unquoted_scansUntilNextFlag() throws Exception {
        Optional<CommandLineScanner.Flag> expected, actual;
        Collection<String> flagPrefixes = Arrays.asList("a-", "bc-");
        CommandLineScanner scanner;
        scanner = new CommandLineScanner(" \t\r\na- some  args c-bc- bc-", flagPrefixes);
        expected = Optional.of(new CommandLineScanner.Flag("a-", "some  args c-bc-", 7, 23, false));
        actual = scanner.nextFlag();
        assertEquals(expected, actual);
        assertEquals(24, scanner.getInputPosition());
        scanner = new CommandLineScanner(" \t\r\nbc- some more args \t\r\n", flagPrefixes);
        expected = Optional.of(new CommandLineScanner.Flag("bc-", "some more args", 8, 22, false));
        actual = scanner.nextFlag();
        assertEquals(expected, actual);
        assertEquals(26, scanner.getInputPosition());
    }

    @Test
    public void nextFlag_quoted_scansQuotedOnly() throws Exception {
        Optional<CommandLineScanner.Flag> expected, actual;
        Collection<String> flagPrefixes = Arrays.asList("a-", "bc-");
        CommandLineScanner scanner;
        scanner = new CommandLineScanner(" \t\r\na-\" quoted \" not", flagPrefixes);
        expected = Optional.of(new CommandLineScanner.Flag("a-", " quoted ", 6, 16, true));
        actual = scanner.nextFlag();
        assertEquals(expected, actual);
        assertEquals(16, scanner.getInputPosition());
        scanner = new CommandLineScanner(" \t\r\nbc- \"quoted\"", flagPrefixes);
        expected = Optional.of(new CommandLineScanner.Flag("bc-", "quoted", 8, 16, true));
        actual = scanner.nextFlag();
        assertEquals(expected, actual);
        assertEquals(16, scanner.getInputPosition());
    }

    @Test
    public void nextFlag_notAFlag_returnsEmpty() {
        CommandLineScanner scanner = new CommandLineScanner("notaflag-");
        try {
            scanner.nextFlag();
            Assert.fail("ParseException not thrown");
        } catch (ParseException e) {
            List<SubstringRange> expectedRanges = Arrays.asList(new SubstringRange(0, 9));
            assertEquals(expectedRanges, e.getRanges());
        }
    }

}
