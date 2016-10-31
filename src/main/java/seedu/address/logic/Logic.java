package seedu.address.logic;

import java.util.Optional;

import javafx.collections.ObservableList;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandException;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.parser.ParseException;
import seedu.address.model.IndexedItem;
import seedu.address.model.task.DeadlineTask;
import seedu.address.model.task.EventTask;
import seedu.address.model.task.FloatingTask;
import seedu.address.model.task.TaskSelect;

/**
 * API of the Logic component
 */
public interface Logic {

    /**
     * Executes the command and returns the result.
     * @param command The command to execute.
     * @return the result of the command execution.
     */
    CommandResult execute(Command command) throws CommandException;

    /**
     * Executes the command and returns the result.
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @see #execute(Command)
     */
    CommandResult execute(String commandText) throws ParseException, CommandException;

    /** Returns the current TaskSelect, if any. */
    Optional<TaskSelect> getTaskSelect();

    /** Returns the filtered list of floating tasks */
    ObservableList<IndexedItem<FloatingTask>> getFloatingTaskList();

    /** Returns the filtered list of deadline tasks */
    ObservableList<IndexedItem<DeadlineTask>> getDeadlineTaskList();

    /** Returns the filtered list of event tasks */
    ObservableList<IndexedItem<EventTask>> getEventTaskList();

}
