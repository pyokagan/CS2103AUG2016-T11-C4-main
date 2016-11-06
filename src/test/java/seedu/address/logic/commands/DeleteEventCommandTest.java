package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.Model;
import seedu.address.model.task.TypicalEventTasks;

public class DeleteEventCommandTest {

    private final TypicalEventTasks tet = new TypicalEventTasks();

    @Mock
    private Model model;

    @Before
    public void setup() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void execute_withValidTargetIndex_removesEventTask() throws Exception {
        Mockito.when(model.removeEventTask(1)).thenReturn(tet.lunchWithBillGates);
        final DeleteEventCommand cmd = new DeleteEventCommand(1);
        final CommandResult result = cmd.execute(model);
        assertEquals("Deleted event e1. Lunch with Bill Gates", result.feedbackToUser);
        Mockito.verify(model).removeEventTask(1);
    }

    @Test
    public void execute_withInvalidTargetIndex_throwsCommandExeption() throws Exception {
        final IllegalValueException e = new IllegalValueException("invalid index");
        Mockito.when(model.removeEventTask(1)).thenThrow(e);
        final DeleteEventCommand cmd = new DeleteEventCommand(1);
        try {
            cmd.execute(model);
            assert false;
        } catch (CommandException ce) {
            assertEquals(e, ce.getCause());
            assertEquals("invalid index", ce.getMessage());
            Mockito.verify(model).removeEventTask(1);
        }
    }

}
