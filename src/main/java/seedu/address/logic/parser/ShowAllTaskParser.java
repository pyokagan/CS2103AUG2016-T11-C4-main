package seedu.address.logic.parser;

import seedu.address.logic.commands.Command;

public class ShowAllTaskParser implements Parser<Command> {

    private final OverloadParser<Command> overloadParser;

    public ShowAllTaskParser() {
        overloadParser = new OverloadParser<Command>()
                            .addParser("Show all finished floating tasks", new ShowAllFloatingTaskParser())
                            .addParser("Show all finished deadlines", new ShowAllDeadlineParser());
    }

    @Override
    public Command parse(String str) throws ParseException {
        return overloadParser.parse(str);
    }
}
