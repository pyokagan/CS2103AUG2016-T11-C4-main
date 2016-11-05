package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TestUtil.assertThrows;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.EditFloatingTaskCommand;
import seedu.address.model.ModelManager;
import seedu.address.model.task.Name;
import seedu.address.model.task.Priority;
import seedu.address.model.task.TypicalFloatingTasks;

public class EditFloatingTaskParserTest {

    private final TypicalFloatingTasks tft = new TypicalFloatingTasks();

    private final ModelManager model = new ModelManager();

    private EditFloatingTaskParser parser = new EditFloatingTaskParser();

    @Before
    public void setupParser() {
        parser = new EditFloatingTaskParser();
        assertEquals(1, model.addFloatingTask(tft.buyAHelicopter));
    }

    @Test
    public void parse() throws ParseException, IllegalValueException {
        // No modification
        assertParse("f1", 1, null, null);

        // name
        assertParse("f1 n-new name", 1, new Name("new name"), null);

        // priority
        assertParse("f1 p-2", 1, null, new Priority("2"));

        // All arguments
        assertParse("f3 n-thisname p-5", 3, new Name("thisname"), new Priority("5"));

        // Invalid flags
        assertIncorrect("f5 invalid-flag");
    }

    private void assertParse(String args, int targetIndex, Name name, Priority priority)
            throws ParseException {
        final Command command = parser.parse(args);
        assertTrue(command instanceof EditFloatingTaskCommand);

        assertEquals(targetIndex, ((EditFloatingTaskCommand)command).getTargetIndex());
        assertEquals(Optional.ofNullable(name), ((EditFloatingTaskCommand)command).getNewName());
        assertEquals(Optional.ofNullable(priority), ((EditFloatingTaskCommand)command).getNewPriority());
    }

    private void assertIncorrect(String args) {
        assertThrows(ParseException.class, () -> parser.parse(args));
    }

    @Test
    public void autocompleteName_returnsFloatingTaskName() {
        assertEquals(Arrays.asList("buy A Helicopter"), parser.autocomplete(model, "f1 n- ", 5));
        assertEquals(Collections.emptyList(), parser.autocomplete(model, "f2 n- ", 5));
        assertEquals(Collections.emptyList(), parser.autocomplete(model, "f1 n-a", 5));
        assertEquals(Collections.emptyList(), parser.autocomplete(model, "1 n-", 4));
    }

    @Test
    public void autocompletePriority_returnsFloatingTaskPriority() {
        assertEquals(Arrays.asList("4"), parser.autocomplete(model, "f1 p- ", 5));
        assertEquals(Collections.emptyList(), parser.autocomplete(model, "f2 p- ", 5));
        assertEquals(Collections.emptyList(), parser.autocomplete(model, "f1 p-3", 5));
        assertEquals(Collections.emptyList(), parser.autocomplete(model, "1 p-", 4));
    }

}
