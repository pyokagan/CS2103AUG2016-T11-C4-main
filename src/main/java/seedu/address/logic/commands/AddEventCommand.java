package seedu.address.logic.commands;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.Model;
import seedu.address.model.task.EventTask;

/**
 * Adds an event task to the task book.
 */
public class AddEventCommand implements AddTaskCommand {

    private static final String MESSAGE_SUCCESS = "Added new event task \"%s\", from \"%s\" to \"%s\".";

    private final EventTask eventTask;

    public AddEventCommand(EventTask eventTask) {
        this.eventTask = eventTask;
    }

    @Override
    public EventTask getTask() {
        return eventTask;
    }

    @Override
    public CommandResult execute(Model model) {
        assert model != null;
        model.addEventTask(eventTask);
        return new CommandResult(String.format(MESSAGE_SUCCESS, eventTask.getName(),
                                               StringUtil.localDateTimeToPrettyString(eventTask.getStart()),
                                               StringUtil.localDateTimeToPrettyString(eventTask.getEnd())));
    }

}
