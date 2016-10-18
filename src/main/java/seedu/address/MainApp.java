package seedu.address;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Application;
import javafx.stage.Stage;
import seedu.address.commons.config.Config;
import seedu.address.commons.config.JsonConfigUtil;
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.Version;
import seedu.address.commons.events.ui.ExitAppRequestEvent;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.Logic;
import seedu.address.logic.LogicManager;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyTaskBook;
import seedu.address.model.TaskBook;
import seedu.address.model.UserPrefs;
import seedu.address.storage.Storage;
import seedu.address.storage.StorageManager;
import seedu.address.ui.Ui;
import seedu.address.ui.UiManager;

/**
 * The main entry point to the application.
 */
public class MainApp extends Application {
    private static final Logger logger = LogsCenter.getLogger(MainApp.class);

    public static final Version VERSION = new Version(1, 0, 0, true);

    /** Name of the application */
    public static final String NAME = "Task Tracker";

    private String configPath;
    private Ui ui;
    private Logic logic;
    private Storage storage;
    private Model model;
    private Config config;
    private UserPrefs userPrefs;

    public MainApp() {
        this(null);
    }

    public MainApp(String configPath) {
        this.configPath = configPath;
    }

    @Override
    public void init() throws Exception {
        logger.info("=============================[ Initializing TaskBook ]===========================");
        super.init();

        config = initConfig();
        storage = new StorageManager(config.taskBookFilePathProperty(), config.getUserPrefsFilePath());

        userPrefs = initPrefs(config);

        initLogging(config);

        model = initModelManager(storage);

        logic = new LogicManager(config, model, storage);

        ui = new UiManager(logic, config, userPrefs);

        initEventsCenter();
    }

    private String getApplicationParameter(String parameterName) {
        Map<String, String> applicationParameters = getParameters().getNamed();
        return applicationParameters.get(parameterName);
    }

    private Model initModelManager(Storage storage) {
        Optional<ReadOnlyTaskBook> addressBookOptional;
        ReadOnlyTaskBook initialData;
        try {
            addressBookOptional = storage.readTaskBook();
            if (!addressBookOptional.isPresent()) {
                logger.info("Data file not found. Will be starting with an empty TaskBook");
            }
            initialData = addressBookOptional.orElse(new TaskBook());
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty TaskBook");
            initialData = new TaskBook();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. . Will be starting with an empty TaskBook");
            initialData = new TaskBook();
        }

        return new ModelManager(initialData);
    }

    private void initLogging(Config config) {
        LogsCenter.init(config);
    }

    private Config initConfig() {
        Config initializedConfig;

        if (configPath == null) {
            configPath = getApplicationParameter("config");
        }
        if (configPath == null) {
            configPath = Config.DEFAULT_CONFIG_FILE;
        }

        logger.info("Using config file : " + configPath);

        try {
            Optional<Config> configOptional = JsonConfigUtil.readConfig(configPath);
            initializedConfig = configOptional.orElse(new Config());
        } catch (DataConversionException e) {
            logger.warning("Config file at " + configPath + " is not in the correct format. "
                           + "Using default config properties");
            initializedConfig = new Config();
        }

        //Update config file in case it was missing to begin with or there are new/unused fields
        try {
            JsonConfigUtil.saveConfig(initializedConfig, configPath);
        } catch (IOException e) {
            logger.warning("Failed to save config file : " + StringUtil.getDetails(e));
        }
        return initializedConfig;
    }

    private UserPrefs initPrefs(Config config) {
        assert config != null;

        String prefsFilePath = config.getUserPrefsFilePath();
        logger.info("Using prefs file : " + prefsFilePath);

        UserPrefs initializedPrefs;
        try {
            Optional<UserPrefs> prefsOptional = storage.readUserPrefs();
            initializedPrefs = prefsOptional.orElse(new UserPrefs());
        } catch (DataConversionException e) {
            logger.warning("UserPrefs file at " + prefsFilePath + " is not in the correct format. "
                           + "Using default user prefs");
            initializedPrefs = new UserPrefs();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. . Will be starting with an empty TaskBook");
            initializedPrefs = new UserPrefs();
        }

        //Update prefs file in case it was missing to begin with or there are new/unused fields
        try {
            storage.saveUserPrefs(initializedPrefs);
        } catch (IOException e) {
            logger.warning("Failed to save config file : " + StringUtil.getDetails(e));
        }

        return initializedPrefs;
    }

    private void initEventsCenter() {
        EventsCenter.getInstance().registerHandler(this);
    }

    @Override
    public void start(Stage primaryStage) {
        logger.info("Starting TaskBook " + MainApp.VERSION);
        ui.start(primaryStage);
    }

    @Override
    public void stop() {
        logger.info("============================ [ Stopping Address Book ] =============================");
        ui.stop();
        try {
            storage.saveUserPrefs(userPrefs);
        } catch (IOException e) {
            logger.severe("Failed to save preferences " + StringUtil.getDetails(e));
        }
        try {
            JsonConfigUtil.saveConfig(config, configPath);
        } catch (IOException e) {
            logger.severe("Failed to save config" + StringUtil.getDetails(e));
        }
    }

    @Subscribe
    public void handleExitAppRequestEvent(ExitAppRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        this.stop();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
