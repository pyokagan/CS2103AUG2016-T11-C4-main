package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static seedu.address.commons.time.TypicalLocalDateTimes.UNIX_EPOCH;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import seedu.address.model.Model;
import seedu.address.model.task.EventTask;
import seedu.address.model.task.Name;
import seedu.address.model.task.TypicalEventTasks;

public class EditEventCommandTest {

    private final TypicalEventTasks tet = new TypicalEventTasks();

    @Mock
    private Model model;

    @Before
    public void setup() throws Exception {
        MockitoAnnotations.initMocks(this);
        Mockito.when(model.getEventTask(1)).thenReturn(new EventTask("Lunch with Bill Gates", UNIX_EPOCH,
                                                                     UNIX_EPOCH.plusHours(1)));
    }

    @Test
    public void execute_withNewName_replacesName() throws Exception {
        final EventTask expected = new EventTask("Lunch with myself", UNIX_EPOCH, UNIX_EPOCH.plusHours(1));
        final EditEventCommand cmd = new EditEventCommand(1, Optional.of(new Name("Lunch with myself")),
                                                          Optional.empty(), Optional.empty(),
                                                          Optional.empty(), Optional.empty());
        final CommandResult result = cmd.execute(model);
        assertEquals("Edited event e1.", result.feedbackToUser);
        Mockito.verify(model).setEventTask(1, expected);
    }

    @Test
    public void execute_withNewStartDate_replacesStartDate() throws Exception {
        final EventTask expected = new EventTask("Lunch with Bill Gates",
                                                 LocalDateTime.of(1960, 1, 1, 0, 0),
                                                 UNIX_EPOCH.plusHours(1));
        final EditEventCommand cmd = new EditEventCommand(1, Optional.empty(),
                                                          Optional.of(LocalDate.of(1960, 1, 1)),
                                                          Optional.empty(), Optional.empty(),
                                                          Optional.empty());
        final CommandResult result = cmd.execute(model);
        assertEquals("Edited event e1.", result.feedbackToUser);
        Mockito.verify(model).setEventTask(1, expected);
    }

    @Test
    public void execute_withNewStartTime_replacesStartTime() throws Exception {
        final EventTask expected = new EventTask("Lunch with Bill Gates", UNIX_EPOCH.plusMinutes(1),
                                                 UNIX_EPOCH.plusHours(1));
        final EditEventCommand cmd = new EditEventCommand(1, Optional.empty(), Optional.empty(),
                                                          Optional.of(LocalTime.of(0, 1)), Optional.empty(),
                                                          Optional.empty());
        final CommandResult result = cmd.execute(model);
        assertEquals("Edited event e1.", result.feedbackToUser);
        Mockito.verify(model).setEventTask(1, expected);
    }

    @Test
    public void execute_withNewEndDate_replacesEndDate() throws Exception {
        final EventTask expected = new EventTask("Lunch with Bill Gates", UNIX_EPOCH,
                                                 LocalDateTime.of(2653, 9, 15, 1, 0));
        final EditEventCommand cmd = new EditEventCommand(1, Optional.empty(), Optional.empty(),
                                                          Optional.empty(),
                                                          Optional.of(LocalDate.of(2653, 9, 15)),
                                                          Optional.empty());
        final CommandResult result = cmd.execute(model);
        assertEquals("Edited event e1.", result.feedbackToUser);
        Mockito.verify(model).setEventTask(1, expected);
    }

    @Test
    public void execute_withNewEndTime_replacesEndTime() throws Exception {
        final EventTask expected = new EventTask("Lunch with Bill Gates", UNIX_EPOCH,
                                                LocalDateTime.of(1970, 1, 1, 12, 34));
        final EditEventCommand cmd = new EditEventCommand(1, Optional.empty(), Optional.empty(),
                                                          Optional.empty(), Optional.empty(),
                                                          Optional.of(LocalTime.of(12, 34)));
        final CommandResult result = cmd.execute(model);
        assertEquals("Edited event e1.", result.feedbackToUser);
        Mockito.verify(model).setEventTask(1, expected);
    }

}
