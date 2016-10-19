package seedu.address.storage.config;

import java.io.IOException;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.config.ReadOnlyConfig;

/**
 * Represents a storage for {@link seedu.address.model.config.Config}
 */
public interface ConfigStorage {

    /**
     * Returns the file path of the config file.
     */
    String getConfigFilePath();

    /**
     * Returns the Config data as a {@link ReadOnlyConfig}.
     * Returns {@code Optional.empty()} if config file was not found.
     * @throws DataConversionException if the data in storage cannot be parsed.
     * @throws IOException if an IO error occurred while reading from the storage.
     */
    Optional<ReadOnlyConfig> readConfig() throws DataConversionException, IOException;

    /**
     * @see #readConfig()
     */
    Optional<ReadOnlyConfig> readConfig(String filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyConfig} to the storage.
     * @param config cannot be null.
     * @throws IOException if an IO error occurred while writing to the storage.
     */
    void saveConfig(ReadOnlyConfig config) throws IOException;

    /**
     * @see #saveConfig(ReadOnlyConfig)
     */
    void saveConfig(ReadOnlyConfig config, String filePath) throws IOException;

}
