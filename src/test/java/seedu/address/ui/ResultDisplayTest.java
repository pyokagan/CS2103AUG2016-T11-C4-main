package seedu.address.ui;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.loadui.testfx.GuiTest;

import javafx.scene.Parent;
import javafx.scene.control.TextArea;
import seedu.address.testutil.GuiTests;

@Category({GuiTests.class})
public class ResultDisplayTest extends GuiTest {

    private ResultDisplay resultDisplay;

    private TextArea textArea;

    @Override
    protected Parent getRootNode() {
        resultDisplay = new ResultDisplay();
        return resultDisplay.getRoot();
    }

    @Before
    public void setupNodes() {
        textArea = find("#resultDisplay");
    }

    @Test
    public void constructor() {
        assertEquals("", textArea.getText());
    }

    @Test
    public void postMessage_setsMessage() {
        resultDisplay.postMessage("test message");
        assertEquals("test message", textArea.getText());
    }

}
