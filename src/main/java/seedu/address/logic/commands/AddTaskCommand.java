package seedu.address.logic.commands;

import seedu.address.model.task.Task;

/**
 * Adds an event task to the task book.
 */
public interface AddTaskCommand extends Command {

    String COMMAND_WORD = "add";

    String MESSAGE_USAGE = COMMAND_WORD + ": Adds an task to the TaskTracker.\n";

    String MESSAGE_SUCCESS = "New task added: %1$s";

    Task getTask();

}
