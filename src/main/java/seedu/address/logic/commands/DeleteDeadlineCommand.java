package seedu.address.logic.commands;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.Model;
import seedu.address.model.task.DeadlineTask;

public class DeleteDeadlineCommand implements Command {

    public static final String COMMAND_WORD = "del-deadline";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the deadline identified by the index number used in the filtered deadline listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_TASK_SUCCESS = "Deleted deadline: %1$s";

    private final int targetIndex;

    public DeleteDeadlineCommand(int targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        try {
            final DeadlineTask deletedTask = model.removeDeadlineTask(targetIndex);
            return new CommandResult(String.format(MESSAGE_DELETE_TASK_SUCCESS, deletedTask));
        } catch (IllegalValueException e) {
            throw new CommandException(e);
        }
    }

}
