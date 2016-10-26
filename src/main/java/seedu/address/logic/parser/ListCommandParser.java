package seedu.address.logic.parser;

import seedu.address.logic.commands.ListCommand;

public class ListCommandParser implements Parser<ListCommand> {

    private final CommandLineParser cmdParser = new CommandLineParser();

    @Override
    public ListCommand parse(String str) throws ParseException {
        cmdParser.parse(str);
        return new ListCommand();
    }

}
