package seedu.address.logic.commands;

import seedu.address.model.Model;

/**
 * Requests to terminate the application.
 */
public class ExitCommand implements Command {

    @Override
    public CommandResult execute(Model model) {
        return new Result();
    }

    private static class Result extends CommandResult implements ExitCommandResult {
        private static final String MSG_EXIT = "Exiting as requested...";

        private Result() {
            super(MSG_EXIT);
        }
    }

}
