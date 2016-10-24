package seedu.address.logic.parser;

import seedu.address.logic.commands.HelpCommand;

public class HelpCommandParser implements Parser<HelpCommand> {

    private final CommandLineParser cmdParser = new CommandLineParser();

    @Override
    public HelpCommand parse(String str) throws ParseException {
        cmdParser.parse(str);
        return new HelpCommand();
    }

}
