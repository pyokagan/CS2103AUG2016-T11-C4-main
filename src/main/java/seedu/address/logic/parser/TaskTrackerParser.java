package seedu.address.logic.parser;

import seedu.address.logic.commands.Command;

/**
 * Parses user input.
 */
public class TaskTrackerParser implements Parser<Command> {

    private final SubcommandParser parser = new SubcommandParser()
            .putSubcommand("add", new AddTaskParser())
            .putSubcommand("edit-float", new EditFloatingTaskParser())
            .putSubcommand("del-float", new DeleteFloatingTaskParser())
            .putSubcommand("fin-float", new MarkFloatingTaskFinishedParser())
            .putSubcommand("del-event", new DeleteEventParser())
            .putSubcommand("edit-event", new EditEventParser())
            .putSubcommand("del-deadline", new DeleteDeadlineParser())
            .putSubcommand("edit-deadline", new EditDeadlineParser())
            .putSubcommand("fin-deadline", new MarkDeadlineFinishedParser())
            .putSubcommand("clear", new ClearCommandParser())
            .putSubcommand("exit", new ExitCommandParser())
            .putSubcommand("help", new HelpCommandParser())
            .putSubcommand("setdatadir", new SetDataDirectoryParser())
            ;

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     */
    @Override
    public Command parse(String userInput) throws ParseException {
        return parser.parse(userInput);
    }

}
