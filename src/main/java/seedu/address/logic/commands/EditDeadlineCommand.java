package seedu.address.logic.commands;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

import seedu.address.commons.core.Messages;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.task.DeadlineTask;
import seedu.address.model.task.Name;

public class EditDeadlineCommand extends Command {

    public static final String COMMAND_WORD = "edit-deadline";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Edits the deadline identified by the index number used in the filtered deadline listing.\n"
            + "Parameters: INDEX [dd-NEW_DUE_DATE] [dt-NEW_DUE_TIME] [n-NEW_NAME]"
            + "Example: " + COMMAND_WORD + " 1 dd-12/12/2017 dt-8pm";

    public static final String MESSAGE_EDIT_TASK_SUCCESS = "Deadline edited: %1$s";

    private final int targetIndex;
    private final Optional<Name> newName;
    private final Optional<LocalDate> newDate;
    private final Optional<LocalTime> newTime;

    public EditDeadlineCommand(int targetIndex, Optional<Name> newName, Optional<LocalDate> newDate,
                               Optional<LocalTime> newTime) {
        this.targetIndex = targetIndex;
        this.newName = newName;
        this.newDate = newDate;
        this.newTime = newTime;
    }

    public int getTargetIndex() {
        return targetIndex;
    }

    public Optional<LocalDate> getNewDate() {
        return newDate;
    }

    public Optional<LocalTime> getNewTime() {
        return newTime;
    }

    @Override
    public CommandResult execute() {
        DeadlineTask oldDeadlineTask;
        try {
            oldDeadlineTask = model.getDeadlineTask(targetIndex);
        } catch (IllegalValueException e) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        DeadlineTask newDeadlineTask;
        newDeadlineTask = new DeadlineTask(
                newName.orElse(oldDeadlineTask.getName()),
                LocalDateTime.of(
                        newDate.orElse(oldDeadlineTask.getDue().toLocalDate()),
                        newTime.orElse(oldDeadlineTask.getDue().toLocalTime())
                )
        );

        try {
            model.setDeadlineTask(targetIndex, newDeadlineTask);
        } catch (IllegalValueException e) {
            throw new AssertionError("The target deadline cannot be missing", e);
        }

        return new CommandResult(String.format(MESSAGE_EDIT_TASK_SUCCESS, newDeadlineTask));
    }

}
