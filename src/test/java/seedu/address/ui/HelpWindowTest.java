package seedu.address.ui;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;

import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.web.WebView;
import seedu.address.testutil.GuiTests;

@Category({GuiTests.class})
public class HelpWindowTest extends FxRobot {

    private HelpWindow helpWindow;
    private WebView webView;

    @BeforeClass
    public static void setupFxToolkit() throws Exception {
        FxToolkit.registerPrimaryStage();
    }

    @Before
    public void setupHelpWindow() throws Exception {
        FxToolkit.registerStage(() -> {
            helpWindow = new HelpWindow();
            return helpWindow.getStage();
        });
        FxToolkit.showStage();
    }

    @After
    public void teardownHelpWindow() throws Exception {
        FxToolkit.cleanupStages();
    }

    @Test
    public void constructor() {
        clickOn(helpWindow.getStage(), MouseButton.PRIMARY);
        press(KeyCode.ENTER);
        release(KeyCode.ENTER);
        webView = lookup("#webView").query();
        assertEquals("https://github.com/CS2103AUG2016-T11-C4/main/blob/master/docs/UserGuide.md", webView.getEngine().getLocation());
        sleep(3000);
        clickOn(8000, 0, MouseButton.PRIMARY);
        sleep(1000);
        press(KeyCode.ESCAPE);
        release(KeyCode.ESCAPE);
    }

}
