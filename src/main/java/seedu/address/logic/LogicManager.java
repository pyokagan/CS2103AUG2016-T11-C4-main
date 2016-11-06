package seedu.address.logic;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandException;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.parser.ParseException;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.TaskTrackerParser;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyModel;
import seedu.address.model.TaskBookChangeListener;
import seedu.address.model.config.Config;
import seedu.address.model.config.ReadOnlyConfig;
import seedu.address.storage.Storage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager extends ComponentManager implements Logic {
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final Parser<? extends Command> parser;

    public LogicManager(Model model, Storage storage, Parser<? extends Command> parser) {
        this.model = model;
        this.storage = storage;
        this.parser = parser;
    }

    public LogicManager(Model model, Storage storage) {
        this(model, storage, new TaskTrackerParser());
    }

    @Override
    public CommandResult execute(Command command) throws CommandException, IOException {
        final TaskBookChangeListener taskBookListener = new TaskBookChangeListener(model.getTaskBook());
        final Config oldConfig = new Config(model.getConfig());
        final CommandResult result = command.execute(model);
        updateConfigStorage(oldConfig);
        updateTaskBookStorage(taskBookListener);
        if (model.hasUncommittedChanges()) {
            model.recordState(command.toString());
        }
        return result;
    }

    @Override
    public CommandResult execute(String commandText) throws ParseException, CommandException, IOException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");
        return execute(parser.parse(commandText));
    }

    private void updateTaskBookStorage(TaskBookChangeListener listener) throws IOException {
        if (listener.getHasChanged()) {
            logger.info("Task book data changed, saving to file");
            storage.saveTaskBook(model.getTaskBook());
        }
    }

    private void updateConfigStorage(Config oldConfig) throws IOException {
        final ReadOnlyConfig newConfig = model.getConfig();
        if (!oldConfig.equals(newConfig)) {
            logger.info("Config changed, saving to file");
            storage.saveConfig(newConfig);
        }

        if (!oldConfig.getTaskBookFilePath().equals(newConfig.getTaskBookFilePath())) {
            logger.info("Task book file path changed, moving task book");
            storage.moveTaskBook(newConfig.getTaskBookFilePath());
        }
    }

    @Override
    public List<String> autocomplete(String input, int pos) {
        return parser.autocomplete(model, input, pos);
    }

    @Override
    public ReadOnlyModel getModel() {
        return model;
    }

}
