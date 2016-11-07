package seedu.address.logic.commands;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.Model;
import seedu.address.model.task.DeadlineTask;
import seedu.address.model.task.TaskType;

public class MarkDeadlineFinishedCommand implements Command {

    private static final String MSG_SUCCESS = "Deadline task "
                                              + TaskType.DEADLINE.getPrefixString()
                                              + "%s finished.";

    private final int targetIndex;

    public MarkDeadlineFinishedCommand(int targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        assert model != null;
        DeadlineTask oldDeadlineTask;
        try {
            oldDeadlineTask = model.getDeadlineTask(targetIndex);
        } catch (IllegalValueException e) {
            throw new CommandException(e);
        }

        DeadlineTask finishedDeadlineTask;
        finishedDeadlineTask = new DeadlineTask(oldDeadlineTask.getName(),
                                                oldDeadlineTask.getDue(),
                                                true);

        try {
            model.setDeadlineTask(targetIndex, finishedDeadlineTask);
        } catch (IllegalValueException e) {
            throw new AssertionError("The target deadline cannot be missing", e);
        }

        return new CommandResult(String.format(MSG_SUCCESS, targetIndex));

    }
}
