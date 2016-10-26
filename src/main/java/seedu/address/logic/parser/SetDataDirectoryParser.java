package seedu.address.logic.parser;

import java.io.File;

import seedu.address.logic.commands.SetDataDirectoryCommand;
import seedu.address.logic.parser.CommandLineParser.RestArgument;

/**
 * Parser for the "setdatadir" command.
 */
public class SetDataDirectoryParser implements Parser<SetDataDirectoryCommand> {

    public static final String COMMAND_WORD = "setdatadir";

    private final RestArgument<File> newDirArg = new RestArgument<>("NEW_DIRECTORY", new FileParser());
    private final CommandLineParser cmdParser = new CommandLineParser().addArgument(newDirArg);

    @Override
    public SetDataDirectoryCommand parse(String str) throws ParseException {
        cmdParser.parse(str);
        return new SetDataDirectoryCommand(newDirArg.getValue());
    }

}
