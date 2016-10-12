package seedu.address.model.task;

import static seedu.address.commons.time.TypicalLocalDateTimes.UNIX_EPOCH;

import java.util.Arrays;
import java.util.List;

import seedu.address.commons.exceptions.IllegalValueException;

public class TypicalEventTasks {

    public final EventTask lunchWithBillGates;

    public final EventTask launchNuclearWeapons;

    public TypicalEventTasks() {
        try {
            lunchWithBillGates = new EventTask("Lunch with Bill Gates", UNIX_EPOCH, UNIX_EPOCH.plusHours(1));
            launchNuclearWeapons = new EventTask("Launch nuclear weapons", UNIX_EPOCH.plusHours(1),
                                                 UNIX_EPOCH.plusHours(2));
        } catch (IllegalValueException e) {
            throw new AssertionError("this should not happen", e);
        }
    }

    public List<EventTask> getEventTasks() {
        final EventTask[] tasks = {lunchWithBillGates, launchNuclearWeapons};
        return Arrays.asList(tasks);
    }

}
