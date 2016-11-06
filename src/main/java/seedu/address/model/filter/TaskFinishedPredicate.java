package seedu.address.model.filter;

import java.time.LocalDateTime;

import seedu.address.model.task.DeadlineTask;
import seedu.address.model.task.EventTask;
import seedu.address.model.task.FloatingTask;
import seedu.address.model.task.Task;

/**
 * A predicate that returns true if the {@link Task} is finished.
 * <p><ul>
 * <li> Floating tasks are finished when {@link FloatingTask#isFinished()} returns true.
 * <li> Deadline tasks are finished when {@link DeadlineTask#isFinished()} returns true.
 * <li> Event tasks are finished when their end time is before the reference datetime.
 * </ul>
 */
public class TaskFinishedPredicate implements TaskPredicate {
    private final LocalDateTime referenceDateTime;

    public TaskFinishedPredicate(LocalDateTime referenceDateTime) {
        assert referenceDateTime != null;
        this.referenceDateTime = referenceDateTime;
    }

    public LocalDateTime getReferenceDateTime() {
        return referenceDateTime;
    }

    @Override
    public boolean test(FloatingTask floatingTask) {
        return floatingTask.isFinished();
    }

    @Override
    public boolean test(DeadlineTask deadlineTask) {
        return deadlineTask.isFinished();
    }

    @Override
    public boolean test(EventTask eventTask) {
        return eventTask.getEnd().isBefore(referenceDateTime);
    }

    @Override
    public String toHumanReadableString() {
        return "finished tasks";
    }

    @Override
    public boolean equals(Object other) {
        return other == this
               || (other instanceof TaskFinishedPredicate
               && referenceDateTime.equals(((TaskFinishedPredicate)other).getReferenceDateTime()));
    }

}
