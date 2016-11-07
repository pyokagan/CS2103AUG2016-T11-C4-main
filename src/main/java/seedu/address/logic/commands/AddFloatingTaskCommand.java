package seedu.address.logic.commands;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.Model;
import seedu.address.model.task.FloatingTask;
import seedu.address.model.task.Name;
import seedu.address.model.task.Priority;

/**
 * Adds an event task to the task book.
 */
public class AddFloatingTaskCommand implements AddTaskCommand {

    private static final String MESSAGE_SUCCESS = "Added new floating task \"%s\", with priority %s.";

    private final FloatingTask floatingTask;

    public AddFloatingTaskCommand(FloatingTask floatingTask) {
        this.floatingTask = floatingTask;
    }

    public AddFloatingTaskCommand(String name, String priority) throws IllegalValueException {
        this(new FloatingTask(new Name(name), new Priority(priority)));
    }

    @Override
    public FloatingTask getTask() {
        return floatingTask;
    }

    @Override
    public CommandResult execute(Model model) {
        assert model != null;
        model.addFloatingTask(floatingTask);
        return new CommandResult(String.format(MESSAGE_SUCCESS, floatingTask.getName(),
                                                                floatingTask.getPriority()));
    }

}
