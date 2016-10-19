package seedu.address.model.config;

import java.util.Objects;
import java.util.logging.Level;

/**
 * Config values used by the app
 */
public class Config {

    public static final String DEFAULT_CONFIG_FILE = "config.json";

    // Config values customizable through config file
    private Level logLevel = Level.INFO;
    private String taskBookFilePath = "data/taskbook.json";

    public Config() {
    }

    public Level getLogLevel() {
        return logLevel;
    }

    public void setLogLevel(Level logLevel) {
        this.logLevel = logLevel;
    }

    public String getTaskBookFilePath() {
        return taskBookFilePath;
    }

    public void setTaskBookFilePath(String taskBookFilePath) {
        this.taskBookFilePath = taskBookFilePath;
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
                && Objects.equals(taskBookFilePath, o.taskBookFilePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(logLevel, taskBookFilePath);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Current log level : " + logLevel);
        sb.append("\nLocal data file location : " + taskBookFilePath);
        return sb.toString();
    }

}
