package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import seedu.address.model.Model;
import seedu.address.model.task.TypicalEventTasks;

public class AddEventCommandTest {

    private final TypicalEventTasks tet = new TypicalEventTasks();

    @Mock
    private Model model;

    @Before
    public void setup() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void execute_addsEventToModel() {
        Mockito.when(model.addEventTask(tet.lunchWithBillGates)).thenReturn(1);
        final AddEventCommand cmd = new AddEventCommand(tet.lunchWithBillGates);
        final CommandResult result = cmd.execute(model);
        assertEquals("Added new event task \"Lunch with Bill Gates\", from \"01/01/1970 Time: 00:00\" to "
                     + "\"01/01/1970 Time: 01:00\".", result.feedbackToUser);
        Mockito.verify(model).addEventTask(tet.lunchWithBillGates);
    }

}
