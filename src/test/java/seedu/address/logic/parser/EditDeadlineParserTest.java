package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.time.TypicalLocalDateTimes.UNIX_EPOCH;
import static seedu.address.testutil.TestUtil.assertThrows;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.EditDeadlineCommand;
import seedu.address.model.ModelManager;
import seedu.address.model.task.TypicalDeadlineTasks;

public class EditDeadlineParserTest {

    private final TypicalDeadlineTasks tdt = new TypicalDeadlineTasks();

    private final ModelManager model = new ModelManager();

    private EditDeadlineParser parser = new EditDeadlineParser(UNIX_EPOCH);

    @Before
    public void setupParser() {
        assertEquals(1, model.addDeadlineTask(tdt.assembleTheMissiles));
    }

    @Test
    public void parse() throws ParseException {
        // No modifications
        assertParse("d1", 1, null, null);

        // Date
        assertParse("d2 dd-4/5/2016", 2, LocalDate.of(2016, 5, 4), null);

        // Time
        assertParse("d3 dt-5:32am", 3, null, LocalTime.of(5, 32));

        // Date and Time
        assertParse("d4 dd-7/8 dt-4:00pm", 4, LocalDate.of(1970, 8, 7), LocalTime.of(16, 0));

        // Index cannot be negative
        assertIncorrect("d-1");

        // Invalid flags
        assertIncorrect("d8 invalid-flag");
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

    @Test
    public void autocompleteName_returnsDeadlineTaskName() {
        assertEquals(Arrays.asList("Assemble The Missiles"), parser.autocomplete(model, "d1 n- ", 5));
        assertEquals(Collections.emptyList(), parser.autocomplete(model, "d2 n- ", 5));
        assertEquals(Collections.emptyList(), parser.autocomplete(model, "d1 n-a", 5));
        assertEquals(Collections.emptyList(), parser.autocomplete(model, "1 n-", 4));
    }

    @Test
    public void autocompleteDueDate_returnsDeadlineDueDate() {
        assertEquals(Arrays.asList("1/1/1970"), parser.autocomplete(model, "d1 dd- ", 6));
        assertEquals(Collections.emptyList(), parser.autocomplete(model, "d2 dd- ", 6));
        assertEquals(Collections.emptyList(), parser.autocomplete(model, "d1 dd-4/5", 6));
        assertEquals(Collections.emptyList(), parser.autocomplete(model, "1 dd-", 5));
    }

    @Test
    public void autocompleteDueTime_returnsDeadlineDueTime() {
        assertEquals(Arrays.asList("2am"), parser.autocomplete(model, "d1 dt- ", 6));
        assertEquals(Collections.emptyList(), parser.autocomplete(model, "d2 dt- ", 6));
        assertEquals(Collections.emptyList(), parser.autocomplete(model, "d1 dt-6am", 6));
        assertEquals(Collections.emptyList(), parser.autocomplete(model, "1 dt-", 5));
    }

}
