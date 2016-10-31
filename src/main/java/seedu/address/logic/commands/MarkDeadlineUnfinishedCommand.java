package seedu.address.logic.commands;

import seedu.address.commons.core.IndexPrefix;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.Model;
import seedu.address.model.task.DeadlineTask;
import seedu.address.model.task.DeadlineTaskBuilder;

public class MarkDeadlineUnfinishedCommand implements Command {

    private static final String MSG_SUCCESS = "Deadline task " + IndexPrefix.DEADLINE.getPrefixString()
                                                + "%s unfinished.";

    private final int targetIndex;

    public MarkDeadlineUnfinishedCommand(int targetIndex) {
        this.targetIndex = targetIndex;
    }

    public int getTargetIndex() {
        return targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        assert model != null;
        final DeadlineTask oldDeadlineTask;
        try {
            oldDeadlineTask = model.getDeadlineTask(targetIndex);
        } catch (IllegalValueException e) {
            throw new CommandException(e);
        }

        final DeadlineTask finishedDeadlineTask = new DeadlineTaskBuilder(oldDeadlineTask)
                                                    .setFinished(false)
                                                    .build();

        try {
            model.setDeadlineTask(targetIndex, finishedDeadlineTask);
        } catch (IllegalValueException e) {
            throw new AssertionError("The target deadline cannot be missing", e);
        }

        return new CommandResult(String.format(MSG_SUCCESS, targetIndex));
    }

}
