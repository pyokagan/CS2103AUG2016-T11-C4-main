package seedu.address.storage.config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.io.File;
import java.util.logging.Level;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.FileUtil;
import seedu.address.model.config.Config;
import seedu.address.model.config.ReadOnlyConfig;

public class JsonConfigStorageTest {

    private Config typicalConfig;

    private JsonFactory jsonFactory;

    private File configFile;

    private JsonConfigStorage configStorage;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Before
    public void setup() {
        typicalConfig = new Config();
        typicalConfig.setLogLevel(Level.INFO);
        typicalConfig.setTaskBookFilePath("taskbook.json");
        jsonFactory = new JsonFactory();
        configFile = new File(testFolder.getRoot(), "config.json");
        configStorage = new JsonConfigStorage(configFile.getAbsolutePath());
    }

    @Test
    public void constructor_nullPath_assertionError() {
        thrown.expect(AssertionError.class);
        new JsonConfigStorage(null);
    }

    @Test
    public void getConfigFilePath_returnsConfigFilePath() {
        assertEquals(configFile.getAbsolutePath(), configStorage.getConfigFilePath());
    }

    @Test
    public void readConfig_null_assertionError() throws Exception {
        thrown.expect(AssertionError.class);
        configStorage.readConfig(null);
    }

    @Test
    public void readConfig_missingFile_emptyResult() throws Exception {
        assertFalse(configStorage.readConfig().isPresent());
    }

    @Test
    public void readConfig_notJsonFormat_exceptionThrown() throws Exception {
        FileUtil.writeToFile(configFile, "not json format");
        thrown.expect(DataConversionException.class);
        configStorage.readConfig();
    }

    @Test
    public void readConfig_fileInOrder_successfullyRead() throws Exception {
        final JsonGenerator jgen = jsonFactory.createGenerator(configFile, JsonEncoding.UTF8);
        jgen.writeStartObject();
        jgen.writeObjectField("logLevel", "INFO");
        jgen.writeObjectField("taskBookFilePath", "taskbook.json");
        jgen.writeEndObject();
        jgen.close();
        final ReadOnlyConfig actual = configStorage.readConfig().get();
        assertEquals(typicalConfig, actual);
    }

    @Test
    public void readConfig_valuesMissingFromFile_defaultValuesUsed() throws Exception {
        final JsonGenerator jgen = jsonFactory.createGenerator(configFile, JsonEncoding.UTF8);
        jgen.writeStartObject();
        jgen.writeEndObject();
        jgen.close();
        final ReadOnlyConfig actual = configStorage.readConfig().get();
        assertEquals(new Config(), actual);
    }

    @Test
    public void readConfig_extraValuesInFile_extraValuesIgnored() throws Exception {
        final JsonGenerator jgen = jsonFactory.createGenerator(configFile, JsonEncoding.UTF8);
        jgen.writeStartObject();
        jgen.writeObjectField("logLevel", "INFO");
        jgen.writeObjectField("taskBookFilePath", "taskbook.json");
        jgen.writeObjectField("extraField", "extraValue");
        jgen.writeEndObject();
        jgen.close();
        final ReadOnlyConfig actual = configStorage.readConfig().get();
        assertEquals(typicalConfig, actual);
    }

    @Test
    public void saveConfig_nullConfig_assertionFailure() throws Exception {
        thrown.expect(AssertionError.class);
        configStorage.saveConfig(null);
    }

    @Test
    public void saveConfig_nullFile_assertionFailure() throws Exception {
        thrown.expect(AssertionError.class);
        configStorage.saveConfig(typicalConfig, null);
    }

    @Test
    public void saveConfig_allInOrder_success() throws Exception {
        configFile = new File(testFolder.getRoot(), "a/config.json");
        configStorage = new JsonConfigStorage(configFile.getAbsolutePath());

        // Try saving when the file and parent directories do not exist
        configStorage.saveConfig(typicalConfig);
        ReadOnlyConfig readBack = configStorage.readConfig().get();
        assertEquals(typicalConfig, readBack);

        // Try saving when the file exists
        typicalConfig.setLogLevel(Level.FINE);
        configStorage.saveConfig(typicalConfig);
        readBack = configStorage.readConfig().get();
        assertEquals(typicalConfig, readBack);
    }

}
