package seedu.address.logic.parser;

import java.io.File;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.IncorrectCommand;
import seedu.address.logic.commands.SetDataDirectoryCommand;

/**
 * TaskTrackerParser for the "setdatadir" command.
 */
public class SetDataDirectoryParser {

    public static final String COMMAND_WORD = "setdatadir";

    public static final String MESSAGE_USAGE = "Parameters: NEW_DIRECTORY\n"
            + "Example: " + COMMAND_WORD + " /my/new/directory";

    private static final String MESSAGE_INVALID_PATH = "%s is not a valid path.";

    private static final String MESSAGE_ABSOLUTE = "%s is not an absolute path.";

    public Command parse(String args) {
        args = args.trim();
        if (args.isEmpty()) {
            return new IncorrectCommand(MESSAGE_USAGE);
        }
        final File newDir;
        try {
            newDir = Paths.get(args).toFile();
        } catch (InvalidPathException e) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_PATH, args));
        }
        if (!newDir.isAbsolute()) {
            return new IncorrectCommand(String.format(MESSAGE_ABSOLUTE, args));
        }
        return new SetDataDirectoryCommand(newDir);
    }

}
