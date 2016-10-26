package seedu.address.logic.parser;

import seedu.address.logic.commands.ShowAllDeadlineCommand;

public class ShowAllDeadlineParser implements Parser<ShowAllDeadlineCommand> {

    private final CommandLineParser cmdParser = new CommandLineParser()
                                                    .addArgument("d"); //TODO replace "d" after IndexPrefix.java added

    @Override
    public ShowAllDeadlineCommand parse(String str) throws ParseException {
        cmdParser.parse(str);
        return new ShowAllDeadlineCommand();
    }
}
