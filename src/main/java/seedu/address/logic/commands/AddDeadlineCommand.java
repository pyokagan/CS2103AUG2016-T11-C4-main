package seedu.address.logic.commands;

import java.time.LocalDateTime;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.StringUtil;
import seedu.address.model.Model;
import seedu.address.model.task.DeadlineTask;

/**
 * Adds an deadline task to the task book.
 */
public class AddDeadlineCommand implements AddTaskCommand {

    private static final String MESSAGE_SUCCESS = "Added new deadline task \"%s\", due on \"%s\".";

    private final DeadlineTask deadlineTask;

    public AddDeadlineCommand(DeadlineTask deadlineTask) {
        this.deadlineTask = deadlineTask;
    }

    public AddDeadlineCommand(String name, LocalDateTime due) throws IllegalValueException {
        this(new DeadlineTask(name, due));
    }

    @Override
    public DeadlineTask getTask() {
        return deadlineTask;
    }

    @Override
    public CommandResult execute(Model model) {
        assert model != null;
        model.addDeadlineTask(deadlineTask);
        return new CommandResult(String.format(MESSAGE_SUCCESS, deadlineTask.getName(),
                                               StringUtil.localDateTimeToPrettyString(deadlineTask.getDue())));
    }

}
