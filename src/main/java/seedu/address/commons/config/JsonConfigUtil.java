package seedu.address.commons.config;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;

/**
 * JSON save/load functionality for {@link Config}.
 */
public class JsonConfigUtil {

    private static final Logger logger = LogsCenter.getLogger(JsonConfigUtil.class);

    private static ObjectMapper objectMapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .registerModule(new JsonConfigModule());

    /**
     * Returns the {@link Config} object from the given file or {@code Optional.empty()} object if the file
     * is not found. If any values are missing from the file, default values will be used, as long as the
     * file is a valid JSON file.
     * @param configFilePath cannot be null.
     * @throws DataConversionException if the file format is not as expected.
     */
    public static Optional<Config> readConfig(String configFilePath) throws DataConversionException {
        assert configFilePath != null;
        final File configFile = new File(configFilePath);
        if (!configFile.exists()) {
            logger.info("Config file " + configFile + " not found");
            return Optional.empty();
        }

        final Config config;
        try {
            config = objectMapper.readValue(configFile, Config.class);
        } catch (IOException e) {
            logger.warning("Error reading from config file " + configFile + ": " + e);
            throw new DataConversionException(e);
        }

        return Optional.of(config);
    }

    /**
     * Saves the {@link Config} object to the specified file.
     * Overwrites existing file if it exists, creates a new file if it doesn't.
     * @param config cannot be null
     * @param configFilePath cannot be null
     * @throws IOException if there was an error during writing to the file
     */
    public static void saveConfig(Config config, String configFilePath) throws IOException {
        assert config != null;
        assert configFilePath != null;
        objectMapper.writeValue(new File(configFilePath), config);
    }

}
