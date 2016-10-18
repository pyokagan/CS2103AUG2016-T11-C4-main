package seedu.address.storage;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.FileUtil;
import seedu.address.model.UserPrefs;

/**
 * A class to access UserPrefs stored in the hard disk as a json file
 */
public class JsonUserPrefsStorage implements UserPrefsStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonUserPrefsStorage.class);

    private String filePath;

    private final ObjectMapper objectMapper;

    public JsonUserPrefsStorage(String filePath, ObjectMapper objectMapper) {
        this.filePath = filePath;
        this.objectMapper = objectMapper;
    }

    public JsonUserPrefsStorage(String filePath) {
        this(filePath, initDefaultObjectMapper());
    }

    private static ObjectMapper initDefaultObjectMapper() {
        return new ObjectMapper()
            .enable(SerializationFeature.INDENT_OUTPUT)
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .registerModule(new Jdk8Module())
            .registerModule(new JsonStorageModule());
    }

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException {
        return readUserPrefs(filePath);
    }

    /**
     * Similar to {@link #readUserPrefs()}
     * @param prefsFilePath location of the data. Cannot be null.
     * @throws DataConversionException if the file format is not as expected.
     */
    public Optional<UserPrefs> readUserPrefs(String prefsFilePath) throws DataConversionException {
        assert prefsFilePath != null;

        File prefsFile = new File(prefsFilePath);

        if (!prefsFile.exists()) {
            logger.info("Prefs file " + prefsFile + " not found");
            return Optional.empty();
        }

        UserPrefs prefs;

        try {
            prefs = objectMapper.readValue(prefsFile, UserPrefs.class);
        } catch (IOException e) {
            logger.warning("Error reading from prefs file " + prefsFile + ": " + e);
            throw new DataConversionException(e);
        }

        return Optional.of(prefs);
    }

    @Override
    public void saveUserPrefs(UserPrefs userPrefs) throws IOException {
        saveUserPrefs(userPrefs, filePath);
    }

    /**
     * Similar to {@link #saveUserPrefs(UserPrefs)}
     * @param prefsFilePath location of the data. Cannot be null.
     */
    public void saveUserPrefs(UserPrefs userPrefs, String prefsFilePath) throws IOException {
        assert userPrefs != null;
        assert prefsFilePath != null;
        final File file = new File(prefsFilePath);
        FileUtil.createIfMissing(file);
        objectMapper.writeValue(file, userPrefs);
    }
}
