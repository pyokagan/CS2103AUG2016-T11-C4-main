package seedu.address.logic.parser;

import seedu.address.logic.commands.Command;

/**
 * Parser for the "del" command.
 */
public class DeleteCommandParser implements Parser<Command> {

    private final OverloadParser<Command> overloadParser = new OverloadParser<Command>()
            .addParser("Delete an event", new DeleteEventParser())
            .addParser("Delete a deadline", new DeleteDeadlineParser())
            .addParser("Delete a floating task", new DeleteFloatingTaskParser());

    @Override
    public Command parse(String str) throws ParseException {
        return overloadParser.parse(str);
    }

}
