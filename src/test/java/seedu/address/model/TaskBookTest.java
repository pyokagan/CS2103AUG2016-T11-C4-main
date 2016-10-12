package seedu.address.model;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collections;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.task.EventTask;
import seedu.address.model.task.FloatingTask;
import seedu.address.model.task.TypicalEventTasks;
import seedu.address.model.task.TypicalFloatingTasks;

public class TaskBookTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private TaskBook taskBook;

    private static TypicalFloatingTasks typicalFloatingTasks = new TypicalFloatingTasks();

    private static TypicalEventTasks typicalEventTasks = new TypicalEventTasks();

    @Before
    public void setupTaskBook() {
        taskBook = new TaskBook();
    }

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), taskBook.getTasks());
        assertEquals(Collections.emptyList(), taskBook.getFloatingTasks());
        assertEquals(Collections.emptyList(), taskBook.getEventTasks());
    }

    @Test
    public void setFloatingTasks() {
        taskBook.setFloatingTasks(typicalFloatingTasks.getFloatingTasks());
        assertEquals(typicalFloatingTasks.getFloatingTasks(), taskBook.getFloatingTasks());
    }

    @Test
    public void addFloatingTask_appendsToList() {
        final ArrayList<FloatingTask> expected = new ArrayList<>();
        taskBook.addFloatingTask(typicalFloatingTasks.readABook);
        expected.add(typicalFloatingTasks.readABook);
        assertEquals(expected, taskBook.getFloatingTasks());
        taskBook.addFloatingTask(typicalFloatingTasks.buyAHelicopter);
        expected.add(typicalFloatingTasks.buyAHelicopter);
        assertEquals(expected, taskBook.getFloatingTasks());
    }

    @Test
    public void removeFloatingTask_removesFromList() {
        final ArrayList<FloatingTask> expected = new ArrayList<>(typicalFloatingTasks.getFloatingTasks());
        expected.remove(0);
        taskBook.setFloatingTasks(typicalFloatingTasks.getFloatingTasks());
        taskBook.removeFloatingTask(0);
        assertEquals(expected, taskBook.getFloatingTasks());
    }

    @Test
    public void setEventTasks() {
        taskBook.setEventTasks(typicalEventTasks.getEventTasks());
        assertEquals(typicalEventTasks.getEventTasks(), taskBook.getEventTasks());
    }

    @Test
    public void addEventTask_appendsToList() {
        final ArrayList<EventTask> expected = new ArrayList<>();
        taskBook.addEventTask(typicalEventTasks.lunchWithBillGates);
        expected.add(typicalEventTasks.lunchWithBillGates);
        assertEquals(expected, taskBook.getEventTasks());
        taskBook.addEventTask(typicalEventTasks.launchNuclearWeapons);
        expected.add(typicalEventTasks.launchNuclearWeapons);
        assertEquals(expected, taskBook.getEventTasks());
    }

    @Test
    public void removeEventTask_removesFromList() {
        final ArrayList<EventTask> expected = new ArrayList<>(typicalEventTasks.getEventTasks());
        expected.remove(0);
        taskBook.setEventTasks(typicalEventTasks.getEventTasks());
        taskBook.removeEventTask(0);
        assertEquals(expected, taskBook.getEventTasks());
    }

}
