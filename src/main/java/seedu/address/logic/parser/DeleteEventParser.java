package seedu.address.logic.parser;

import seedu.address.logic.commands.DeleteEventCommand;
import seedu.address.logic.parser.CommandLineParser.Argument;
import seedu.address.model.task.TaskType;

public class DeleteEventParser implements Parser<DeleteEventCommand> {

    private final Argument<Integer> indexArg = new Argument<>("INDEX", new IndexParser(TaskType.EVENT));
    private final CommandLineParser cmdParser = new CommandLineParser()
                                                    .addArgument(indexArg);

    @Override
    public DeleteEventCommand parse(String str) throws ParseException {
        cmdParser.parse(str);
        return new DeleteEventCommand(indexArg.getValue());
    }

}
