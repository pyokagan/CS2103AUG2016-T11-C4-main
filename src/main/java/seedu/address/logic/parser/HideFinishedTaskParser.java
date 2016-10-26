package seedu.address.logic.parser;

import seedu.address.logic.commands.Command;

public class HideFinishedTaskParser implements Parser<Command> {

    private final OverloadParser<Command> overloadParser;

    public HideFinishedTaskParser() {
        overloadParser = new OverloadParser<Command>()
                            .addParser("Hide finished floating tasks", new HideFinishedFloatingTaskParser())
                            .addParser("Hide finished deadlines", new HideFinishedDeadlineParser());
    }

    @Override
    public Command parse(String str) throws ParseException {
        return overloadParser.parse(str);
    }
}
