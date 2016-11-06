package seedu.address.logic.commands;

import java.util.Optional;

import seedu.address.commons.core.IndexPrefix;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.Model;
import seedu.address.model.task.FloatingTask;
import seedu.address.model.task.Name;
import seedu.address.model.task.Priority;

public class EditFloatingTaskCommand implements Command {

    private static final String MESSAGE_EDIT_TASK_SUCCESS = "Edited floating task "
                                                           + IndexPrefix.FLOAT.getPrefixString() + "%d.";

    private final int targetIndex;
    private final Optional<Name> newName;
    private final Optional<Priority> newPriority;

    public EditFloatingTaskCommand(int targetIndex, Optional<Name> newName, Optional<Priority> newPriority) {
        this.targetIndex = targetIndex;
        this.newName = newName;
        this.newPriority = newPriority;
    }

    public int getTargetIndex() {
        return targetIndex;
    }

    public Optional<Name> getNewName() {
        return newName;
    }

    public Optional<Priority> getNewPriority() {
        return newPriority;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        final FloatingTask oldFloatingTask;
        try {
            oldFloatingTask = model.getFloatingTask(targetIndex);
        } catch (IllegalValueException e) {
            throw new CommandException(e);
        }

        final FloatingTask newFloatingTask;

        newFloatingTask = new FloatingTask(
                newName.orElse(oldFloatingTask.getName()),
                newPriority.orElse(oldFloatingTask.getPriority()),
                oldFloatingTask.isFinished()
        );

        try {
            model.setFloatingTask(targetIndex, newFloatingTask);
        } catch (IllegalValueException e) {
            throw new AssertionError("The target floating task cannot be missing", e);
        }

        return new CommandResult(String.format(MESSAGE_EDIT_TASK_SUCCESS, targetIndex));
    }

}
