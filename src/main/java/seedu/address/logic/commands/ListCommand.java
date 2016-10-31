package seedu.address.logic.commands;

import seedu.address.model.Model;
import seedu.address.model.WorkingTaskBook;

public class ListCommand implements Command {

    @Override
    public CommandResult execute(Model model) {
        assert model != null;
        model.setFloatingTaskComparator(WorkingTaskBook.DEFAULT_FLOATING_TASK_COMPARATOR);
        model.setFloatingTaskPredicate(null);
        model.setDeadlineTaskComparator(WorkingTaskBook.DEFAULT_DEADLINE_TASK_COMPARATOR);
        model.setDeadlineTaskPredicate(null);
        model.setEventTaskComparator(WorkingTaskBook.DEFAULT_EVENT_TASK_COMPARATOR);
        model.setEventTaskPredicate(null);
        return new CommandResult("Listed all tasks.");
    }

}
