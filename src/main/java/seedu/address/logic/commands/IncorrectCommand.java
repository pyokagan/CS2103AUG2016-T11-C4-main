package seedu.address.logic.commands;

import seedu.address.model.Model;

/**
 * Represents an incorrect command. Upon execution, produces some feedback to the user.
 */
public class IncorrectCommand implements Command {

    public final String feedbackToUser;

    public IncorrectCommand(String feedbackToUser) {
        this.feedbackToUser = feedbackToUser;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        throw new CommandException(feedbackToUser);
    }

}

