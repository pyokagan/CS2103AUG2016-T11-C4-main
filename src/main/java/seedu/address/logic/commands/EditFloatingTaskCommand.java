package seedu.address.logic.commands;

import java.util.Optional;

import seedu.address.commons.core.Messages;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.Model;
import seedu.address.model.task.FloatingTask;
import seedu.address.model.task.Name;
import seedu.address.model.task.Priority;

public class EditFloatingTaskCommand extends Command {

    public static final String COMMAND_WORD = "edit-float";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Edits the floating task identified by the index number used in the filtered floating task listing.\n"
            + "Parameters: INDEX [n-NAME] [p-PRIORITY]"
            + "Example: " + COMMAND_WORD + " 1 p-2";

    public static final String MESSAGE_EDIT_TASK_SUCCESS = "Floting task edited: %1$s";

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
    public CommandResult execute(Model model) {
        final FloatingTask oldFloatingTask;
        try {
            oldFloatingTask = model.getFloatingTask(targetIndex);
        } catch (IllegalValueException e) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        final FloatingTask newFloatingTask;

        newFloatingTask = new FloatingTask(
                newName.orElse(oldFloatingTask.getName()),
                newPriority.orElse(oldFloatingTask.getPriority())
        );

        try {
            model.setFloatingTask(targetIndex, newFloatingTask);
        } catch (IllegalValueException e) {
            throw new AssertionError("The target floating task cannot be missing", e);
        }

        return new CommandResult(String.format(MESSAGE_EDIT_TASK_SUCCESS, newFloatingTask));
    }

}
