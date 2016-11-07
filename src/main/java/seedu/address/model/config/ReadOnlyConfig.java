package seedu.address.model.config;

import java.util.logging.Level;

import javafx.beans.property.ReadOnlyProperty;

/**
 * Unmodifiable view of a {@link Config} object.
 */
public interface ReadOnlyConfig {

    /**
     * Returns the configured log level.
     */
    Level getLogLevel();

    /**
     * Returns the configured task book file path as a read only property.
     */
    ReadOnlyProperty<String> taskBookFilePathProperty();

    /**
     * Returns the configured task book file path.
     */
    String getTaskBookFilePath();

}
