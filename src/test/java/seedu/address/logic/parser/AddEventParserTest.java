package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.time.TypicalLocalDateTimes.PI_DAY;

import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddEventCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.IncorrectCommand;
import seedu.address.model.task.EventTask;

public class AddEventParserTest {

    private AddEventParser parser;

    @Before
    public void setupParser() {
        parser = new AddEventParser(PI_DAY);
    }

    @Test
    public void parse() {
        // All arguments provided
        assertParse("\"a\" 1/2/2000 4:30am 2/3/2100 6:32pm", "a", LocalDateTime.of(2000, 2, 1, 4, 30),
                    LocalDateTime.of(2100, 3, 2, 18, 32));

        // If startDate is not given, then it is the current date
        assertParse("\"a\" 7:23am 4/6/2654 8:24pm", "a", LocalDateTime.of(2653, 9, 15, 7, 23),
                LocalDateTime.of(2654, 6, 4, 20, 24));

        // If startTime is not given, then it is 12am
        assertParse("\"a\" 3/4/2015 4/5/2015 7pm", "a", LocalDateTime.of(2015, 4, 3, 0, 0),
                LocalDateTime.of(2015, 5, 4, 19, 0));

        // If endDate is not given, then it is the same as startDate
        assertParse("\"a\" 1/2/2000 5:41am 6:32pm", "a", LocalDateTime.of(2000, 2, 1, 5, 41),
                LocalDateTime.of(2000, 2, 1, 18, 32));

        // If endTime is not given, then it is 11.59pm
        assertParse("\"a\" 8/7/2014 12:45am 9/7/2014", "a",
                LocalDateTime.of(2014, 7, 8, 0, 45), LocalDateTime.of(2014, 7, 9, 23, 59));

        // Must have at least one of { startDate, startTime }, and at least one of { endDate, endTime }
        assertIncorrect("\"a\" 1/2/2000 4:30am");
        assertIncorrect("\"a\"");
        assertIncorrect("");

        // Too many arguments
        assertIncorrect("\"a\" 4/5/2016 7:23am 6/7/2017 8:42pm extraArg");
    }

    private void assertParse(String args, String name, LocalDateTime start, LocalDateTime end) {
        final EventTask expected;
        try {
            expected = new EventTask(name, start, end);
        } catch (IllegalValueException e) {
            throw new AssertionError("should not happen", e);
        }
        final Command command = parser.parse(args);
        assertTrue(command instanceof AddEventCommand);
        assertEquals(expected, ((AddEventCommand)command).getEventTask());
    }

    private void assertIncorrect(String args) {
        final Command command = parser.parse(args);
        assertTrue(command instanceof IncorrectCommand);
    }

}
