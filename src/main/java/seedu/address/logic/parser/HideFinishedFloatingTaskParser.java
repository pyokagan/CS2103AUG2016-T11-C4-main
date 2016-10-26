package seedu.address.logic.parser;

import seedu.address.logic.commands.HideFinishedFloatingTaskCommand;

public class HideFinishedFloatingTaskParser implements Parser<HideFinishedFloatingTaskCommand> {

    private final CommandLineParser cmdParser = new CommandLineParser()
                                                    .addArgument("f"); //TODO replace " f" after IndexPrefix.java added

    @Override
    public HideFinishedFloatingTaskCommand parse(String str) throws ParseException {
        cmdParser.parse(str);
        return new HideFinishedFloatingTaskCommand();
    }
}
