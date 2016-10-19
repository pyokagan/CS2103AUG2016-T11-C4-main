package seedu.address.model.task;

import java.time.LocalDateTime;
import java.util.Objects;

import java.util.function.Predicate;

import seedu.address.commons.exceptions.IllegalValueException;

public class DeadlineTask extends Task {

    private static final String FMT_STRING = "DeadlineTask[name=%s, due=%s, finished=%s]";

    private final LocalDateTime due;

    private boolean finished;

    public static Predicate<? super DeadlineTask> isNotFinishedDeadline() {
        return p -> !p.isFinished();
    }

    public DeadlineTask(Name name, LocalDateTime due, boolean finished) {
        super(name);
        assert due != null;
        this.due = due;
        this.finished = finished;
    }

    public DeadlineTask(Name name, LocalDateTime due) {
        this(name, due, false);
    }

    public DeadlineTask(String name, LocalDateTime due) throws IllegalValueException {
        this(new Name(name), due, false);
    }

    public LocalDateTime getDue() {
        return due;
    }

    public boolean isFinished() {
        return this.finished;
    }

    public void markAsFinished() {
        assert this.finished == false;
        this.finished = true;
    }

    @Override
    public boolean equals(Object other) {
        return other == this
               || (other instanceof DeadlineTask
               && name.equals(((DeadlineTask)other).name)
               && due.equals(((DeadlineTask)other).due));
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, due);
    }

    @Override
    public String toString() {
        return String.format(FMT_STRING, name, due, finished);
    }

}
