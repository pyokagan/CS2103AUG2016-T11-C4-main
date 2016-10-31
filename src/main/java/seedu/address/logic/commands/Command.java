package seedu.address.logic.commands;

import seedu.address.model.Model;

/**
 * Represents a command with hidden internal logic and the ability to be executed.
 *
 * This is a functional interface whose functional method is {@link #execute(Model)}
 */
public interface Command {

    /**
     * Executes the command and returns the result message.
     *
     * @return feedback message of the operation result for display
     * @throws CommandException if an error occurred during command execution. The model will be left in
     *          an unknown state -- the caller should call {@link Model#undo()} to revert the model back
     *          to a known state if necessary.
     */
    CommandResult execute(Model model) throws CommandException;

}
