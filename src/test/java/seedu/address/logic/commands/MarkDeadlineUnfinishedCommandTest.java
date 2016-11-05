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

public class MarkDeadlineUnfinishedCommandTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final TypicalDeadlineTasks tdt = new TypicalDeadlineTasks();
    private final Model model = new ModelManager();

    @Test
    public void execute_withValidTargetIndex_marksUnfinished() throws Exception {
        final DeadlineTask assembleTheMissiles = new DeadlineTaskBuilder(tdt.assembleTheMissiles)
                                                        .setFinished(true).build();
        final DeadlineTask completeHomework = new DeadlineTaskBuilder(tdt.completeHomework)
                                                .setFinished(true).build();
        assertEquals(1, model.addDeadlineTask(assembleTheMissiles));
        assertEquals(2, model.addDeadlineTask(completeHomework));
        final CommandResult result = new MarkDeadlineUnfinishedCommand(1).execute(model);
        assertEquals("Deadline task d1 unfinished.", result.feedbackToUser);
        assertEquals(tdt.assembleTheMissiles, model.getDeadlineTask(1));
        assertEquals(completeHomework, model.getDeadlineTask(2));
    }

    @Test
    public void execute_invalidTargetIndex_throwsCommandException() throws Exception {
        thrown.expect(CommandException.class);
        thrown.expectMessage("index does not exist");
        new MarkDeadlineUnfinishedCommand(1).execute(model);
    }

    @Test
    public void execute_nullModel_throwsAssertionError() throws Exception {
        thrown.expect(AssertionError.class);
        new MarkDeadlineUnfinishedCommand(1).execute(null);
    }

}
