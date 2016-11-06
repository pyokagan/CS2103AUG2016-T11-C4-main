package seedu.address.logic.parser;

import seedu.address.logic.commands.DeleteDeadlineCommand;
import seedu.address.logic.parser.CommandLineParser.Argument;
import seedu.address.model.task.TaskType;

public class DeleteDeadlineParser implements Parser<DeleteDeadlineCommand> {

    private final Argument<Integer> indexArg = new Argument<>("INDEX", new IndexParser(TaskType.DEADLINE));
    private final CommandLineParser cmdParser = new CommandLineParser()
                                                    .addArgument(indexArg);

    @Override
    public DeleteDeadlineCommand parse(String str) throws ParseException {
        cmdParser.parse(str);
        return new DeleteDeadlineCommand(indexArg.getValue());
    }

}
