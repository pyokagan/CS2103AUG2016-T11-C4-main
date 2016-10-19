package seedu.address.storage.config;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.FileUtil;
import seedu.address.model.config.Config;
import seedu.address.model.config.ReadOnlyConfig;

public class JsonConfigStorage implements ConfigStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonConfigStorage.class);

    private final String filePath;

    private final ObjectMapper objectMapper;

    public JsonConfigStorage(String filePath, ObjectMapper objectMapper) {
        assert !CollectionUtil.isAnyNull(filePath, objectMapper);
        this.filePath = filePath;
        this.objectMapper = objectMapper;
    }

    public JsonConfigStorage(String filePath) {
        this(filePath, initDefaultObjectMapper());
    }

    private static ObjectMapper initDefaultObjectMapper() {
        return new ObjectMapper()
                .enable(SerializationFeature.INDENT_OUTPUT)
                .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                .registerModule(new Jdk8Module())
                .registerModule(new JsonConfigModule());
    }

    @Override
    public String getConfigFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyConfig> readConfig() throws DataConversionException, IOException {
        return readConfig(filePath);
    }

    @Override
    public Optional<ReadOnlyConfig> readConfig(String filePath) throws DataConversionException, IOException {
        assert filePath != null;
        final File configFile = new File(filePath);
        if (!configFile.exists()) {
            logger.info("Config file " + configFile + " not found");
            return Optional.empty();
        }
        try {
            return Optional.of(objectMapper.readValue(configFile, Config.class));
        } catch (JsonProcessingException e) {
            throw new DataConversionException(e);
        }
    }

    @Override
    public void saveConfig(ReadOnlyConfig config) throws IOException {
        saveConfig(config, filePath);
    }

    @Override
    public void saveConfig(ReadOnlyConfig config, String filePath) throws IOException {
        assert !CollectionUtil.isAnyNull(config, filePath);
        final File file = new File(filePath);
        FileUtil.createIfMissing(file);
        objectMapper.writeValue(file, config);
    }

}
