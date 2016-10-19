package seedu.address.model.filter;

import java.util.function.Predicate;

import seedu.address.model.task.DeadlineTask;

public class DeadlineTaskFinished implements Predicate<DeadlineTask> {

    @Override
    public boolean test(DeadlineTask task) {
        return !task.isFinished();
    }

}
