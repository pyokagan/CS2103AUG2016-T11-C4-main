package seedu.address.logic.commands;

import seedu.address.model.Model;
import seedu.address.model.task.Task;

/**
 * Adds an event task to the task book.
 */
public abstract class AddTaskCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an task to the TaskTracker.\n";

    public static final String MESSAGE_SUCCESS = "New task added: %1$s";

    public abstract Task getTask();

    @Override
    public abstract CommandResult execute(Model model);

}
