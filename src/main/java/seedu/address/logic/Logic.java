package seedu.address.logic;

import java.io.IOException;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandException;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.parser.ParseException;
import seedu.address.model.ReadOnlyModel;

/**
 * API of the Logic component
 */
public interface Logic {

    /**
     * Executes the command and returns the result.
     * @param command The command to execute.
     * @return the result of the command execution.
     * @throws CommandException if an error occurred while executing the command.
     * @throws IOException if an error occurred while saving the modified model to disk.
     */
    CommandResult execute(Command command) throws CommandException, IOException;

    /**
     * Executes the command and returns the result.
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws ParseException if the commandText could not be parsed.
     * @see #execute(Command)
     */
    CommandResult execute(String commandText) throws ParseException, CommandException, IOException;

    /** Returns the logic's component associated model. */
    ReadOnlyModel getModel();

}
