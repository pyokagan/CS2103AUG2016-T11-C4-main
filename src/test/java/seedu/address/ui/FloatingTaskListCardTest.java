package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;

import javafx.scene.control.Label;
import seedu.address.model.task.TypicalFloatingTasks;
import seedu.address.testutil.GuiTests;

@Category({GuiTests.class})
public class FloatingTaskListCardTest extends FxRobot {

    private TypicalFloatingTasks tft = new TypicalFloatingTasks();

    private FloatingTaskListCard floatingTaskListCard;

    private Label indexLabel;

    private Label nameLabel;

    private Label priorityLabel;

    @BeforeClass
    public static void setupFxToolkit() throws Exception {
        FxToolkit.registerPrimaryStage();
    }

    private void setupNodes() throws Exception {
        FxToolkit.showStage();
        indexLabel = lookup("#indexLabel").query();
        nameLabel = lookup("#nameLabel").query();
        priorityLabel = lookup("#priorityLabel").query();
    }

    @Test
    public void withFloatingTask_displaysInfoInLabels() throws Exception {
        FxToolkit.setupSceneRoot(() -> {
            floatingTaskListCard = new FloatingTaskListCard(tft.buyAHelicopter, 42);
            return floatingTaskListCard.getRoot();
        });
        setupNodes();
        assertEquals("42. ", indexLabel.getText());
        assertEquals("buy A Helicopter", nameLabel.getText());
        assertEquals("4", priorityLabel.getText());
    }

    @Test
    public void nullFloatingTask_becomesInvisible() throws Exception {
        FxToolkit.setupSceneRoot(() -> {
            floatingTaskListCard = new FloatingTaskListCard(null, 31);
            return floatingTaskListCard.getRoot();
        });
        assertFalse(floatingTaskListCard.getRoot().isVisible());
    }

}
