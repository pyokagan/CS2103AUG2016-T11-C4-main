package seedu.address.logic.commands;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.Model;
import seedu.address.model.task.EventTask;
import seedu.address.model.task.TaskType;

public class DeleteEventCommand implements Command {

    public static final String MESSAGE_DELETE_TASK_SUCCESS = "Deleted event "
                                                             + TaskType.EVENT.getPrefixString()
                                                             + "%d. %s";

    private final int targetIndex;

    public DeleteEventCommand(int targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        try {
            final EventTask deletedTask = model.removeEventTask(targetIndex);
            return new CommandResult(String.format(MESSAGE_DELETE_TASK_SUCCESS, targetIndex, deletedTask.getName()));
        } catch (IllegalValueException e) {
            throw new CommandException(e);
        }
    }

}
