package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static seedu.address.commons.time.TypicalLocalDateTimes.UNIX_EPOCH;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import seedu.address.model.Model;
import seedu.address.model.task.DeadlineTask;
import seedu.address.model.task.Name;

public class EditDeadlineCommandTest {

    @Mock
    private Model model;

    @Before
    public void setup() throws Exception {
        MockitoAnnotations.initMocks(this);
        Mockito.when(model.getDeadlineTask(1)).thenReturn(new DeadlineTask(new Name("Finish project"),
                                                                           UNIX_EPOCH.plusDays(1), true));
    }

    @Test
    public void execute_withNoEdits_replacesNothing() throws Exception {
        final DeadlineTask expected = new DeadlineTask(new Name("Finish project"), UNIX_EPOCH.plusDays(1), true);
        final EditDeadlineCommand cmd = new EditDeadlineCommand(1, Optional.empty(), Optional.empty(),
                                                                Optional.empty());
        final CommandResult result = cmd.execute(model);
        assertEquals("Edited deadline task d1.", result.feedbackToUser);
        Mockito.verify(model).setDeadlineTask(1, expected);
    }

}
