package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.time.TypicalLocalDateTimes.UNIX_EPOCH;
import static seedu.address.testutil.TestUtil.assertThrows;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.EditDeadlineCommand;

public class EditDeadlineParserTest {

    private EditDeadlineParser parser;

    @Before
    public void setupParser() {
        parser = new EditDeadlineParser(UNIX_EPOCH);
    }

    @Test
    public void parse() throws ParseException {
        // No modifications
        assertParse("1", 0, null, null);

        // Date
        assertParse("2 dd-4/5/2016", 1, LocalDate.of(2016, 5, 4), null);

        // Time
        assertParse("3 dt-5:32am", 2, null, LocalTime.of(5, 32));

        // Date and Time
        assertParse("4 dd-7/8 dt-4:00pm", 3, LocalDate.of(1970, 8, 7), LocalTime.of(16, 0));

        // Index cannot be negative
        assertIncorrect("-1");

        // Invalid flags
        assertIncorrect("8 invalid-flag");
    }

    private void assertParse(String args, int targetIndex, LocalDate newDate, LocalTime newTime)
                throws ParseException {
        final Command command = parser.parse(args);
        assertTrue(command instanceof EditDeadlineCommand);
        final EditDeadlineCommand editCommand = (EditDeadlineCommand) command;
        assertEquals(targetIndex, editCommand.getTargetIndex());
        assertEquals(Optional.ofNullable(newDate), editCommand.getNewDate());
        assertEquals(Optional.ofNullable(newTime), editCommand.getNewTime());

    }

    private void assertIncorrect(String args) {
        assertThrows(ParseException.class, () -> parser.parse(args));
    }

}
