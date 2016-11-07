package seedu.address.logic.commands;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.Model;
import seedu.address.model.task.EventTask;
import seedu.address.model.task.Name;
import seedu.address.model.task.TaskType;

public class EditEventCommand implements Command {

    private static final String MESSAGE_EDIT_TASK_SUCCESS = "Edited event task "
                                                            + TaskType.EVENT.getPrefixString() + "%d.";

    private final int targetIndex;
    private final Optional<Name> newName;
    private final Optional<LocalDate> newStartDate;
    private final Optional<LocalTime> newStartTime;
    private final Optional<LocalDate> newEndDate;
    private final Optional<LocalTime> newEndTime;

    public EditEventCommand(int targetIndex, Optional<Name> newName, Optional<LocalDate> newStartDate,
                            Optional<LocalTime> newStartTime, Optional<LocalDate> newEndDate,
                            Optional<LocalTime> newEndTime) {
        this.targetIndex = targetIndex;
        this.newName = newName;
        this.newStartDate = newStartDate;
        this.newStartTime = newStartTime;
        this.newEndDate = newEndDate;
        this.newEndTime = newEndTime;
    }

    public int getTargetIndex() {
        return targetIndex;
    }

    public Optional<LocalDate> getNewStartDate() {
        return newStartDate;
    }

    public Optional<LocalTime> getNewStartTime() {
        return newStartTime;
    }

    public Optional<LocalDate> getNewEndDate() {
        return newEndDate;
    }

    public Optional<LocalTime> getNewEndTime() {
        return newEndTime;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        EventTask oldEventTask;
        try {
            oldEventTask = model.getEventTask(targetIndex);
        } catch (IllegalValueException e) {
            throw new CommandException(e);
        }

        EventTask newEventTask;
        try {
            newEventTask = new EventTask(
                    newName.orElse(oldEventTask.getName()),
                    LocalDateTime.of(
                            newStartDate.orElse(oldEventTask.getStart().toLocalDate()),
                            newStartTime.orElse(oldEventTask.getStart().toLocalTime())
                    ),
                    LocalDateTime.of(
                            newEndDate.orElse(oldEventTask.getEnd().toLocalDate()),
                            newEndTime.orElse(oldEventTask.getEnd().toLocalTime())
                    )
            );
        } catch (IllegalValueException e) {
            throw new CommandException(e);
        }

        try {
            model.setEventTask(targetIndex, newEventTask);
        } catch (IllegalValueException e) {
            throw new AssertionError("The target event cannot be missing", e);
        }

        return new CommandResult(String.format(MESSAGE_EDIT_TASK_SUCCESS, targetIndex));
    }

}
