package seedu.address.model.filter;

import seedu.address.model.task.DeadlineTask;
import seedu.address.model.task.EventTask;
import seedu.address.model.task.FloatingTask;

/**
 * Represents a predicate used to filter {@link FloatingTask}, {@link DeadlineTask} and {@link EventTask}.
 */
public interface TaskPredicate {

    /**
     * Evaluates the predicate on the given {@link FloatingTask}.
     * @returns true if the input floating task matches the predicate, otherwise false.
     */
    boolean test(FloatingTask floatingTask);

    /**
     * Evaluates the predicate on the given {@link DeadlineTask}
     * @returns true if the input deadline task matches the predicate, otherwise false.
     */
    boolean test(DeadlineTask deadlineTask);

    /**
     * Evaluates the predicate on the given {@link EventTask}
     * @returns true if the input event task matches the predicate, otherwise false.
     */
    boolean test(EventTask eventTask);

    /**
     * Returns a human-readable explanation of the predicate. This explanation must read correctly when
     * used as follows: "Filtering by: XX", where "XX" is the return value of this method.
     */
    String toHumanReadableString();

}
