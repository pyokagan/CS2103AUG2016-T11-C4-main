package seedu.address.model.task;

import java.util.Objects;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Floating in the TaskTracker.
 * TODO FloatingTaskTest
 */

public class FloatingTask extends Task {

    private static final String FMT_STRING = "FloatingTask[name=%s, priority=%s, finished=%s]";

    private Priority priority;

    private final boolean finished;

    public FloatingTask(Name name, Priority priority, boolean finished) {
        super(name);
        assert priority != null;
        this.priority = priority;
        this.finished = finished;
    }

    public FloatingTask(Name name, Priority priority) {
        this(name, priority, false);
    }

    public FloatingTask(String name, Priority priority) throws IllegalValueException {
        this(new Name(name), priority, false);
    }

    public FloatingTask(String name) throws IllegalValueException {
        this(new Name(name), new Priority("0"), false);
    }

    public Priority getPriority() {
        return this.priority;
    }

    public boolean isFinished() {
        return this.finished;
    }

    @Override
    public boolean equals(Object other) {
        return other == this
            || (other instanceof FloatingTask
            && name.equals(((FloatingTask)other).name)
            && priority.equals(((FloatingTask)other).priority)
            && finished == ((FloatingTask)other).finished);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, priority);
    }

    @Override
    public String toString() {
        return String.format(FMT_STRING, name, priority, finished);
    }
}
