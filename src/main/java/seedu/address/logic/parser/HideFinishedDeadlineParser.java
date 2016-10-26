package seedu.address.logic.parser;

import seedu.address.logic.commands.HideFinishedDeadlineCommand;

public class HideFinishedDeadlineParser implements Parser<HideFinishedDeadlineCommand> {

    private final CommandLineParser cmdParser = new CommandLineParser()
                                                    .addArgument("d"); //TODO replace " d" after IndexPrefix.java added

    @Override
    public HideFinishedDeadlineCommand parse(String str) throws ParseException {
        cmdParser.parse(str);
        return new HideFinishedDeadlineCommand();
    }
}
