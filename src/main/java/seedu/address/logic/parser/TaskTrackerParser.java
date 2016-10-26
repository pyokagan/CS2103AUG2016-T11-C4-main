package seedu.address.logic.parser;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.IncorrectCommand;

/**
 * Parses user input.
 */
public class TaskTrackerParser {

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
            .putSubcommand("hide-finished", new HideFinishedTaskParser())
            .putSubcommand("view", new ShowAllTaskParser())
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
    public Command parseCommand(String userInput) {
        try {
            return parser.parse(userInput);
        } catch (ParseException e) {
            return new IncorrectCommand(e.getMessage());
        }
    }

}
