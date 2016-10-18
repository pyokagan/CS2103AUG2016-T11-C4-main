package seedu.address.logic.commands;

import java.io.File;
import java.io.IOException;

import com.google.common.io.Files;

import seedu.address.commons.util.FileUtil;

/**
 * Command that changes the directory where application data is stored.
 */
public class SetDataDirectoryCommand extends Command {

    public static final String COMMAND_WORD = "setdatadir";

    private final String directory;

    public SetDataDirectoryCommand(String directory) {
        this.directory = directory;
    }

    @Override
    public CommandResult execute() {
        assert config != null;
        final File newDir = new File(directory);
        final File newTaskBookFile = new File(newDir, "taskbook.json");
        final File newUserPrefsFile = new File(newDir, "userprefs.json");
        if (!newDir.isAbsolute()) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult("Must be an absolute path");
        }
        if (FileUtil.isFileExists(newDir)) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(newDir.getAbsolutePath() + " already exists.");
        }
        try {
            FileUtil.createDirs(newDir);
            Files.move(new File(config.getTaskBookFilePath()), newTaskBookFile);
            Files.move(new File(config.getUserPrefsFilePath()), newUserPrefsFile);
        } catch (IOException e) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(e.getMessage());
        }
        config.setTaskBookFilePath(newTaskBookFile.getAbsolutePath());
        config.setUserPrefsFilePath(newUserPrefsFile.getAbsolutePath());
        return new CommandResult("Data directory changed to: " + newDir.getAbsolutePath());
    }

}
