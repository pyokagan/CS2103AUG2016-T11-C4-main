package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static seedu.address.commons.time.TypicalLocalDateTimes.UNIX_EPOCH;

import org.junit.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.TaskBook;
import seedu.address.model.TaskBookBuilder;
import seedu.address.model.config.Config;

public class ClearFinishedCommandTest {

    @Test
    public void execute_clearsTasksInModel() {
        final TaskBook expectedTaskBook = new TaskBookBuilder().addUnfinishedTasks().build();
        final TaskBook taskBook = new TaskBookBuilder().addTypicalTasks().build();
        final Model model = new ModelManager(new Config(), taskBook);
        final ClearFinishedCommand command = new ClearFinishedCommand(UNIX_EPOCH.plusHours(1));
        CommandResult result;
        try {
            result = command.execute(model);
        } catch (CommandException e) {
            // should never reach this point
            result = null;
            throw new AssertionError("this should not happen", e);
        }
        assertEquals(expectedTaskBook, model.getTaskBook());
        assertEquals("All finished task in Task Tracker have been cleared!", result.feedbackToUser);
    }

}
