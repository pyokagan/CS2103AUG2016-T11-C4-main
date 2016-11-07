package seedu.address.logic.commands;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.Model;
import seedu.address.model.task.DeadlineTask;
import seedu.address.model.task.Name;
import seedu.address.model.task.TaskType;

public class EditDeadlineCommand implements Command {

    private static final String MESSAGE_EDIT_TASK_SUCCESS = "Edited deadline task "
                                                            + TaskType.DEADLINE.getPrefixString() + "%d.";

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
    public CommandResult execute(Model model) throws CommandException {
        DeadlineTask oldDeadlineTask;
        try {
            oldDeadlineTask = model.getDeadlineTask(targetIndex);
        } catch (IllegalValueException e) {
            throw new CommandException(e);
        }

        DeadlineTask newDeadlineTask;
        newDeadlineTask = new DeadlineTask(
                newName.orElse(oldDeadlineTask.getName()),
                LocalDateTime.of(
                        newDate.orElse(oldDeadlineTask.getDue().toLocalDate()),
                        newTime.orElse(oldDeadlineTask.getDue().toLocalTime())
                ),
                oldDeadlineTask.isFinished()
        );

        try {
            model.setDeadlineTask(targetIndex, newDeadlineTask);
        } catch (IllegalValueException e) {
            throw new AssertionError("The target deadline cannot be missing", e);
        }

        return new CommandResult(String.format(MESSAGE_EDIT_TASK_SUCCESS, targetIndex));
    }

}
