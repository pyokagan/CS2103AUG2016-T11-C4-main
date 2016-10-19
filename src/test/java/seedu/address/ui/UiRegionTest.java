package seedu.address.ui;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.loadui.testfx.GuiTest;

import javafx.scene.Parent;
import javafx.scene.layout.Region;
import seedu.address.testutil.GuiTests;

@Category({GuiTests.class})
public class UiRegionTest extends GuiTest {

    private UiRegion uiRegion;

    @Override
    protected Parent getRootNode() {
        uiRegion = new UiRegion();
        return uiRegion;
    }

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), uiRegion.getChildrenUnmodifiable());
    }

    @Test
    public void setNode_addsOrReplacesChild() {
        final Region node1 = new Region();
        interact(() -> uiRegion.setNode(node1));
        assertEquals(node1, uiRegion.getNode());
        assertEquals(Arrays.asList(node1), uiRegion.getChildrenUnmodifiable());
        final Region node2 = new Region();
        interact(() -> uiRegion.setNode(node2));
        assertEquals(node2, uiRegion.getNode());
        assertEquals(Arrays.asList(node2), uiRegion.getChildrenUnmodifiable());
    }

}
