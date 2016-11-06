package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import seedu.address.model.Model;
import seedu.address.model.task.FloatingTask;
import seedu.address.model.task.Name;
import seedu.address.model.task.Priority;

public class EditFloatingTaskCommandTest {

    @Mock
    private Model model;

    @Before
    public void setup() throws Exception {
        MockitoAnnotations.initMocks(this);
        Mockito.when(model.getFloatingTask(1)).thenReturn(new FloatingTask(new Name("Finish project"),
                                                                           new Priority("5"), true));
    }

    @Test
    public void execute_withNoEdits_replacesNothing() throws Exception {
        final FloatingTask expected = new FloatingTask(new Name("Finish project"), new Priority("5"), true);
        final EditFloatingTaskCommand cmd = new EditFloatingTaskCommand(1, Optional.empty(), Optional.empty());
        final CommandResult result = cmd.execute(model);
        assertEquals("Edited floating task f1.", result.feedbackToUser);
        Mockito.verify(model).setFloatingTask(1, expected);
    }

}
