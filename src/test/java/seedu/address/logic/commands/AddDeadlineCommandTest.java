package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import seedu.address.model.Model;
import seedu.address.model.task.TypicalDeadlineTasks;

public class AddDeadlineCommandTest {

    private final TypicalDeadlineTasks tdt = new TypicalDeadlineTasks();

    @Mock
    private Model model;

    @Before
    public void setup() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void execute_addsEventToModel() {
        Mockito.when(model.addDeadlineTask(tdt.assembleTheMissiles)).thenReturn(1);
        final AddDeadlineCommand cmd = new AddDeadlineCommand(tdt.assembleTheMissiles);
        final CommandResult result = cmd.execute(model);
        assertEquals("Added new deadline task \"Assemble The Missiles\", due on \"01/01/1970 Time: 02:00\".",
                     result.feedbackToUser);
        Mockito.verify(model).addDeadlineTask(tdt.assembleTheMissiles);
    }

}
