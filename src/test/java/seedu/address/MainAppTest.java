package seedu.address;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.rules.TemporaryFolder;
import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;

import javafx.application.Application;
import seedu.address.testutil.GuiTests;

/**
 * Integration tests
 */
@Category({GuiTests.class})
public class MainAppTest extends FxRobot {

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    private Application application;

    @BeforeClass
    public static void setupFxToolkit() throws Exception {
        FxToolkit.registerPrimaryStage();
    }

    @Before
    public void setup() throws Exception {
        File configFile = setupConfig(new File(tempFolder.getRoot(), "taskbook.json"),
                                      new File(tempFolder.getRoot(), "userprefs.json"));
        application = FxToolkit.setupApplication(() -> new MainApp(configFile.getAbsolutePath()));
        FxToolkit.showStage();
    }

    private File setupConfig(File taskBookFile, File userPrefsFile) throws Exception {
        File configFile = tempFolder.newFile("config.json");
        JsonGenerator jgen = new JsonFactory().createGenerator(configFile, JsonEncoding.UTF8);
        jgen.writeStartObject();
        jgen.writeObjectField("taskBookFilePath", taskBookFile.getAbsolutePath());
        jgen.writeObjectField("userPrefsFilePath", userPrefsFile.getAbsolutePath());
        jgen.writeEndObject();
        jgen.close();
        return configFile;
    }

    @After
    public void teardown() throws Exception {
        FxToolkit.cleanupApplication(application);
    }

    @Test
    public void startStop() {
    }

}
