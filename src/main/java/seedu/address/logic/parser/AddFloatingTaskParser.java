package seedu.address.logic.parser;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddFloatingTaskCommand;
import seedu.address.logic.parser.CommandLineParser.Argument;
import seedu.address.logic.parser.CommandLineParser.OptionalFlag;
import seedu.address.model.task.FloatingTask;
import seedu.address.model.task.Name;
import seedu.address.model.task.Priority;

public class AddFloatingTaskParser implements Parser<AddFloatingTaskCommand> {

    private final Argument<Name> nameArg = new Argument<>("NAME", new NameParser());
    private final OptionalFlag<Priority> priorityFlag = new OptionalFlag<>("p-", "PRIORITY", new PriorityParser());
    private final CommandLineParser cmdParser = new CommandLineParser()
                                                    .addArgument(nameArg)
                                                    .putFlag(priorityFlag);

    @Override
    public AddFloatingTaskCommand parse(String str) throws ParseException {
        cmdParser.parse(str);

        final FloatingTask toAdd;
        try {
            toAdd = new FloatingTask(nameArg.getValue(),
                                     priorityFlag.getValue().orElse(new Priority("0")));
        } catch (IllegalValueException e) {
            throw new AssertionError("Should not happen", e);
        }
        return new AddFloatingTaskCommand(toAdd);
    }

}
