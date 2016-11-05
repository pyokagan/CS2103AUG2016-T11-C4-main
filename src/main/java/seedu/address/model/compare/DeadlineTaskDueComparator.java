package seedu.address.model.compare;

import java.util.Comparator;

import com.google.common.collect.ComparisonChain;

import seedu.address.model.task.DeadlineTask;

/**
 * Compares two DeadlineTasks in the order of
 * 1. Due date
 * 2. Name
 */
public class DeadlineTaskDueComparator implements Comparator<DeadlineTask> {

    @Override
    public int compare(DeadlineTask a, DeadlineTask b) {
        return ComparisonChain.start()
                .compare(a.getDue(), b.getDue())
                .compare(a.getName().toString(), b.getName().toString())
                .result();
    }

}
