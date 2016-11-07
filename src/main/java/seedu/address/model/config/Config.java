package seedu.address.model.config;

import java.util.Objects;
import java.util.logging.Level;

import javafx.beans.property.ReadOnlyProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Config values used by the app
 */
public class Config implements ReadOnlyConfig {
    public static final String DEFAULT_CONFIG_FILE = "config.json";
    public static final Level DEFAULT_LOG_LEVEL = Level.INFO;
    public static final String DEFAULT_TASK_BOOK_FILE_PATH = "data/taskbook.json";

    // Config values customizable through config file
    private Level logLevel = DEFAULT_LOG_LEVEL;
    private final SimpleStringProperty taskBookFilePath = new SimpleStringProperty(DEFAULT_TASK_BOOK_FILE_PATH);

    public Config() {
    }

    public Config(ReadOnlyConfig config) {
        this();
        resetData(config);
    }

    public void resetData(ReadOnlyConfig config) {
        setLogLevel(config.getLogLevel());
        setTaskBookFilePath(config.getTaskBookFilePath());
    }

    @Override
    public Level getLogLevel() {
        return logLevel;
    }

    public void setLogLevel(Level logLevel) {
        this.logLevel = logLevel;
    }

    @Override
    public ReadOnlyProperty<String> taskBookFilePathProperty() {
        return taskBookFilePath;
    }

    @Override
    public String getTaskBookFilePath() {
        return taskBookFilePath.get();
    }

    public void setTaskBookFilePath(String taskBookFilePath) {
        assert taskBookFilePath != null;
        this.taskBookFilePath.set(taskBookFilePath);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof ReadOnlyConfig)) { //this handles null as well.
            return false;
        }

        ReadOnlyConfig o = (ReadOnlyConfig)other;

        return Objects.equals(logLevel, o.getLogLevel())
                && Objects.equals(getTaskBookFilePath(), o.getTaskBookFilePath());
    }

    @Override
    public int hashCode() {
        return Objects.hash(logLevel, taskBookFilePath);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Current log level : " + logLevel);
        sb.append("\nLocal data file location : " + taskBookFilePath.get());
        return sb.toString();
    }

}
