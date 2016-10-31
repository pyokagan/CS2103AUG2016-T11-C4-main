package seedu.address.logic.commands;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.Model;
import seedu.address.model.task.EventTask;

public class DeleteEventCommand implements Command {

    public static final String COMMAND_WORD = "del-event";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the event identified by the index number used in the filtered event listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_TASK_SUCCESS = "Deleted event: %1$s";

    public final int targetIndex;

    public DeleteEventCommand(int targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        try {
            final EventTask deletedTask = model.removeEventTask(targetIndex);
            return new CommandResult(String.format(MESSAGE_DELETE_TASK_SUCCESS, deletedTask));
        } catch (IllegalValueException e) {
            throw new CommandException(e);
        }
    }

}
