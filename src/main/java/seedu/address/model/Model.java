package seedu.address.model;

import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.exceptions.IllegalValueException;
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

    /* Adds the given floating task */
    void addFloatingTask(FloatingTask floatingTask);

    /** Retrieves the given Floating task from the specified index in the filtered Floating task list */
    FloatingTask getFloatingTask(int indexInFilteredList) throws IllegalValueException;

    /** Removes the given Floating task and returns it. */
    FloatingTask removeFloatingTask(int indexInFilteredList) throws IllegalValueException;

    /** Replaces the given Floating task with a new Floating task */
    void setFloatingTask(int indexInFilteredList, FloatingTask newFloatingTask) throws IllegalValueException;

    /** Returns the filtered Floating task list as an unmodifiable ObservableList */
    ObservableList<FloatingTask> getFilteredFloatingTaskList();

    /**
     * Updates the filter of the filtered Floating task list to filter by the given predicate.
     *
     * If predicate is null, the filtered Floating task list will be populated with all Floating tasks.
     */
    void setFloatingTaskFilter(Predicate<? super FloatingTask> predicate);

    /**
     * Updates the filter of the filtered floating task list to remove all finished tasks from the list.
     */
    void setIsFinishedFloatingTaskFilter();

    //// Deadline Tasks

    /** Adds the given deadline task */
    void addDeadlineTask(DeadlineTask deadlineTask);

    /** Retrieves the given deadline task from the specified index in the filtered deadline task list */
    DeadlineTask getDeadlineTask(int indexInFilteredList) throws IllegalValueException;

    /** Removes the given deadline task and returns it. */
    DeadlineTask removeDeadlineTask(int indexInFilteredList) throws IllegalValueException;

    /** Replaces the given deadline task with a new deadline task */
    void setDeadlineTask(int indexInFilteredList, DeadlineTask newDeadlineTask) throws IllegalValueException;

    /** Returns the filtered deadline task list as an unmodifiable ObservableList */
    ObservableList<DeadlineTask> getFilteredDeadlineTaskList();

    /**
     * Updates the filter of the filtered deadline task list to filter by the given predicate.
     *
     * If predicate is null, the filtered deadline task list will be populated with all deadline tasks.
     */
    void setDeadlineTaskFilter(Predicate<? super DeadlineTask> predicate);

    /**
     * Updates the filter of the filtered deadline task list to remove all finished tasks from the list.
     */
    void setIsFinishedDeadlineFilter();

    //// Event Tasks

    /** Adds the given event task */
    void addEventTask(EventTask eventTask);

    /** Retrieves the given event task from the specified index in the filtered event task list */
    EventTask getEventTask(int indexInFilteredList) throws IllegalValueException;

    /** Removes the given event task and returns it. */
    EventTask removeEventTask(int indexInFilteredList) throws IllegalValueException;

    /** Replaces the given event task with a new event task */
    void setEventTask(int indexInFilteredList, EventTask newEventTask) throws IllegalValueException;

    /** Returns the filtered event task list as an unmodifiable ObservableList */
    ObservableList<EventTask> getFilteredEventTaskList();

    /**
     * Updates the filter of the filtered event task list to filter by the given predicate.
     *
     * If predicate is null, the filtered event task list will be populated with all event tasks.
     */
    void setEventTaskFilter(Predicate<? super EventTask> predicate);

}
