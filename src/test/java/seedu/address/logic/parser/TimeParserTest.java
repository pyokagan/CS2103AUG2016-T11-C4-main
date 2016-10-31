package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.time.LocalTime;

import org.junit.Before;
import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;

public class TimeParserTest {

    private static final LocalTime TEST_TIME = LocalTime.of(3, 14);

    private TimeParser parser;

    @Before
    public void setupParser() {
        parser = new TimeParser(TEST_TIME);
    }

    @Test
    public void constructor() {
        assertEquals(TEST_TIME, parser.getReferenceTime());
    }

    @Test
    public void parse() throws Exception {
        // Missing minute component
        assertParse("  4am  ", LocalTime.of(4, 0));

        // . and : accepted, but must precede minute component
        assertParse("1.23pm", LocalTime.of(13, 23));
        assertParse("2:45am", LocalTime.of(2, 45));
        assertParseFail("5:am");

        // Hours (0-12)am/pm
        assertParse("12am", LocalTime.of(0, 0));
        assertParse("0am", LocalTime.of(0, 0)); // Yeah, we accept 0am because some people do use it apparently (?)
        assertParse("0pm", LocalTime.of(12, 0)); // Same for 0pm.
        assertParse("12pm", LocalTime.of(12, 0));
        assertParseFail("13am");

        // Minutes (0-59)
        assertParse("4:00am", LocalTime.of(4, 0));
        assertParse("4:59am", LocalTime.of(4, 59));
        assertParseFail("4:60am");
        assertParseFail("4:99am");

        // Must have two minute digits
        assertParseFail("4:0am");

        assertParseFail("not a time at all");
    }

    private void assertParse(String str, LocalTime expected) throws Exception {
        final LocalTime actual = parser.parse(str);
        assertEquals(expected, actual);
    }

    private void assertParseFail(String str) {
        try {
            parser.parse(str);
            fail("IllegalValueException was not thrown");
        } catch (IllegalValueException e) {
        }
    }

}
