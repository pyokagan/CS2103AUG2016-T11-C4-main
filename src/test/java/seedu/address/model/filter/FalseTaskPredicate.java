package seedu.address.model.filter;

import seedu.address.model.task.DeadlineTask;
import seedu.address.model.task.EventTask;
import seedu.address.model.task.FloatingTask;

/**
 * A predicate that returns false for all floating, deadline and event tasks.
 */
public class FalseTaskPredicate implements TaskPredicate {

    @Override
    public boolean test(FloatingTask floatingTask) {
        return false;
    }

    @Override
    public boolean test(DeadlineTask deadlineTask) {
        return false;
    }

    @Override
    public boolean test(EventTask eventTask) {
        return false;
    }

    @Override
    public String toHumanReadableString() {
        return "false task predicate";
    }

}
