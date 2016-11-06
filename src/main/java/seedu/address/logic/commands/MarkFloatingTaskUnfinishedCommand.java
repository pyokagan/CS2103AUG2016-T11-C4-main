package seedu.address.logic.commands;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.Model;
import seedu.address.model.task.FloatingTask;
import seedu.address.model.task.FloatingTaskBuilder;
import seedu.address.model.task.TaskType;

public class MarkFloatingTaskUnfinishedCommand implements Command {

    private static final String MSG_SUCCESS = "Floating task " + TaskType.FLOAT.getPrefixString()
                                                + "%s unfinished.";

    private final int targetIndex;

    public MarkFloatingTaskUnfinishedCommand(int targetIndex) {
        this.targetIndex = targetIndex;
    }

    public int getTargetIndex() {
        return targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        assert model != null;
        final FloatingTask oldFloatingTask;
        try {
            oldFloatingTask = model.getFloatingTask(targetIndex);
        } catch (IllegalValueException e) {
            throw new CommandException(e);
        }

        final FloatingTask finishedFloatingTask = new FloatingTaskBuilder(oldFloatingTask)
                                                    .setFinished(false)
                                                    .build();
        try {
            model.setFloatingTask(targetIndex, finishedFloatingTask);
        } catch (IllegalValueException e) {
            throw new AssertionError("The target floating task cannot be missing", e);
        }

        return new CommandResult(String.format(MSG_SUCCESS, targetIndex));
    }

}
