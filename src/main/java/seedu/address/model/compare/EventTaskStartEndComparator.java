package seedu.address.model.compare;

import java.util.Comparator;

import com.google.common.collect.ComparisonChain;

import seedu.address.model.task.EventTask;

/**
 * Compares two event tasks in the following order:
 * 1. Start datetime
 * 2. End datetime
 * 3. Name
 */
public class EventTaskStartEndComparator implements Comparator<EventTask> {

    @Override
    public int compare(EventTask a, EventTask b) {
        return ComparisonChain.start()
                .compare(a.getStart(), b.getStart())
                .compare(a.getEnd(), b.getEnd())
                .compare(a.getName().toString(), b.getName().toString())
                .result();
    }

}
