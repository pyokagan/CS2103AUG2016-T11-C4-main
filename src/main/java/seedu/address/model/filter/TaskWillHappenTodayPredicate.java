package seedu.address.model.filter;

import java.time.LocalDate;
import java.time.LocalDateTime;

import seedu.address.model.task.DeadlineTask;
import seedu.address.model.task.EventTask;
import seedu.address.model.task.FloatingTask;
import seedu.address.model.task.Task;

/**
 * A predicate that returns true if the {@link Task} happens today/in the reference.
 * <p><ul>
 * <li> Floating tasks all happens today if {@link FloatingTask#isFinished()} returns false.
 * <li> Deadline tasks happens today when {@link DeadlineTask#getDue()} is in reference date
 *      and when {@link DeadlineTask#isFinished()} returns false.
 * <li> Event tasks happens today when their start/end time is before the end time of the reference date
 *      and after the reference datetime or the start time is before the reference datetime and the end
 *      time is after the reference datetime.
 * </ul>
 */
public class TaskWillHappenTodayPredicate implements TaskPredicate {
    private final LocalDateTime referenceDateTime;
    private final LocalDate today;
    private final LocalDate tomorrow;

    public TaskWillHappenTodayPredicate(LocalDateTime referenceDateTime) {
        assert referenceDateTime != null;
        this.referenceDateTime = referenceDateTime;
        this.today = this.referenceDateTime.toLocalDate();
        this.tomorrow = this.today.plusDays(1);
    }

    @Override
    public boolean test(FloatingTask floatingTask) {
        return !floatingTask.isFinished();
    }

    @Override
    public boolean test(DeadlineTask deadlineTask) {
        return deadlineTask.getDue().isAfter(today.atStartOfDay())
               && deadlineTask.getDue().isBefore(tomorrow.atStartOfDay())
               && !deadlineTask.isFinished();
    }

    @Override
    public boolean test(EventTask eventTask) {
        return eventTask.getEnd().isAfter(referenceDateTime)
                  && eventTask.getEnd().isBefore(tomorrow.atStartOfDay())
               || eventTask.getStart().isAfter(referenceDateTime)
                  && eventTask.getStart().isBefore(tomorrow.atStartOfDay())
               || eventTask.getEnd().isAfter(referenceDateTime)
                  && eventTask.getStart().isBefore(referenceDateTime);
    }

    @Override
    public String toHumanReadableString() {
        return "finished tasks today";
    }

    @Override
    public boolean equals(Object other) {
        return other == this
               || (other instanceof TaskWillHappenTodayPredicate
               && referenceDateTime.equals(((TaskWillHappenTodayPredicate)other).referenceDateTime));
    }

}
