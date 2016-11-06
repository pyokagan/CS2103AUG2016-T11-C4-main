package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.filter.TaskPredicate;
import seedu.address.model.task.DeadlineTask;
import seedu.address.model.task.EventTask;
import seedu.address.model.task.FloatingTask;

/**
 * Unmodifiable view of a task book.
 */
public interface ReadOnlyTaskBook {

    /**
     * Returns an unmodifiable view of the FloatingTasks list.
     */
    ObservableList<FloatingTask> getFloatingTasks();

    /**
     * Returns an unmodifiable view of a filtered Floating Tasks list filtered by given predicate.
     */
    ObservableList<FloatingTask> getFloatingTasks(TaskPredicate predicate);

    /**
     * Returns an unmodifiable view of the DeadlineTasks list.
     */
    ObservableList<DeadlineTask> getDeadlineTasks();

    /**
     * Returns an unmodifiable view of a filtered Deadline Tasks list filtered by given predicate.
     */
    ObservableList<DeadlineTask> getDeadlineTasks(TaskPredicate predicate);

    /**
     * Returns an unmodifiable view of the EventTasks list.
     */
    ObservableList<EventTask> getEventTasks();

    /**
     * Returns an unmodifiable view of a filtered Event Tasks list filtered by given predicate.
     */
    ObservableList<EventTask> getEventTasks(TaskPredicate predicate);

}
