package seedu.address.model;

import java.util.Comparator;
import java.util.Optional;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.ModelManager.HeadAtBoundaryException;
import seedu.address.model.filter.TaskPredicate;
import seedu.address.model.task.DeadlineTask;
import seedu.address.model.task.EventTask;
import seedu.address.model.task.FloatingTask;
import seedu.address.model.task.TaskSelect;

/**
 * The API of the Model component.
 */
public interface Model extends ReadOnlyModel {

    //// Config

    /** Sets configured task book file path */
    void setTaskBookFilePath(String taskBookFilePath);

    //// Task Book

    /** Clears existing backing task book and replaces with the provided new task book data. */
    void resetTaskBook(ReadOnlyTaskBook newTaskBook);

    //// Task Select

    /** Sets the task being selected. */
    void setTaskSelect(Optional<TaskSelect> taskSelect);

    //// Task Filtering

    /** Sets the {@link TaskPredicate} used to filter tasks. If the filter is null, no filter is applied. */
    void setTaskPredicate(TaskPredicate taskPredicate);

    //// Floating Tasks

    /* Adds the given floating task and returns its working index. */
    int addFloatingTask(FloatingTask floatingTask);

    /** Removes the given Floating task and returns it. */
    FloatingTask removeFloatingTask(int workingIndex) throws IllegalValueException;

    /** Removes the all Floating tasks satisfy the given predicate. */
    void removeFloatingTasks(TaskPredicate taskPredicate);

    /** Replaces the given Floating task with a new Floating task */
    void setFloatingTask(int workingIndex, FloatingTask newFloatingTask) throws IllegalValueException;

    /** Sets the comparator used to sort the floating task list. */
    void setFloatingTaskComparator(Comparator<? super FloatingTask> comparator);

    //// Deadline Tasks

    /** Adds the given deadline task and returns its working index. */
    int addDeadlineTask(DeadlineTask deadlineTask);

    /** Removes the given deadline task and returns it. */
    DeadlineTask removeDeadlineTask(int workingIndex) throws IllegalValueException;

    /** Removes the all Deadline tasks satisfy the given predicate. */
    void removeDeadlineTasks(TaskPredicate taskPredicate);

    /** Replaces the given deadline task with a new deadline task */
    void setDeadlineTask(int workingIndex, DeadlineTask newDeadlineTask) throws IllegalValueException;

    /** Sets the comparator used to sort the deadline task list. */
    void setDeadlineTaskComparator(Comparator<? super DeadlineTask> comparator);

    //// Event Tasks

    /** Adds the given event task and returns its working index */
    int addEventTask(EventTask eventTask);

    /** Removes the given event task and returns it. */
    EventTask removeEventTask(int workingIndex) throws IllegalValueException;

    /** Removes the all Event tasks satisfy the given predicate. */
    void removeEventTasks(TaskPredicate taskPredicate);

    /** Replaces the given event task with a new event task */
    void setEventTask(int workingIndex, EventTask newEventTask) throws IllegalValueException;

    /** Sets the comparator used to sort the event task list. */
    void setEventTaskComparator(Comparator<? super EventTask> comparator);

    //// undo/redo

    /**
     * Saves the state of the model as a commit.
     * @param name The name of the commit.
     * @return The new commit.
     */
    Commit recordState(String name);

    /**
     * Redoes the most recently undone commit.
     * @return The commit that was redone.
     * @throws HeadAtBoundaryException if there are no more commits to redo.
     */
    Commit redo() throws HeadAtBoundaryException;

    /**
     * Undoes the most recent commit.
     * @return the commit that was undone.
     * @throws HeadAtBoundaryException
     */
    Commit undo() throws HeadAtBoundaryException;

    public interface Commit {
        /** The name of the commit */
        String getName();
    }

}
