package seedu.address.commons.time;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.time.TypicalLocalDateTimes.PI_DAY;
import static seedu.address.commons.time.TypicalLocalDateTimes.UNIX_EPOCH;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.exceptions.IllegalValueException;

public class LocalDateTimeDurationTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void constructor_validArguments_works() throws Exception {
        final LocalDateTime start = UNIX_EPOCH;
        final LocalDateTime end = UNIX_EPOCH.plusDays(1);
        final LocalDateTimeDuration zonedDateTimeDuration = new LocalDateTimeDuration(start, end);
        assertEquals(start, zonedDateTimeDuration.getStart());
        assertEquals(end, zonedDateTimeDuration.getEnd());
    }

    @Test
    public void constructor_zeroDuration_works() throws Exception {
        final LocalDateTime start = UNIX_EPOCH;
        final LocalDateTime end = UNIX_EPOCH;
        final LocalDateTimeDuration zonedDateTimeDuration = new LocalDateTimeDuration(start, end);
        assertEquals(start, zonedDateTimeDuration.getStart());
        assertEquals(end, zonedDateTimeDuration.getEnd());
    }

    @Test
    public void constructor_endBeforeBegin_throwsException() throws Exception {
        final LocalDateTime start = UNIX_EPOCH;
        final LocalDateTime end = UNIX_EPOCH.minusDays(1);
        thrown.expect(IllegalValueException.class);
        new LocalDateTimeDuration(start, end);
    }

    @Test
    public void addTo_ZonedDateTime_addsDuration() throws Exception {
        final LocalDateTime start = UNIX_EPOCH;
        final LocalDateTime end = UNIX_EPOCH.plusHours(1);
        final LocalDateTimeDuration duration = new LocalDateTimeDuration(start, end);

        final LocalDateTime expected = PI_DAY.plusHours(1);
        // LocalDateTime.plus() calls LocalDateTimeDuration.addTo() internally
        final LocalDateTime actual = PI_DAY.plus(duration);
        assertEquals(expected, actual);
    }

    @Test
    public void get_seconds_returnsCorrectResult() throws Exception {
        final LocalDateTime start = UNIX_EPOCH;
        final LocalDateTime end = UNIX_EPOCH.plusHours(1).plusMinutes(32);
        final LocalDateTimeDuration duration = new LocalDateTimeDuration(start, end);

        assertEquals(5520, duration.get(ChronoUnit.SECONDS));
    }

    @Test
    public void getUnits_returnsAtLeastOneUnit() throws Exception {
        final LocalDateTime start = UNIX_EPOCH;
        final LocalDateTime end = UNIX_EPOCH.plusHours(1);
        final LocalDateTimeDuration duration = new LocalDateTimeDuration(start, end);

        final List<TemporalUnit> units = duration.getUnits();
        assertTrue(units.size() > 0);
    }

    @Test
    public void subtractFrom_ZonedDateTime_subtractsDuration() throws Exception {
        final LocalDateTime start = UNIX_EPOCH;
        final LocalDateTime end = UNIX_EPOCH.plusHours(1);
        final LocalDateTimeDuration duration = new LocalDateTimeDuration(start, end);

        final LocalDateTime expected = PI_DAY.minusHours(1);
        // LocalDateTime.minus() calls LocalDateTimeDuration.subtractFrom() internally
        final LocalDateTime actual = PI_DAY.minus(duration);
        assertEquals(expected, actual);
    }

    @Test
    public void equals_isEquals_returnsTrue() throws Exception {
        final LocalDateTime start = UNIX_EPOCH;
        final LocalDateTime end = UNIX_EPOCH.plusHours(1);
        final LocalDateTimeDuration duration = new LocalDateTimeDuration(start, end);
        final LocalDateTimeDuration other = new LocalDateTimeDuration(start, end);
        assertTrue(duration.equals(other));
    }

    @Test
    public void equals_notEquals_returnsFalse() throws Exception {
        final LocalDateTime start = UNIX_EPOCH;
        final LocalDateTime end = UNIX_EPOCH.plusHours(1);
        final LocalDateTimeDuration duration = new LocalDateTimeDuration(start, end);
        final LocalDateTime otherEnd = UNIX_EPOCH.plusHours(2);
        final LocalDateTime otherStart = UNIX_EPOCH.minusHours(1);
        assertFalse(duration.equals(new LocalDateTimeDuration(start, otherEnd)));
        assertFalse(duration.equals(new LocalDateTimeDuration(otherStart, end)));
    }

}
