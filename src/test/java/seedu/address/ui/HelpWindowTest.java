package seedu.address.ui;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;

import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import seedu.address.testutil.GuiTests;

@Category({GuiTests.class})
public class HelpWindowTest extends FxRobot {

    private HelpWindow helpWindow;

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
        press(KeyCode.ESCAPE);
        release(KeyCode.ESCAPE);
    }

}
