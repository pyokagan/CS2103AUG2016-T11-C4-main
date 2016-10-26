package seedu.address.logic.parser;

import seedu.address.logic.commands.ShowAllFloatingTaskCommand;

public class ShowAllFloatingTaskParser implements Parser<ShowAllFloatingTaskCommand> {

    private final CommandLineParser cmdParser = new CommandLineParser()
                                                    .addArgument("f"); //TODO replace "f" after IndexPrefix.java added

    @Override
    public ShowAllFloatingTaskCommand parse(String str) throws ParseException {
        cmdParser.parse(str);
        return new ShowAllFloatingTaskCommand();
    }
}
