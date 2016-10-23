package seedu.address.logic.commands;

import seedu.address.commons.core.Messages;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.task.FloatingTask;

public class MarkFloatingTaskFinishedCommand extends Command {

    public static final String COMMAND_WORD = "fin-float";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Mark a floating task as finished and hide it from the deadline list view. \n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_MARK_TASK_FINISHED_SUCCESS = "Floating task finished: %1$s";

    private final int targetIndex;

    public MarkFloatingTaskFinishedCommand(int targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute() {
        FloatingTask oldFloatingTask;
        try {
            oldFloatingTask = model.getFloatingTask(targetIndex - 1);
        } catch (IllegalValueException e) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        FloatingTask finishedFloatingTask;
        finishedFloatingTask = new FloatingTask(oldFloatingTask.getName(),
                                                oldFloatingTask.getPriority(),
                                                true);

        try {
            model.setFloatingTask(targetIndex - 1, finishedFloatingTask);
        } catch (IllegalValueException e) {
            throw new AssertionError("The target floating task cannot be missing", e);
        }

        return new CommandResult(String.format(MESSAGE_MARK_TASK_FINISHED_SUCCESS, finishedFloatingTask));

    }
}

