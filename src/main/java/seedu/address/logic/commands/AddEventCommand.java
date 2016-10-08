package seedu.address.logic.commands;

import java.time.LocalDateTime;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.task.EventTask;

/**
 * Adds an event task to the task book.
 */
public class AddEventCommand extends Command {

    public static final String COMMAND_WORD = "event";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an event to the task book. "
            + "Parameters: \"NAME\" <STARTING_DATE> <STARTING_TIME> <ENDING_DATE> <ENDING_TIME>\n"
            + "Example: " + COMMAND_WORD
            + " \"Lunch with Bill Gates\" 12pm 2pm";

    public static final String MESSAGE_SUCCESS = "New event added: %1$s";

    private final EventTask eventTask;

    public AddEventCommand(String name, LocalDateTime start, LocalDateTime end) throws IllegalValueException {
        eventTask = new EventTask(name, start, end);
    }

    public EventTask getEventTask() {
        return eventTask;
    }

    @Override
    public CommandResult execute() {
        assert model != null;
        model.addEventTask(eventTask);
        return new CommandResult(String.format(MESSAGE_SUCCESS, eventTask));
    }

}
