package seedu.address.logic.commands;

import seedu.address.model.compare.DeadlineTaskDueComparator;
import seedu.address.model.compare.EventTaskStartEndComparator;
import seedu.address.model.compare.FloatingTaskPriorityComparator;

public class ListCommand extends Command {

    @Override
    public CommandResult execute() {
        assert model != null;
        model.setFloatingTaskFilter(null);
        model.setFloatingTaskSortComparator(new FloatingTaskPriorityComparator());
        model.setDeadlineTaskFilter(null);
        model.setDeadlineTaskSortComparator(new DeadlineTaskDueComparator());
        model.setEventTaskFilter(null);
        model.setEventTaskSortComparator(new EventTaskStartEndComparator());
        return new CommandResult("Listed all tasks.");
    }

}
