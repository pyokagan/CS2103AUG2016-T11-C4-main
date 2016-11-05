package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static seedu.address.commons.time.TypicalLocalDateTimes.UNIX_EPOCH;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import seedu.address.model.Model;
import seedu.address.model.filter.FalseTaskPredicate;
import seedu.address.model.task.EventTask;

public class ListCommandTest {

    @Mock
    private Model model;

    @Before
    public void setup() throws Exception {
        MockitoAnnotations.initMocks(this);
        Mockito.when(model.getEventTask(1)).thenReturn(new EventTask("Lunch with Bill Gates", UNIX_EPOCH,
                                                                     UNIX_EPOCH.plusHours(1)));
    }

    @Test
    public void execute_withNullTaskPredicate_replacesTaskPredicate() {
        final ListCommand cmd = new ListCommand(null);
        final CommandResult result = cmd.execute(model);
        assertEquals("Listing all tasks.", result.feedbackToUser);
        Mockito.verify(model).setTaskPredicate(null);
    }

    @Test
    public void execute_withNonNullTaskPredicate_replacesTaskPredicate() {
        final FalseTaskPredicate predicate = new FalseTaskPredicate();
        final ListCommand cmd = new ListCommand(predicate);
        final CommandResult result = cmd.execute(model);
        assertEquals("Listing all tasks matching filter: false task predicate", result.feedbackToUser);
        Mockito.verify(model).setTaskPredicate(predicate);
    }

}
