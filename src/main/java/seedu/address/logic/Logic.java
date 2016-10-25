package seedu.address.logic;

import javafx.collections.ObservableList;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.parser.ParseException;
import seedu.address.model.task.DeadlineTask;
import seedu.address.model.task.EventTask;
import seedu.address.model.task.FloatingTask;

/**
 * API of the Logic component
 */
public interface Logic {
    /**
     * Executes the command and returns the result.
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     */
    CommandResult execute(String commandText) throws ParseException;

    /** Returns the filtered list of floating tasks */
    ObservableList<FloatingTask> getFilteredFloatingTaskList();

    /** Returns the filtered list of deadline tasks */
    ObservableList<DeadlineTask> getFilteredDeadlineTaskList();

    /** Returns the filtered list of event tasks */
    ObservableList<EventTask> getFilteredEventTaskList();

}
