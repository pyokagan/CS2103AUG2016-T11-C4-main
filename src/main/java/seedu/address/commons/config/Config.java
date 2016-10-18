package seedu.address.commons.config;

import java.util.Objects;
import java.util.logging.Level;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Config values used by the app
 */
public class Config {

    public static final String DEFAULT_CONFIG_FILE = "config.json";

    // Config values customizable through config file
    private Level logLevel = Level.INFO;
    private String userPrefsFilePath = "preferences.json";

    private SimpleStringProperty taskBookFilePath = new SimpleStringProperty("data/taskbook.json");

    public Level getLogLevel() {
        return logLevel;
    }

    public void setLogLevel(Level logLevel) {
        this.logLevel = logLevel;
    }

    public String getUserPrefsFilePath() {
        return userPrefsFilePath;
    }

    public void setUserPrefsFilePath(String userPrefsFilePath) {
        this.userPrefsFilePath = userPrefsFilePath;
    }

    public StringProperty taskBookFilePathProperty() {
        return taskBookFilePath;
    }

    public String getTaskBookFilePath() {
        return taskBookFilePath.get();
    }

    public void setTaskBookFilePath(String taskBookFilePath) {
        this.taskBookFilePath.set(taskBookFilePath);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof Config)) { //this handles null as well.
            return false;
        }

        Config o = (Config)other;

        return Objects.equals(logLevel, o.logLevel)
                && Objects.equals(userPrefsFilePath, o.userPrefsFilePath)
                && Objects.equals(taskBookFilePath.get(), o.taskBookFilePath.get());
    }

    @Override
    public int hashCode() {
        return Objects.hash(logLevel, userPrefsFilePath, taskBookFilePath.get());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Current log level : " + logLevel);
        sb.append("\nPreference file Location : " + userPrefsFilePath);
        sb.append("\nLocal data file location : " + taskBookFilePath.get());
        return sb.toString();
    }

}
