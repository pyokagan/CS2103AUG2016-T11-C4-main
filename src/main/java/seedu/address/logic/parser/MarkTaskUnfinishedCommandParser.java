package seedu.address.logic.parser;

import seedu.address.logic.commands.Command;

public class MarkTaskUnfinishedCommandParser implements Parser<Command> {

    private final OverloadParser<Command> overloadParser = new OverloadParser<Command>()
            .addParser("Mark a deadline task as unfinished", new MarkDeadlineUnfinishedCommandParser())
            .addParser("Mark a floating task as unfinished", new MarkFloatingTaskUnfinishedCommandParser());

    @Override
    public Command parse(String str) throws ParseException {
        return overloadParser.parse(str);
    }

}
