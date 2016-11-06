package seedu.address.logic.parser;

import seedu.address.logic.commands.DeleteFloatingTaskCommand;
import seedu.address.logic.parser.CommandLineParser.Argument;
import seedu.address.model.task.TaskType;

/**
 * Parser for "del-float" command.
 */
public class DeleteFloatingTaskParser implements Parser<DeleteFloatingTaskCommand> {

    private final Argument<Integer> indexArg = new Argument<>("INDEX", new IndexParser(TaskType.FLOAT));
    private final CommandLineParser cmdParser = new CommandLineParser()
                                                    .addArgument(indexArg);

    @Override
    public DeleteFloatingTaskCommand parse(String str) throws ParseException {
        cmdParser.parse(str);
        return new DeleteFloatingTaskCommand(indexArg.getValue());
    }

}
