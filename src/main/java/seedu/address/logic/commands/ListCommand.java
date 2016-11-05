package seedu.address.logic.commands;

import seedu.address.model.Model;
import seedu.address.model.filter.TaskPredicate;

/**
 * Lists all tasks that matches a certain {@link TaskPredicate}
 */
public class ListCommand implements Command {

    private static final String MSG_LIST_ALL = "Listing all tasks.";
    private static final String MSG_LIST_FILTER = "Listing all tasks matching filter: %s";

    private final TaskPredicate taskPredicate;

    public ListCommand(TaskPredicate taskPredicate) {
        this.taskPredicate = taskPredicate;
    }

    @Override
    public CommandResult execute(Model model) {
        assert model != null;
        model.setTaskPredicate(taskPredicate);
        return new CommandResult(taskPredicate != null ? String.format(MSG_LIST_FILTER, taskPredicate.toHumanReadableString())
                                                         : MSG_LIST_ALL);
    }

}
