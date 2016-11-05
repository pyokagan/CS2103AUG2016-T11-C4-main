package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.model.ModelManager;

public class HelpCommandTest {

    @Test
    public void execute() {
        final HelpCommand command = new HelpCommand();
        final ModelManager model = new ModelManager();
        final CommandResult result = command.execute(model);
        assertEquals("Opening help window...", result.feedbackToUser);
        assertTrue(result instanceof HelpCommandResult);
    }

}
