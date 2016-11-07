package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import seedu.address.model.Model;
import seedu.address.model.task.TypicalFloatingTasks;

public class AddFloatingTaskCommandTest {

    private final TypicalFloatingTasks tft = new TypicalFloatingTasks();

    @Mock
    private Model model;

    @Before
    public void setup() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void execute_addsFloatingTaskToModel() {
        Mockito.when(model.addFloatingTask(tft.buyAHelicopter)).thenReturn(1);
        final AddFloatingTaskCommand cmd = new AddFloatingTaskCommand(tft.buyAHelicopter);
        final CommandResult result = cmd.execute(model);
        assertEquals("Added new floating task \"buy A Helicopter\", with priority 4.", result.feedbackToUser);
        Mockito.verify(model).addFloatingTask(tft.buyAHelicopter);
    }

}
