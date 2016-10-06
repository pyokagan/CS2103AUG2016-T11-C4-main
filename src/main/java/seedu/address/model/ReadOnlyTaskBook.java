package seedu.address.model;

import java.util.List;

import seedu.address.model.task.ReadOnlyTask;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyTaskBook {

    /**
     * Returns an unmodifiable view of tasks list
     */
    List<ReadOnlyTask> getTaskList();

}
