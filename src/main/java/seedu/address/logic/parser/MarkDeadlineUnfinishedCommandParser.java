package seedu.address.logic.parser;

import seedu.address.commons.core.IndexPrefix;
import seedu.address.logic.commands.MarkDeadlineUnfinishedCommand;
import seedu.address.logic.parser.CommandLineParser.Argument;

public class MarkDeadlineUnfinishedCommandParser implements Parser<MarkDeadlineUnfinishedCommand> {

    private final Argument<Integer> indexArg = new Argument<>("INDEX", new IndexParser(IndexPrefix.DEADLINE));
    private final CommandLineParser cmdParser = new CommandLineParser()
                                                    .addArgument(indexArg);

    @Override
    public MarkDeadlineUnfinishedCommand parse(String str) throws ParseException {
        cmdParser.parse(str);
        return new MarkDeadlineUnfinishedCommand(indexArg.getValue());
    }

}
