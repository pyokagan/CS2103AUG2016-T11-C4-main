package seedu.address.model.task;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.time.TypicalLocalDateTimes.UNIX_EPOCH;

import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.Test;

import seedu.address.commons.time.LocalDateTimeDuration;

public class EventTaskTest {

    private EventTask eventTask;

    @Before
    public void setupEventTask() throws Exception {
        final Name name = new Name("Event Task Name");
        final LocalDateTime start = UNIX_EPOCH;
        final LocalDateTime end = UNIX_EPOCH.plusHours(1);
        final LocalDateTimeDuration duration = new LocalDateTimeDuration(start, end);
        eventTask = new EventTask(name, duration);
    }

    @Test
    public void getDuration_returnsDuration() throws Exception {
        final LocalDateTimeDuration expected = new LocalDateTimeDuration(UNIX_EPOCH, UNIX_EPOCH.plusHours(1));
        final LocalDateTimeDuration actual = eventTask.getDuration();
        assertEquals(expected, actual);
    }

    @Test
    public void getStart_returnsStart() throws Exception {
        final LocalDateTime expected = UNIX_EPOCH;
        final LocalDateTime actual = eventTask.getStart();
        assertEquals(expected, actual);
    }

    @Test
    public void getEnd_returnsEnd() throws Exception {
        final LocalDateTime expected = UNIX_EPOCH.plusHours(1);
        final LocalDateTime actual = eventTask.getEnd();
        assertEquals(expected, actual);
    }

    @Test
    public void equals_isEqual_returnsTrue() throws Exception {
        final EventTask other = new EventTask("Event Task Name", UNIX_EPOCH, UNIX_EPOCH.plusHours(1));
        assertTrue(eventTask.equals(other));
        assertTrue(eventTask.hashCode() == other.hashCode());
    }

    @Test
    public void equals_notEqual_returnsFalse() throws Exception {
        final EventTask other = new EventTask(new Name("Event Task Name"), UNIX_EPOCH, UNIX_EPOCH.plusHours(2));
        assertFalse(eventTask.equals(other));
        assertFalse(eventTask.hashCode() == other.hashCode());
    }

    @Test
    public void toString_returnsCorrectFormat() {
        final String expected = "EventTask[name=Event Task Name, "
                                + "duration=LocalDateTimeDuration[start=1970-01-01T00:00, "
                                + "end=1970-01-01T01:00]]";
        final String actual = eventTask.toString();
        assertEquals(expected, actual);
    }

}
