package seedu.address.logic.commands;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

import seedu.address.commons.core.Messages;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.Model;
import seedu.address.model.task.EventTask;
import seedu.address.model.task.Name;

public class EditEventCommand extends Command {

    public static final String COMMAND_WORD = "edit-event";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Edits the event identified by the index number used in the filtered event listing.\n"
            + "Parameters: INDEX [sd-NEW_START_TIME | st-NEW_START_DATE | ed-NEW_END_DATE | et-NEW_END_TIME | n-NEW_NAME]"
            + "Example: " + COMMAND_WORD + " 1 st-4pm et-8pm";

    public static final String MESSAGE_EDIT_TASK_SUCCESS = "Event edited: %1$s";

    public final int targetIndex;
    public final Optional<Name> newName;
    public final Optional<LocalDate> newStartDate;
    public final Optional<LocalTime> newStartTime;
    public final Optional<LocalDate> newEndDate;
    public final Optional<LocalTime> newEndTime;

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
    public CommandResult execute(Model model) {
        EventTask oldEventTask;
        try {
            oldEventTask = model.getEventTask(targetIndex);
        } catch (IllegalValueException e) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
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
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(e.getMessage());
        }

        try {
            model.setEventTask(targetIndex, newEventTask);
        } catch (IllegalValueException e) {
            throw new AssertionError("The target event cannot be missing", e);
        }

        return new CommandResult(String.format(MESSAGE_EDIT_TASK_SUCCESS, newEventTask));
    }

}
