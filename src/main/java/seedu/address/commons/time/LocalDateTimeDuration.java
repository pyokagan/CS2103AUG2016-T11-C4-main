package seedu.address.commons.time;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAmount;
import java.time.temporal.TemporalUnit;
import java.util.List;
import java.util.Objects;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.CollectionUtil;

/**
 * Represents a period bounded by two LocalDateTimes.
 *
 * Guarantees: Immutable POJO with non-null values. End LocalDateTime is after start LocalDateTime.
 */
public final class LocalDateTimeDuration implements Comparable<LocalDateTimeDuration>, TemporalAmount {

    private static final String FMT_STRING = "LocalDateTimeDuration[start=%s, end=%s]";

    private final LocalDateTime start;

    private final LocalDateTime end;

    private final Duration duration;

    public LocalDateTimeDuration(LocalDateTime start, LocalDateTime end) throws IllegalValueException {
        assert !CollectionUtil.isAnyNull(start, end);
        if (end.isBefore(start)) {
            throw new IllegalValueException("end datetime must be after start datetime");
        }
        this.start = start;
        this.end = end;
        this.duration = Duration.between(start, end);
    }

    public LocalDateTime getStart() {
        return start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    @Override
    public Temporal addTo(Temporal temporal) {
        return temporal.plus(duration);
    }

    @Override
    public long get(TemporalUnit unit) {
        return duration.get(unit);
    }

    @Override
    public List<TemporalUnit> getUnits() {
        return duration.getUnits();
    }

    @Override
    public Temporal subtractFrom(Temporal temporal) {
        return temporal.minus(duration);
    }

    @Override
    public int compareTo(LocalDateTimeDuration other) {
        int cmp = start.compareTo(other.start);
        if (cmp != 0) {
            return cmp;
        }
        return start.compareTo(other.end);
    }

    @Override
    public boolean equals(Object other) {
        return other == this
               || (other instanceof LocalDateTimeDuration
               && start.equals(((LocalDateTimeDuration)other).start)
               && end.equals(((LocalDateTimeDuration)other).end));
    }

    @Override
    public int hashCode() {
        return Objects.hash(start, end);
    }

    @Override
    public String toString() {
        return String.format(FMT_STRING, start, end);
    }

}
