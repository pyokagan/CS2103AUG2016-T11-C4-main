package seedu.address.commons.config;

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

public class JsonConfigUtilTest {

    private Config typicalConfig;

    private JsonFactory jsonFactory;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    @Before
    public void setup() {
        typicalConfig = new Config();
        typicalConfig.setLogLevel(Level.INFO);
        typicalConfig.setUserPrefsFilePath("C:\\\\preferences.json");
        typicalConfig.setTaskBookFilePath("taskbook.json");
        jsonFactory = new JsonFactory();
    }

    @Test
    public void readConfig_null_assertionFailure() throws Exception {
        thrown.expect(AssertionError.class);
        JsonConfigUtil.readConfig(null);
    }

    @Test
    public void readConfig_missingFile_emptyResult() throws Exception {
        final File missingFile = new File(tempFolder.getRoot(), "missingFile.json");
        assertFalse(JsonConfigUtil.readConfig(missingFile.getAbsolutePath()).isPresent());
    }

    @Test
    public void readConfig_notJasonFormat_exceptionThrown() throws Exception {
        final File file = tempFolder.newFile("invalidFormat.json");
        thrown.expect(DataConversionException.class);
        JsonConfigUtil.readConfig(file.getAbsolutePath());
    }

    @Test
    public void readConfig_fileInOrder_successfullyRead() throws Exception {
        final File file = tempFolder.newFile("typical.json");
        final JsonGenerator jgen = jsonFactory.createGenerator(file, JsonEncoding.UTF8);
        jgen.writeStartObject();
        jgen.writeObjectField("logLevel", "INFO");
        jgen.writeObjectField("userPrefsFilePath", "C:\\\\preferences.json");
        jgen.writeObjectField("taskBookFilePath", "taskbook.json");
        jgen.writeEndObject();
        jgen.close();
        final Config actual = JsonConfigUtil.readConfig(file.getAbsolutePath()).get();
        assertEquals(typicalConfig, actual);
    }

    @Test
    public void readConfig_valuesMissingFromFile_defaultValuesUsed() throws Exception {
        final File file = tempFolder.newFile("valuesMissing.json");
        final JsonGenerator jgen = jsonFactory.createGenerator(file, JsonEncoding.UTF8);
        jgen.writeStartObject();
        jgen.writeEndObject();
        jgen.close();
        final Config actual = JsonConfigUtil.readConfig(file.getAbsolutePath()).get();
        assertEquals(new Config(), actual);
    }

    @Test
    public void readConfig_extraValuesInFile_extraValuesIgnored() throws Exception {
        final File file = tempFolder.newFile("extraValues.json");
        final JsonGenerator jgen = jsonFactory.createGenerator(file, JsonEncoding.UTF8);
        jgen.writeStartObject();
        jgen.writeObjectField("logLevel", "INFO");
        jgen.writeObjectField("userPrefsFilePath", "C:\\\\preferences.json");
        jgen.writeObjectField("taskBookFilePath", "taskbook.json");
        jgen.writeObjectField("extraField", "extraValue");
        jgen.writeEndObject();
        jgen.close();
        final Config actual = JsonConfigUtil.readConfig(file.getAbsolutePath()).get();
        assertEquals(typicalConfig, actual);
    }

    @Test
    public void saveConfig_nullConfig_assertionFailure() throws Exception {
        thrown.expect(AssertionError.class);
        JsonConfigUtil.saveConfig(null, tempFolder.newFile().getAbsolutePath());
    }

    @Test
    public void saveConfig_nullFile_assertionFailure() throws Exception {
        thrown.expect(AssertionError.class);
        JsonConfigUtil.saveConfig(new Config(), null);
    }

    @Test
    public void saveConfig_allInOrder_success() throws Exception {
        final File file = new File(tempFolder.getRoot(), "tempConfig.json");

        // Try writing when the file does not exist
        JsonConfigUtil.saveConfig(typicalConfig, file.getAbsolutePath());
        Config readBack = JsonConfigUtil.readConfig(file.getAbsolutePath()).get();
        assertEquals(typicalConfig, readBack);

        // Try saving when the file exists
        typicalConfig.setLogLevel(Level.FINE);
        JsonConfigUtil.saveConfig(typicalConfig, file.getAbsolutePath());
        readBack = JsonConfigUtil.readConfig(file.getAbsolutePath()).get();
        assertEquals(typicalConfig, readBack);
    }

}
