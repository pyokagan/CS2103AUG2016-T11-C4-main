package seedu.address.logic.parser;

import java.util.List;

import seedu.address.logic.commands.Command;
import seedu.address.model.ReadOnlyModel;

/**
 * Top level task tracker command-line parser.
 */
public class TaskTrackerParser implements Parser<Command> {
    private final SubcommandParser parser = new SubcommandParser()
            .putSubcommand("add", new AddTaskParser())
            .putSubcommand("edit", new EditCommandParser())
            .putSubcommand("del", new DeleteCommandParser())
            .putSubcommand("fin", new MarkFinishedCommandParser())
            .putSubcommand("unfin", new MarkTaskUnfinishedCommandParser())
            .putSubcommand("clear", new ClearCommandParser())
            .putSubcommand("exit", new ExitCommandParser())
            .putSubcommand("help", new HelpCommandParser())
            .putSubcommand("setdatadir", new SetDataDirectoryParser())
            .putSubcommand("list", new ListCommandParser())
            .putSubcommand("undo", new UndoCommandParser())
            .putSubcommand("redo", new RedoCommandParser())
            .putSubcommand("find", new FindCommandParser())
            ;

    @Override
    public Command parse(String userInput) throws ParseException {
        return parser.parse(userInput);
    }

    @Override
    public List<String> autocomplete(ReadOnlyModel model, String input, int pos) {
        return parser.autocomplete(model, input, pos);
    }

}
