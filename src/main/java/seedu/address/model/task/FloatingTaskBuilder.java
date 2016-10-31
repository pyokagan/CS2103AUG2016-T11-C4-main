package seedu.address.model.task;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * A builder for {@link FloatingTask}
 */
public class FloatingTaskBuilder {
    private Name name;
    private Priority priority;
    private boolean finished;

    public FloatingTaskBuilder(Name name, Priority priority, boolean finished) {
        setName(name);
        setPriority(priority);
        setFinished(finished);
    }

    public FloatingTaskBuilder(FloatingTask template) {
        this(template.getName(), template.getPriority(), template.isFinished());
    }

    public FloatingTaskBuilder setName(Name name) {
        assert name != null;
        this.name = name;
        return this;
    }

    public FloatingTaskBuilder setName(String name) throws IllegalValueException {
        this.name = new Name(name);
        return this;
    }

    public FloatingTaskBuilder setPriority(Priority priority) {
        assert priority != null;
        this.priority = priority;
        return this;
    }

    public FloatingTaskBuilder setFinished(boolean finished) {
        this.finished = finished;
        return this;
    }

    public FloatingTask build() {
        return new FloatingTask(name, priority, finished);
    }
}
