package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.task.DeadlineTask;
import seedu.address.model.task.DeadlineTaskBuilder;
import seedu.address.model.task.TypicalDeadlineTasks;

public class MarkDeadlineFinishedCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final TypicalDeadlineTasks tdt = new TypicalDeadlineTasks();
    private final Model model = new ModelManager();

    @Test
    public void execute_withValidTargetIndex_marksFinished() throws Exception {
        final DeadlineTask assembleTheMissiles = tdt.assembleTheMissiles;
        final DeadlineTask completeHomework = tdt.completeHomework;
        assertEquals(1, model.addDeadlineTask(assembleTheMissiles));
        assertEquals(2, model.addDeadlineTask(completeHomework));
        final CommandResult result = new MarkDeadlineFinishedCommand(1).execute(model);
        assertEquals("Deadline task d1 finished.", result.feedbackToUser);
        assertEquals((new DeadlineTaskBuilder(tdt.assembleTheMissiles).setFinished(true).build()),
                     model.getDeadlineTask(1));
        assertEquals(completeHomework, model.getDeadlineTask(2));
    }

    @Test
    public void execute_invalidTargetIndex_throwsCommandException() throws Exception {
        thrown.expect(CommandException.class);
        thrown.expectMessage("index does not exist");
        new MarkDeadlineFinishedCommand(1).execute(model);
    }

    @Test
    public void execute_nullModel_throwsAssertionError() throws Exception {
        thrown.expect(AssertionError.class);
        new MarkDeadlineFinishedCommand(1).execute(null);
    }
}
