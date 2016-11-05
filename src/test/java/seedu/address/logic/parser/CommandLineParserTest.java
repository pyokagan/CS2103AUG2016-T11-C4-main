package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.TestUtil.assertThrows;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import seedu.address.commons.util.SubstringRange;

public class CommandLineParserTest {

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
        assertEquals(y, arg2.getValue());
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
        assertEquals("flag2Value", flag2.getValue());
    }

}
