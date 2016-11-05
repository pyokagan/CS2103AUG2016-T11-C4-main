package seedu.address.model;

import java.time.LocalDateTime;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.task.DeadlineTask;
import seedu.address.model.task.EventTask;
import seedu.address.model.task.FloatingTask;
import seedu.address.model.task.Name;
import seedu.address.model.task.Priority;
import seedu.address.model.task.TypicalDeadlineTasks;
import seedu.address.model.task.TypicalEventTasks;
import seedu.address.model.task.TypicalFloatingTasks;

/**
 * Exposes a fluent API for building a {@link TaskBook} for use in tests.
 */
public class TaskBookBuilder {

    private TaskBook taskBook;

    public TaskBookBuilder() {
        this.taskBook = new TaskBook();
    }

    public TaskBookBuilder(ReadOnlyTaskBook taskBook) {
        this.taskBook = new TaskBook(taskBook);
    }

    /**
     * Adds a {@link FloatingTask} to the task book.
     */
    public TaskBookBuilder addFloatingTask(FloatingTask floatingTask) {
        taskBook.addFloatingTask(floatingTask);
        return this;
    }

    /**
     * @see #addFloatingTask(FloatingTask)
     */
    public TaskBookBuilder addFloatingTask(String name, int priority) {
        try {
            addFloatingTask(new FloatingTask(new Name(name), new Priority(Integer.toString(priority))));
            return this;
        } catch (IllegalValueException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Adds a list of {@link FloatingTask} to the task book.
     */
    public TaskBookBuilder addFloatingTasks(Iterable<FloatingTask> floatingTasks) {
        for (FloatingTask floatingTask : floatingTasks) {
            addFloatingTask(floatingTask);
        }
        return this;
    }

    /**
     * Adds a list of typical floating tasks to the task book.
     * @see TypicalFloatingTasks
     */
    public TaskBookBuilder addTypicalFloatingTasks() {
        final TypicalFloatingTasks tft = new TypicalFloatingTasks();
        return addFloatingTasks(tft.getFloatingTasks());
    }

    /**
     * Adds a {@link DeadlineTask} to the task book.
     */
    public TaskBookBuilder addDeadlineTask(DeadlineTask deadlineTask) {
        taskBook.addDeadlineTask(deadlineTask);
        return this;
    }

    /**
     * @see #addDeadlineTask(DeadlineTask)
     */
    public TaskBookBuilder addDeadlineTask(String name, LocalDateTime due, boolean finished) {
        try {
            addDeadlineTask(new DeadlineTask(new Name(name), due, finished));
            return this;
        } catch (IllegalValueException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @see #addDeadlineTask(String, LocalDateTime, boolean)
     */
    public TaskBookBuilder addDeadlineTask(String name, LocalDateTime due) {
        return addDeadlineTask(name, due, false);
    }

    /**
     * Adds a list of {@link DeadlineTask} to the task book.
     */
    public TaskBookBuilder addDeadlineTasks(Iterable<DeadlineTask> deadlineTasks) {
        for (DeadlineTask deadlineTask : deadlineTasks) {
            addDeadlineTask(deadlineTask);
        }
        return this;
    }

    /**
     * Adds a list of typical deadline tasks to the task book.
     */
    public TaskBookBuilder addTypicalDeadlineTasks() {
        final TypicalDeadlineTasks tdt = new TypicalDeadlineTasks();
        return addDeadlineTasks(tdt.getDeadlineTasks());
    }

    /**
     * Adds a {@link EventTask} to the task book.
     */
    public TaskBookBuilder addEventTask(EventTask eventTask) {
        taskBook.addEventTask(eventTask);
        return this;
    }

    /**
     * @see #addEventTask(EventTask)
     */
    public TaskBookBuilder addEventTask(String name, LocalDateTime start, LocalDateTime end) {
        try {
            addEventTask(new EventTask(name, start, end));
            return this;
        } catch (IllegalValueException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Adds a list of {@link EventTask} to the task book.
     */
    public TaskBookBuilder addEventTasks(Iterable<EventTask> eventTasks) {
        for (EventTask eventTask : eventTasks) {
            addEventTask(eventTask);
        }
        return this;
    }

    /**
     * Adds a list of typical event tasks to the task book.
     * @see TypicalEventTasks
     */
    public TaskBookBuilder addTypicalEventTasks() {
        final TypicalEventTasks tet = new TypicalEventTasks();
        return addEventTasks(tet.getEventTasks());
    }

    /**
     * Adds a list of typical floating, deadline and event tasks to the task book.
     */
    public TaskBookBuilder addTypicalTasks() {
        addTypicalFloatingTasks();
        addTypicalDeadlineTasks();
        addTypicalEventTasks();
        return this;
    }

    /**
     * Returns the built {@link TaskBook}
     */
    public TaskBook build() {
        return taskBook;
    }

}
