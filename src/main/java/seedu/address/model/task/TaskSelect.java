package seedu.address.model.task;

import com.google.common.base.MoreObjects;

/**
 * Represents a task that is being selected.
 * Guarantees: immutable, a POJO.
 */
public class TaskSelect {
    /** The type of task being selected. */
    private final TaskType taskType;

    /** The working index of the task being selected. */
    private final int workingIndex;

    public TaskSelect(TaskType taskType, int workingIndex) {
        this.taskType = taskType;
        this.workingIndex = workingIndex;
    }

    public TaskType getTaskType() {
        return taskType;
    }

    public int getWorkingIndex() {
        return workingIndex;
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof TaskSelect
                && taskType == ((TaskSelect)other).taskType
                && workingIndex == ((TaskSelect)other).workingIndex);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("taskType", taskType)
                .add("workingIndex", workingIndex)
                .toString();
    }

}
