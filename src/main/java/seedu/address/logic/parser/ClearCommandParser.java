package seedu.address.logic.parser;

import seedu.address.logic.commands.ClearCommand;

public class ClearCommandParser implements Parser<ClearCommand> {

    private final CommandLineParser cmdParser = new CommandLineParser();

    @Override
    public ClearCommand parse(String str) throws ParseException {
        cmdParser.parse(str);
        return new ClearCommand();
    }

}
