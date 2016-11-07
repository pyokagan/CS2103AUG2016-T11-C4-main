package seedu.address.logic.parser;

import seedu.address.logic.commands.MarkDeadlineFinishedCommand;
import seedu.address.logic.parser.CommandLineParser.Argument;
import seedu.address.model.task.TaskType;

public class MarkDeadlineFinishedParser implements Parser<MarkDeadlineFinishedCommand> {

    private final Argument<Integer> indexArg = new Argument<>("INDEX", new IndexParser(TaskType.DEADLINE));
    private final CommandLineParser cmdParser = new CommandLineParser()
                                                    .addArgument(indexArg);

    @Override
    public MarkDeadlineFinishedCommand parse(String str) throws ParseException {
        cmdParser.parse(str);
        return new MarkDeadlineFinishedCommand(indexArg.getValue());
    }

}
