package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TestUtil.assertThrows;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.EditFloatingTaskCommand;
import seedu.address.model.task.Name;
import seedu.address.model.task.Priority;

public class EditFloatingTaskParserTest {

    private EditFloatingTaskParser parser;

    @Before
    public void setupParser() {
        parser = new EditFloatingTaskParser();
    }

    @Test
    public void parse() throws ParseException, IllegalValueException {
        // No modification
        assertParse("f1", 0, null, null);

        // name
        assertParse("f1 n-new name", 0, new Name("new name"), null);

        // priority
        assertParse("f1 p-2", 0, null, new Priority("2"));

        // All arguments
        assertParse("f3 n-thisname p-5", 2, new Name("thisname"), new Priority("5"));

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

}
