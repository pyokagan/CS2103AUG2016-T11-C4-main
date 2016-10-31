package seedu.address.logic.commands;

import seedu.address.model.Model;

public class ShowAllFloatingTaskCommand implements Command {

    public static final String COMMAND_WORD = "view";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": List all floating task on the user interface.\n"
            + "Example: " + COMMAND_WORD + " f"; // TODO replace " f" after IndexPrefix.java added

    public static final String MESSAGE_EDIT_TASK_SUCCESS = "All Floating Tasks have been listed.";

    @Override
    public CommandResult execute(Model model) {
        // clear the filter/predicate of the filtered floating task list.
        model.setFloatingTaskPredicate(null);
        return new CommandResult(MESSAGE_EDIT_TASK_SUCCESS);
    }
}
