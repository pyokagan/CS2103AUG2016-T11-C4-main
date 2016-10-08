package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.time.TypicalLocalDateTimes.UNIX_EPOCH;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.EditEventCommand;
import seedu.address.logic.commands.IncorrectCommand;

public class EditEventParserTest {

    private EditEventParser parser;

    @Before
    public void setupParser() {
        parser = new EditEventParser(UNIX_EPOCH);
    }

    @Test
    public void parse() {
        // No modifications
        assertParse("1", 1, null, null, null, null);

        // startDate
        assertParse("2 sd-4/5/2016", 2, LocalDate.of(2016, 5, 4), null, null, null);

        // startTime
        assertParse("3 st-5:32am", 3, null, LocalTime.of(5, 32), null, null);

        // endDate
        assertParse("4 ed-7/8", 4, null, null, LocalDate.of(1970, 8, 7), null);

        // endTime
        assertParse("5 et-7pm", 5, null, null, null, LocalTime.of(19, 0));

        // Index cannot be negative
        assertIncorrect("-1");

        // Invalid flags
        assertIncorrect("8 invalid-flag");
    }

    private void assertParse(String args, int targetIndex, LocalDate newStartDate, LocalTime newStartTime,
                             LocalDate newEndDate, LocalTime newEndTime) {
        final Command command = parser.parse(args);
        assertTrue(command instanceof EditEventCommand);
        final EditEventCommand editCommand = (EditEventCommand) command;
        assertEquals(targetIndex, editCommand.getTargetIndex());
        assertEquals(newStartDate, editCommand.getNewStartDate());
        assertEquals(newStartTime, editCommand.getNewStartTime());
        assertEquals(newEndDate, editCommand.getNewEndDate());
        assertEquals(newEndTime, editCommand.getNewEndTime());
    }

    private void assertIncorrect(String args) {
        final Command command = parser.parse(args);
        assertTrue(command instanceof IncorrectCommand);
    }

}
