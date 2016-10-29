package seedu.address.logic.commands;

import seedu.address.model.WorkingTaskBook;

public class ListCommand extends Command {

    @Override
    public CommandResult execute() {
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
