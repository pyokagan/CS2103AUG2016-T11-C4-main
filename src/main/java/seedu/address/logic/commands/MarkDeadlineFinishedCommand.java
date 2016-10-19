package seedu.address.logic.commands;

import seedu.address.commons.core.Messages;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.task.DeadlineTask;

public class MarkDeadlineFinishedCommand extends Command {

    public static final String COMMAND_WORD = "fin-deadline";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Mark a deadline as finished and hide it from the deadline list view. \n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 2";

    public static final String MESSAGE_DELETE_TASK_SUCCESS = "Deadline task finished: %1$s";

    private final int targetIndex;

    public MarkDeadlineFinishedCommand(int targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute() {
        try {
            final DeadlineTask finishedTask = model.markDeadlineFinished(targetIndex - 1);
            return new CommandResult(String.format(MESSAGE_DELETE_TASK_SUCCESS, finishedTask));
        } catch (IllegalValueException e) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }
    }
}
