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

    public static final String MESSAGE_MARK_TASK_FINISHED_SUCCESS = "Deadline task finished: %1$s";

    private final int targetIndex;

    public MarkDeadlineFinishedCommand(int targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute() {
        DeadlineTask oldDeadlineTask;
        try {
            oldDeadlineTask = model.getDeadlineTask(targetIndex - 1);
        } catch (IllegalValueException e) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        DeadlineTask finishedDeadlineTask;
        finishedDeadlineTask = new DeadlineTask(oldDeadlineTask.name,
                                                oldDeadlineTask.getDue(),
                                                true);

        try {
            model.setDeadlineTask(targetIndex - 1, finishedDeadlineTask);
        } catch (IllegalValueException e) {
            throw new AssertionError("The target deadline cannot be missing", e);
        }

        return new CommandResult(String.format(MESSAGE_MARK_TASK_FINISHED_SUCCESS, finishedDeadlineTask));

    }
}
