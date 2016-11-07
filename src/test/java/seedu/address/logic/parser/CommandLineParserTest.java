package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.TestUtil.assertThrows;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import seedu.address.commons.util.SubstringRange;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyModel;

public class CommandLineParserTest {

    private ModelManager model = new ModelManager();

    @Test
    public void parse_emptyString() throws ParseException {
        CommandLineParser parser = new CommandLineParser();
        parser.parse("");
        parser.parse(" \t\r\n");
    }

    @Test
    public void parse_tooManyArguments_throwsException() {
        CommandLineParser parser;
        parser = new CommandLineParser();
        try {
            parser.parse("arg1");
            Assert.fail("ParseException not thrown");
        } catch (ParseException e) {
            List<SubstringRange> expectedRanges = Arrays.asList(new SubstringRange(0, 4));
            assertEquals("Too many arguments", e.getMessage());
            assertEquals(expectedRanges, e.getRanges());
        }

        CommandLineParser.Argument<String> arg1 = new CommandLineParser.Argument<>("arg1", str -> str);
        parser = new CommandLineParser().addArgument(arg1);
        try {
            parser.parse("arg1 arg2");
            Assert.fail("ParseException not thrown");
        } catch (ParseException e) {
            List<SubstringRange> expectedRanges = Arrays.asList(new SubstringRange(5, 9));
            assertEquals("Too many arguments", e.getMessage());
            assertEquals(expectedRanges, e.getRanges());
        }
    }

    @Test
    public void parse_withArguments() throws ParseException {
        final Object x = new Object(), y = new Object();
        CommandLineParser.Argument<Object> arg1 = new CommandLineParser.Argument<>("arg1", str -> {
            assertEquals("Arg1", str);
            return x;
        });
        CommandLineParser.Argument<Object> arg2 = new CommandLineParser.Argument<>("arg2", str -> {
            assertEquals("Arg2", str);
            return y;
        });
        CommandLineParser parser = new CommandLineParser().addArgument(arg1).addArgument(arg2);
        assertThrows(AssertionError.class, () -> arg1.getValue());
        assertThrows(AssertionError.class, () -> arg2.getValue());
        parser.parse(" \t\r\nArg1 \t\r\n\"Arg2\" \t\r\n");
        assertEquals(x, arg1.getValue());
        assertEquals(new SubstringRange(4, 8), arg1.getRange());
        assertEquals(y, arg2.getValue());
        assertEquals(new SubstringRange(12, 18), arg2.getRange());
    }

    @Test
    public void parse_withArgumentsButNotEnoughArguments_throwsException() {
        final Object x = new Object(), y = new Object();
        CommandLineParser.Argument<Object> arg1 = new CommandLineParser.Argument<>("arg1", str -> {
            assertEquals("Arg1", str);
            return x;
        });
        CommandLineParser.Argument<Object> arg2 = new CommandLineParser.Argument<>("arg2", str -> {
            assertEquals("Arg2", str);
            return y;
        });
        CommandLineParser parser = new CommandLineParser().addArgument(arg1).addArgument(arg2);
        try {
            parser.parse(" \t\r\nArg1 \t\r\n");
            Assert.fail("ParseException not thrown");
        } catch (ParseException e) {
            List<SubstringRange> expectedRanges = Arrays.asList(new SubstringRange(8, 12));
            assertEquals("Required argument not provided: arg2", e.getMessage());
            assertEquals(expectedRanges, e.getRanges());
        }
    }

    @Test
    public void parse_argumentParserThrowsException_throwsException() {
        final Object x = new Object();
        final ParseException myException = new ParseException("MyParseException",
                                                              new SubstringRange(0, 1),
                                                              new SubstringRange(1, 2));
        CommandLineParser.Argument<Object> arg1 = new CommandLineParser.Argument<>("arg1", str -> {
            assertEquals("Arg1", str);
            return x;
        });
        CommandLineParser.Argument<Object> arg2 = new CommandLineParser.Argument<>("arg2", str -> {
            throw myException;
        });
        CommandLineParser parser = new CommandLineParser().addArgument(arg1).addArgument(arg2);
        try {
            parser.parse("Arg1 Arg2");
            Assert.fail("ParseException not thrown");
        } catch (ParseException e) {
            List<SubstringRange> expectedRanges = Arrays.asList(new SubstringRange(5, 6), new SubstringRange(6, 7));
            assertEquals(myException, e.getCause());
            assertEquals("arg2: MyParseException", e.getMessage());
            assertEquals(expectedRanges, e.getRanges());
        }
        assertEquals(x, arg1.getValue());
        assertThrows(AssertionError.class, () -> arg2.getValue());
    }

    @Test
    public void parse_withListArgument_collectsArgumentsIntoList() throws ParseException {
        final Deque<String> expectedArgs = new ArrayDeque<>(Arrays.asList("Apple", "Banana", "Pear"));
        Parser<String> listParser = str -> {
            assertEquals(expectedArgs.poll(), str);
            return str.toUpperCase();
        };
        CommandLineParser.ListArgument<String> listArg = new CommandLineParser.ListArgument<>("LISTARG", str -> {
            throw new AssertionError("should not be called");
        });
        listArg.setParser(listParser);
        CommandLineParser parser = new CommandLineParser().addArgument(listArg);
        assertEquals("LISTARG", listArg.getName());
        assertEquals(Collections.emptyList(), listArg.getValues());
        assertThrows(AssertionError.class, () -> listArg.getRange());
        parser.parse("  Apple Banana Pear ");
        assertEquals(Arrays.asList("APPLE", "BANANA", "PEAR"), listArg.getValues());
        assertEquals(new SubstringRange(2, 19), listArg.getRange());
    }

    @Test
    public void parse_flag_parsesOutOfOrder() throws ParseException {
        CommandLineParser.Flag<String> flag1 = new CommandLineParser.Flag<>("f1-", "FLAG1", str -> {
            assertEquals("", str);
            return "flag1Value";
        });
        CommandLineParser.Flag<String> flag2 = new CommandLineParser.Flag<>("f2-", "FLAG2", str -> {
            assertEquals("value1", str);
            return "flag2Value";
        });
        CommandLineParser parser = new CommandLineParser().putFlag(flag1).putFlag(flag2);
        assertThrows(AssertionError.class, () -> flag1.getValue());
        assertThrows(AssertionError.class, () -> flag1.getRange());
        assertThrows(AssertionError.class, () -> flag2.getValue());
        assertThrows(AssertionError.class, () -> flag2.getRange());
        parser.parse(" \t\r\nf2- \t\r\nvalue1 \t\r\nf1- \t\r\n");
        assertEquals("flag1Value", flag1.getValue());
        assertEquals(new SubstringRange(28, 28), flag1.getRange());
        assertEquals("flag2Value", flag2.getValue());
        assertEquals(new SubstringRange(11, 17), flag2.getRange());
    }

    @Test
    public void parse_requiredFlagNotProvided_throwsParseException() {
        CommandLineParser.Flag<String> flag1 = new CommandLineParser.Flag<>("f1-", "FLAG1", str -> {
            throw new AssertionError("should not be called");
        });
        CommandLineParser parser = new CommandLineParser().putFlag(flag1);
        try {
            parser.parse("");
            assert false;
        } catch (ParseException e) {
            assertEquals("Required flag not provided: f1-FLAG1", e.getMessage());
        }
    }

    @Test
    public void parse_sameFlagSpecifiedMultipleTimes_throwsParseException() {
        CommandLineParser.Flag<String> flag1 = new CommandLineParser.Flag<>("f1-", "FLAG1", str -> {
            assertEquals("flag1Arg", str);
            return "flag1Value";
        });
        CommandLineParser parser = new CommandLineParser().putFlag(flag1);
        try {
            parser.parse("f1-flag1Arg f1-flag2Arg");
            assert false;
        } catch (ParseException e) {
            assertEquals("f1- specified multiple times", e.getMessage());
            assertEquals(Arrays.asList(new SubstringRange(15, 23)), e.getRanges());
        }
    }

    @Test
    public void parse_flagParserThrowsParseException_rethrowsParseException() {
        final Object x = new Object();
        final ParseException myException = new ParseException("MyParseException",
                                                              new SubstringRange(0, 1),
                                                              new SubstringRange(1, 2));
        CommandLineParser.Flag<Object> flag1 = new CommandLineParser.Flag<>("f1-", "flag1", str -> {
            assertEquals("Flag1", str);
            return x;
        });
        CommandLineParser.Flag<Object> flag2 = new CommandLineParser.Flag<>("f2-", "flag2", str -> {
            assertEquals("Flag2", str);
            throw myException;
        });
        CommandLineParser parser = new CommandLineParser().putFlag(flag1).putFlag(flag2);
        try {
            parser.parse("f1-Flag1 f2-Flag2");
            assert false;
        } catch (ParseException e) {
            List<SubstringRange> expectedRanges = Arrays.asList(new SubstringRange(12, 13), new SubstringRange(13, 14));
            assertEquals(myException, e.getCause());
            assertEquals("flag2: MyParseException", e.getMessage());
            assertEquals(expectedRanges, e.getRanges());
        }
        assertEquals(x, flag1.getValue());
        assertThrows(AssertionError.class, () -> flag2.getValue());
    }

    @Test
    public void autocomplete_withArguments_callsAutocompleteOfArgumentsAndReturnsCandidates() {
        final List<String> expectedAutocomplete = Arrays.asList("a", "b", "c");
        CommandLineParser.Argument<String> arg1 = new CommandLineParser.Argument<>("ARG1", new Parser<String>() {
            @Override
            public String parse(String str) throws ParseException {
                assertEquals("arg1", str);
                throw new ParseException("some exception"); // This is okay
            }

            @Override
            public List<String> autocomplete(ReadOnlyModel model, String input, int pos) {
                throw new AssertionError("should not be called");
            }
        });

        CommandLineParser.Argument<String> arg2 = new CommandLineParser.Argument<>("ARG2", new Parser<String>() {
            @Override
            public String parse(String str) throws ParseException {
                throw new AssertionError("should not be called");
            }

            @Override
            public List<String> autocomplete(ReadOnlyModel model, String input, int pos) {
                assertEquals("arg2", input);
                assertEquals(1, pos);
                return expectedAutocomplete;
            }
        });

        CommandLineParser parser = new CommandLineParser().addArgument(arg1).addArgument(arg2);
        assertEquals(expectedAutocomplete, parser.autocomplete(model, "arg1  arg2  ", 7));
        // The parser will skip quoted args because we do not handle it yet
        assertEquals(Collections.emptyList(), parser.autocomplete(model, "arg1 \"arg2\"", 7));
    }

    @Test
    public void autocomplete_withListArgument_callsAutocompleteForAnyArgumentAndReturnsCandidates() {
        final List<String> expectedAutocomplete = Arrays.asList("a", "b", "c");
        CommandLineParser.ListArgument<String> listArg = new CommandLineParser.ListArgument<>("ARGS", new Parser<String>() {
            @Override
            public String parse(String str) throws ParseException {
                throw new AssertionError("should not be called");
            }

            @Override
            public List<String> autocomplete(ReadOnlyModel model, String input, int pos) {
                return expectedAutocomplete;
            }
        });

        CommandLineParser parser = new CommandLineParser().addArgument(listArg);
        assertEquals(expectedAutocomplete, parser.autocomplete(model, "arg1  arg2  ", 7));
        assertEquals(Collections.emptyList(), parser.autocomplete(model, "", 0));
    }

}
