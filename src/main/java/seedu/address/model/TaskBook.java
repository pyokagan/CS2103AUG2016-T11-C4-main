package seedu.address.model;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Task;
import seedu.address.model.task.TaskNotFoundException;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .equals comparison)
 */
public class TaskBook implements ReadOnlyTaskBook {

    private final ObservableList<Task> tasks;

    public TaskBook() {
        tasks = FXCollections.observableArrayList();
    }

    /**
     * Tasks are copied into this TaskBook.
     */
    public TaskBook(ReadOnlyTaskBook toBeCopied) {
        this(toBeCopied.getTaskList());
    }

    /**
     * Tasks are copied into this TaskBook.
     */
    public TaskBook(List<ReadOnlyTask> tasks) {
        this();
        resetData(tasks);
    }

    //// list overwrite operations

    public ObservableList<Task> getTasks() {
        return FXCollections.unmodifiableObservableList(tasks);
    }

    public void setTasks(List<Task> persons) {
        this.tasks.setAll(persons);
    }

    public void resetData(Collection<? extends ReadOnlyTask> newPersons) {
        setTasks(newPersons.stream().map(Task::new).collect(Collectors.toList()));
    }

    public void resetData(ReadOnlyTaskBook newData) {
        resetData(newData.getTaskList());
    }

    //// task-level operations

    /**
     * Adds a task to the task book.
     * Also checks the new tasks's tags and updates {@link #tags} with any new tags found,
     * and updates the Tag objects in the task to point to those in {@link #tags}.
     */
    public void addTask(Task task) {
        tasks.add(task);
    }

    public boolean removeTask(ReadOnlyTask key) throws TaskNotFoundException {
        if (tasks.remove(key)) {
            return true;
        } else {
            throw new TaskNotFoundException();
        }
    }

    //// util methods

    @Override
    public String toString() {
        return tasks.size() + " tasks";
        // TODO: refine later
    }

    @Override
    public List<ReadOnlyTask> getTaskList() {
        return Collections.unmodifiableList(tasks);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TaskBook // instanceof handles nulls
                && this.tasks.equals(((TaskBook) other).tasks));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(tasks);
    }
}
