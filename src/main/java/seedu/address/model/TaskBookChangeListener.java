package seedu.address.model;

import seedu.address.commons.util.ObservableListChangeListener;

public class TaskBookChangeListener extends ObservableListChangeListener {

    public TaskBookChangeListener(ReadOnlyTaskBook taskBook) {
        super(taskBook.getFloatingTasks(), taskBook.getDeadlineTasks(), taskBook.getEventTasks());
    }

}
