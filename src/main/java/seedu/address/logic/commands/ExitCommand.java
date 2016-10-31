package seedu.address.logic.commands;

/**
 * Requests to terminate the application.
 */
public class ExitCommand extends Command {

    @Override
    public CommandResult execute() {
        return new Result();
    }

    private static class Result extends CommandResult implements ExitCommandResult {
        private static final String MSG_EXIT = "Exiting as requested...";

        private Result() {
            super(MSG_EXIT);
        }
    }

}
