package seedu.address.model.task;

import java.time.LocalDateTime;
import java.util.Objects;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.time.LocalDateTimeDuration;

public final class EventTask extends Task {

    private static final String FMT_STRING = "EventTask[name=%s, duration=%s]";

    private final LocalDateTimeDuration duration;

    public EventTask(Name name, LocalDateTimeDuration duration) {
        super(name);
        assert duration != null;
        this.duration = duration;
    }

    public EventTask(String name, LocalDateTimeDuration duration) throws IllegalValueException {
        this(new Name(name), duration);
    }

    public EventTask(Name name, LocalDateTime start, LocalDateTime end) throws IllegalValueException {
        this(name, new LocalDateTimeDuration(start, end));
    }

    public EventTask(String name, LocalDateTime start, LocalDateTime end) throws IllegalValueException {
        this(name, new LocalDateTimeDuration(start, end));
    }

    public LocalDateTimeDuration getDuration() {
        return duration;
    }

    public LocalDateTime getStart() {
        return duration.getStart();
    }

    public LocalDateTime getEnd() {
        return duration.getEnd();
    }

    @Override
    public boolean equals(Object other) {
        return other == this
               || (other instanceof EventTask
               && name.equals(((EventTask)other).name)
               && duration.equals(((EventTask)other).duration));
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, duration);
    }

    @Override
    public String toString() {
        return String.format(FMT_STRING, name, duration);
    }

}
