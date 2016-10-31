package seedu.address.logic;

import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.storage.DataSavingExceptionEvent;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandException;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.parser.ParseException;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.TaskTrackerParser;
import seedu.address.model.IndexedItem;
import seedu.address.model.Model;
import seedu.address.model.TaskBookChangeListener;
import seedu.address.model.config.Config;
import seedu.address.model.config.ReadOnlyConfig;
import seedu.address.model.task.DeadlineTask;
import seedu.address.model.task.EventTask;
import seedu.address.model.task.FloatingTask;
import seedu.address.model.task.TaskSelect;
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
    public CommandResult execute(Command command) throws CommandException {
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
    public CommandResult execute(String commandText) throws ParseException, CommandException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");
        return execute(parser.parse(commandText));
    }

    private void updateTaskBookStorage(TaskBookChangeListener listener) {
        try {
            if (listener.getHasChanged()) {
                logger.info("Task book data changed, saving to file");
                storage.saveTaskBook(model.getTaskBook());
            }
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }

    private void updateConfigStorage(Config oldConfig) {
        final ReadOnlyConfig newConfig = model.getConfig();
        try {
            if (!oldConfig.equals(newConfig)) {
                logger.info("Config changed, saving to file");
                storage.saveConfig(newConfig);
            }

            if (!oldConfig.getTaskBookFilePath().equals(newConfig.getTaskBookFilePath())) {
                logger.info("Task book file path changed, moving task book");
                storage.moveTaskBook(newConfig.getTaskBookFilePath());
            }
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }

    @Override
    public Optional<TaskSelect> getTaskSelect() {
        return model.getTaskSelect();
    }

    @Override
    public ObservableList<IndexedItem<FloatingTask>> getFloatingTaskList() {
        return model.getFloatingTaskList();
    }

    @Override
    public ObservableList<IndexedItem<DeadlineTask>> getDeadlineTaskList() {
        return model.getDeadlineTaskList();
    }

    @Override
    public ObservableList<IndexedItem<EventTask>> getEventTaskList() {
        return model.getEventTaskList();
    }

}
