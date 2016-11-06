package seedu.address.model.filter;

import java.time.LocalDateTime;

import seedu.address.model.task.DeadlineTask;
import seedu.address.model.task.EventTask;
import seedu.address.model.task.FloatingTask;
import seedu.address.model.task.Task;

/**
 * A predicate that returns true if the {@link Task} is overdue.
 * <p><ul>
 * <li> Floating tasks are never overdue.
 * <li> Deadline tasks are overdue when {@link DeadlineTask#getDue()} is before the reference datetime.
 * <li> Event tasks are overdue when their end time is before the reference datetime.
 * </ul>
 */
public class TaskOverduePredicate implements TaskPredicate {
    private final LocalDateTime referenceDateTime;

    public TaskOverduePredicate(LocalDateTime referenceDateTime) {
        assert referenceDateTime != null;
        this.referenceDateTime = referenceDateTime;
    }

    @Override
    public boolean test(FloatingTask floatingTask) {
        return false;
    }

    @Override
    public boolean test(DeadlineTask deadlineTask) {
        return !deadlineTask.isFinished() && deadlineTask.getDue().isBefore(referenceDateTime);
    }

    @Override
    public boolean test(EventTask eventTask) {
        return eventTask.getEnd().isBefore(referenceDateTime);
    }

    @Override
    public String toHumanReadableString() {
        return "overdue tasks";
    }

    @Override
    public boolean equals(Object other) {
        return other == this
               || (other instanceof TaskOverduePredicate
               && referenceDateTime.equals(((TaskOverduePredicate)other).referenceDateTime));
    }

}
