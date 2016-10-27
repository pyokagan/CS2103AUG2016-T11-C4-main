package seedu.address.ui;

import static org.junit.Assert.assertEquals;

import org.controlsfx.control.StatusBar;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.loadui.testfx.GuiTest;

import javafx.scene.Parent;
import seedu.address.testutil.GuiTests;

@Category({GuiTests.class})
public class StatusBarFooterTest extends GuiTest {

    private StatusBarFooter statusBarFooter;

    private StatusBar syncStatus;

    private StatusBar saveLocationStatus;

    @Override
    protected Parent getRootNode() {
        statusBarFooter = new StatusBarFooter("save/location");
        return statusBarFooter.getRoot();
    }

    @Before
    public void setupNodes() {
        syncStatus = find("#syncStatus");
        saveLocationStatus = find("#saveLocationStatus");
    }

    @Test
    public void constructor() {
        assertEquals("Not updated yet in this session", syncStatus.getText());
        assertEquals("./save/location", saveLocationStatus.getText());
    }

}
