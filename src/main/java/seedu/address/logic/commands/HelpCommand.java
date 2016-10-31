package seedu.address.logic.commands;

import seedu.address.model.Model;

/**
 * Requests the application to open its "help window".
 */
public class HelpCommand implements Command {

    @Override
    public CommandResult execute(Model model) {
        return new Result();
    }

    private static class Result extends CommandResult implements HelpCommandResult {
        private static final String MSG_EXIT = "Opening help window...";

        private Result() {
            super(MSG_EXIT);
        }
    }

}
