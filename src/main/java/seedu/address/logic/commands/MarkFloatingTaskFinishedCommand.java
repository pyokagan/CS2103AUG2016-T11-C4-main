package seedu.address.logic.commands;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.Model;
import seedu.address.model.task.FloatingTask;
import seedu.address.model.task.TaskType;

public class MarkFloatingTaskFinishedCommand implements Command {

    private static final String MSG_SUCCESS = "Floating task "
                                              + TaskType.FLOAT.getPrefixString()
                                              + "%s finished.";

    private final int targetIndex;

    public MarkFloatingTaskFinishedCommand(int targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        assert model != null;
        FloatingTask oldFloatingTask;
        try {
            oldFloatingTask = model.getFloatingTask(targetIndex);
        } catch (IllegalValueException e) {
            throw new CommandException(e);
        }

        FloatingTask finishedFloatingTask;
        finishedFloatingTask = new FloatingTask(oldFloatingTask.getName(),
                                                oldFloatingTask.getPriority(),
                                                true);

        try {
            model.setFloatingTask(targetIndex, finishedFloatingTask);
        } catch (IllegalValueException e) {
            throw new AssertionError("The target floating task cannot be missing", e);
        }

        return new CommandResult(String.format(MSG_SUCCESS, targetIndex));

    }
}

