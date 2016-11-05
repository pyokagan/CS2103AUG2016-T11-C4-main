package seedu.address.logic.parser;

import seedu.address.logic.commands.ExitCommand;

public class ExitCommandParser implements Parser<ExitCommand> {

    private final CommandLineParser cmdParser = new CommandLineParser();

    @Override
    public ExitCommand parse(String str) throws ParseException {
        cmdParser.parse(str);
        return new ExitCommand();
    }

}
