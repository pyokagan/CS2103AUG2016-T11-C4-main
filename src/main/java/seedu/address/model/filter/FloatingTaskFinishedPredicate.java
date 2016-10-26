package seedu.address.model.filter;

import java.util.function.Predicate;

import seedu.address.model.task.FloatingTask;

public class FloatingTaskFinishedPredicate implements Predicate<FloatingTask> {

    @Override
    public boolean test(FloatingTask task) {
        return !task.isFinished();
    }
}
