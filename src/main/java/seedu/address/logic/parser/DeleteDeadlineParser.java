package seedu.address.logic.parser;

import seedu.address.commons.core.IndexPrefix;
import seedu.address.logic.commands.DeleteDeadlineCommand;
import seedu.address.logic.parser.CommandLineParser.Argument;

public class DeleteDeadlineParser implements Parser<DeleteDeadlineCommand> {

    private final Argument<Integer> indexArg = new Argument<>("INDEX", new IndexParser(IndexPrefix.DEADLINE));
    private final CommandLineParser cmdParser = new CommandLineParser()
                                                    .addArgument(indexArg);

    @Override
    public DeleteDeadlineCommand parse(String str) throws ParseException {
        cmdParser.parse(str);
        return new DeleteDeadlineCommand(indexArg.getValue());
    }

}
