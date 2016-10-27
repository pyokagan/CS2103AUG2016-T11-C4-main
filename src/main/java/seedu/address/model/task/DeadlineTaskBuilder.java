package seedu.address.model.task;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * A builder for {@link DeadlineTask}
 */
public class DeadlineTaskBuilder {
    private Name name;
    private LocalDate dueDate;
    private LocalTime dueTime;
    private boolean finished;

    public DeadlineTaskBuilder(Name name, LocalDateTime due, boolean finished) {
        setName(name);
        setDue(due);
        setFinished(finished);
    }

    public DeadlineTaskBuilder(DeadlineTask template) {
        this(template.getName(), template.getDue(), template.isFinished());
    }

    public DeadlineTaskBuilder setName(Name name) {
        assert name != null;
        this.name = name;
        return this;
    }

    public DeadlineTaskBuilder setName(String name) throws IllegalValueException {
        this.name = new Name(name);
        return this;
    }

    public DeadlineTaskBuilder setDue(LocalDateTime due) {
        assert due != null;
        setDueDate(due.toLocalDate());
        setDueTime(due.toLocalTime());
        return this;
    }

    public DeadlineTaskBuilder setDueDate(LocalDate dueDate) {
        assert dueDate != null;
        this.dueDate = dueDate;
        return this;
    }

    public DeadlineTaskBuilder setDueTime(LocalTime dueTime) {
        assert dueTime != null;
        this.dueTime = dueTime;
        return this;
    }

    public DeadlineTaskBuilder setFinished(boolean finished) {
        this.finished = finished;
        return this;
    }

    public DeadlineTask build() {
        return new DeadlineTask(name, LocalDateTime.of(dueDate, dueTime), finished);
    }
}
