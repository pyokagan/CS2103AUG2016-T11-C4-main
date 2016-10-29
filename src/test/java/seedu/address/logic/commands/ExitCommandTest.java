package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ExitCommandTest {

    @Test
    public void execute() {
        final ExitCommand command = new ExitCommand();
        final CommandResult result = command.execute();
        assertEquals("Exiting as requested...", result.feedbackToUser);
        assertTrue(result instanceof ExitCommandResult);
    }

}
