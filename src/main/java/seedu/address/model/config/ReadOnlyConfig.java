package seedu.address.model.config;

import java.util.logging.Level;

/**
 * Unmodifiable view of a {@link Config} object.
 */
public interface ReadOnlyConfig {

    /**
     * Returns the configured log level.
     */
    Level getLogLevel();

    /**
     * Returns the configured task book file path.
     */
    String getTaskBookFilePath();

}
