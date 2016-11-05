package seedu.address.model.compare;

import java.util.Comparator;

import com.google.common.collect.ComparisonChain;

import seedu.address.model.task.FloatingTask;

/**
 * Compares two floating tasks in the order of:
 * 1. Priority (reverse order)
 * 2. Name
 */
public class FloatingTaskPriorityComparator implements Comparator<FloatingTask> {

    @Override
    public int compare(FloatingTask a, FloatingTask b) {
        return ComparisonChain.start()
                .compare(b.getPriority().toInteger(), a.getPriority().toInteger())
                .compare(a.getName().toString(), b.getName().toString())
                .result();
    }

}
