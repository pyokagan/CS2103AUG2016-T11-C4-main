package seedu.address.logic.parser;

import seedu.address.commons.core.IndexPrefix;
import seedu.address.logic.commands.EditFloatingTaskCommand;
import seedu.address.logic.parser.CommandLineParser.Argument;
import seedu.address.logic.parser.CommandLineParser.OptionalFlag;
import seedu.address.model.task.Name;
import seedu.address.model.task.Priority;

public class EditFloatingTaskParser implements Parser<EditFloatingTaskCommand> {

    private final Argument<Integer> indexArg = new Argument<>("INDEX", new IndexParser(IndexPrefix.FLOAT));
    private final OptionalFlag<Name> newNameFlag = new OptionalFlag<>("n-", "NEW_NAME", new NameParser());
    private final OptionalFlag<Priority> newPriorityFlag = new OptionalFlag<>("p-", "NEW_PRIORITY", new PriorityParser());
    private final CommandLineParser cmdParser = new CommandLineParser()
                                                        .addArgument(indexArg)
                                                        .putFlag(newNameFlag)
                                                        .putFlag(newPriorityFlag);

    @Override
    public EditFloatingTaskCommand parse(String args) throws ParseException {
        cmdParser.parse(args);

        return new EditFloatingTaskCommand(indexArg.getValue(), newNameFlag.getValue(),
                                           newPriorityFlag.getValue());
    }

}
