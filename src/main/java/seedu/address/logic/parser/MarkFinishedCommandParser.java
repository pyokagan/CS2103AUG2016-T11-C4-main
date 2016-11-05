package seedu.address.logic.parser;

import seedu.address.logic.commands.Command;

public class MarkFinishedCommandParser implements Parser<Command> {

    private final OverloadParser<Command> overloadParser = new OverloadParser<Command>()
            .addParser("Mark a deadline as finished", new MarkDeadlineFinishedParser())
            .addParser("Mark a floating task as finished", new MarkFloatingTaskFinishedParser());

    @Override
    public Command parse(String str) throws ParseException {
        return overloadParser.parse(str);
    }

}
