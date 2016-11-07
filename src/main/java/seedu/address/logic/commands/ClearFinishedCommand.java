package seedu.address.logic.commands;

import java.time.LocalDateTime;

import seedu.address.model.Model;
import seedu.address.model.filter.TaskFinishedPredicate;

public class ClearFinishedCommand implements Command {

    public static final String COMMAND_PARAMETER = "fin";
    public static final String MESSAGE_SUCCESS = "All finished task in Task Tracker have been cleared!";

    private final TaskFinishedPredicate predicate;

    public ClearFinishedCommand(LocalDateTime now) {
        this.predicate = new TaskFinishedPredicate(now);
    }

    public ClearFinishedCommand() {
        this(LocalDateTime.now());
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        assert model != null;
        model.removeFloatingTasks(predicate);
        model.removeDeadlineTasks(predicate);
        model.removeEventTasks(predicate);
        return new CommandResult(MESSAGE_SUCCESS);
    }

}
