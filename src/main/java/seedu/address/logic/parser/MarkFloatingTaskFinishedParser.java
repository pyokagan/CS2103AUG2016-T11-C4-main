package seedu.address.logic.parser;

import seedu.address.logic.commands.MarkFloatingTaskFinishedCommand;
import seedu.address.logic.parser.CommandLineParser.Argument;

public class MarkFloatingTaskFinishedParser implements Parser<MarkFloatingTaskFinishedCommand> {

    private final Argument<Integer> indexArg = new Argument<>("INDEX", new IndexParser());
    private final CommandLineParser cmdParser = new CommandLineParser()
                                                    .addArgument(indexArg);

    @Override
    public MarkFloatingTaskFinishedCommand parse(String str) throws ParseException {
        cmdParser.parse(str);
        return new MarkFloatingTaskFinishedCommand(indexArg.getValue());
    }

}
