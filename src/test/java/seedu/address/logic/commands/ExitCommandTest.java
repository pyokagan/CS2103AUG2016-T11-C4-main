package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.model.ModelManager;

public class ExitCommandTest {

    @Test
    public void execute() {
        final ModelManager model = new ModelManager();
        final ExitCommand command = new ExitCommand();
        final CommandResult result = command.execute(model);
        assertEquals("Exiting as requested...", result.feedbackToUser);
        assertTrue(result instanceof ExitCommandResult);
    }

}
