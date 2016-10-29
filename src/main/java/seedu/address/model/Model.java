package seedu.address.model;

import java.util.Comparator;
import java.util.Optional;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.Command;
import seedu.address.model.ModelManager.HeadAtBoundaryException;
import seedu.address.model.config.ReadOnlyConfig;
import seedu.address.model.task.DeadlineTask;
import seedu.address.model.task.EventTask;
import seedu.address.model.task.FloatingTask;

/**
 * The API of the Model component.
 */
public interface Model {

    //// Config

    /** Returns current config as a read-only view */
    ReadOnlyConfig getConfig();

    /** Returns configured task book file path. */
    String getTaskBookFilePath();

    /** Sets configured task book file path */
    void setTaskBookFilePath(String taskBookFilePath);

    //// Task Book

    /** Clears existing backing task book and replaces with the provided new task book data. */
    void resetTaskBook(ReadOnlyTaskBook newTaskBook);

    /** Returns the TaskBook */
    ReadOnlyTaskBook getTaskBook();

    //// Floating Tasks

    /* Adds the given floating task and returns its working index. */
    int addFloatingTask(FloatingTask floatingTask);

    /** Retrieves the given floating task given its working index. */
    FloatingTask getFloatingTask(int workingIndex) throws IllegalValueException;

    /** Removes the given Floating task and returns it. */
    FloatingTask removeFloatingTask(int workingIndex) throws IllegalValueException;

    /** Replaces the given Floating task with a new Floating task */
    void setFloatingTask(int workingIndex, FloatingTask newFloatingTask) throws IllegalValueException;

    /** Returns the filtered Floating task list as an unmodifiable ObservableList */
    ObservableList<IndexedItem<FloatingTask>> getFloatingTaskList();

    /**
     * Updates the filter of the filtered Floating task list to filter by the given predicate.
     *
     * If predicate is null, the filtered Floating task list will be populated with all Floating tasks.
     */
    void setFloatingTaskPredicate(Predicate<? super FloatingTask> predicate);

    /** Returns the comparator used to sort the floating task list. */
    Comparator<? super FloatingTask> getFloatingTaskComparator();

    /** Sets the comparator used to sort the floating task list. */
    void setFloatingTaskComparator(Comparator<? super FloatingTask> comparator);

    //// Deadline Tasks

    /** Adds the given deadline task and returns its working index. */
    int addDeadlineTask(DeadlineTask deadlineTask);

    /** Retrieves the given deadline task from the specified index in the filtered deadline task list */
    DeadlineTask getDeadlineTask(int workingIndex) throws IllegalValueException;

    /** Removes the given deadline task and returns it. */
    DeadlineTask removeDeadlineTask(int workingIndex) throws IllegalValueException;

    /** Replaces the given deadline task with a new deadline task */
    void setDeadlineTask(int workingIndex, DeadlineTask newDeadlineTask) throws IllegalValueException;

    /** Returns the filtered deadline task list as an unmodifiable ObservableList */
    ObservableList<IndexedItem<DeadlineTask>> getDeadlineTaskList();

    /**
     * Updates the filter of the filtered deadline task list to filter by the given predicate.
     *
     * If predicate is null, the filtered deadline task list will be populated with all deadline tasks.
     */
    void setDeadlineTaskPredicate(Predicate<? super DeadlineTask> predicate);

    /** Returns the comparator used to sort the deadline task list. */
    Comparator<? super DeadlineTask> getDeadlineTaskComparator();

    /** Sets the comparator used to sort the deadline task list. */
    void setDeadlineTaskComparator(Comparator<? super DeadlineTask> comparator);

    //// Event Tasks

    /** Adds the given event task and returns its working index */
    int addEventTask(EventTask eventTask);

    /** Retrieves the given event task with the specified working index */
    EventTask getEventTask(int workingIndex) throws IllegalValueException;

    /** Removes the given event task and returns it. */
    EventTask removeEventTask(int workingIndex) throws IllegalValueException;

    /** Replaces the given event task with a new event task */
    void setEventTask(int workingIndex, EventTask newEventTask) throws IllegalValueException;

    /** Returns the filtered event task list as an unmodifiable ObservableList */
    ObservableList<IndexedItem<EventTask>> getEventTaskList();

    /**
     * Updates the filter of the filtered event task list to filter by the given predicate.
     *
     * If predicate is null, the filtered event task list will be populated with all event tasks.
     */
    void setEventTaskPredicate(Predicate<? super EventTask> predicate);

    /** Returns the comparator used to sort the event task list. */
    Comparator<? super EventTask> getEventTaskComparator();

    /** Sets the comparator used to sort the event task list. */
    void setEventTaskComparator(Comparator<? super EventTask> comparator);

    ////undo redo
    /**
     * saves a copy of the current TaskBook, and the Command that causes the change from the previous TaskBook to the current TaskBook.
     * @param command
     */
    void recordState(Command command);

    /**
     * resets the TaskBook to the TaskBook before it was changed by the most recent undo.
     * @return the Command that was redone
     * @throws HeadAtBoundaryException
     */
    Command redo() throws HeadAtBoundaryException;

    /**
     * resets the TaskBook to it's previous state.
     * @return the Command that was undone
     * @throws HeadAtBoundaryException
     */
    Command undo() throws HeadAtBoundaryException;

    /**
     *@return true if TaskBook has changed
     *Unlikely the commits list will be empty since recordState() is called when ModelManager is initialised. Thus there will always be at least one Commit in commits.
     */
    boolean hasUncommittedChanges();

}
