package seedu.address.logic.parser;

import seedu.address.logic.commands.MarkFloatingTaskUnfinishedCommand;
import seedu.address.logic.parser.CommandLineParser.Argument;
import seedu.address.model.task.TaskType;

public class MarkFloatingTaskUnfinishedCommandParser implements Parser<MarkFloatingTaskUnfinishedCommand> {

    private final Argument<Integer> indexArg = new Argument<>("INDEX", new IndexParser(TaskType.FLOAT));
    private final CommandLineParser cmdParser = new CommandLineParser()
                                                    .addArgument(indexArg);

    @Override
    public MarkFloatingTaskUnfinishedCommand parse(String str) throws ParseException {
        cmdParser.parse(str);
        return new MarkFloatingTaskUnfinishedCommand(indexArg.getValue());
    }

}
