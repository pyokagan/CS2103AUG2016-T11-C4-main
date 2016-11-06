package seedu.address.ui;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;

import javafx.scene.control.ListView;
import seedu.address.model.IndexedItem;
import seedu.address.model.ModelManager;
import seedu.address.model.TaskBookBuilder;
import seedu.address.model.config.Config;
import seedu.address.model.task.EventTask;
import seedu.address.testutil.GuiTests;

@Category({GuiTests.class})
public class EventTaskListPaneTest extends FxRobot {

    private final ModelManager model = new ModelManager(new Config(),
                                                        new TaskBookBuilder().addTypicalEventTasks()
                                                            .build());

    private EventTaskListPane pane;

    private ListView<IndexedItem<EventTask>> listView;

    @BeforeClass
    public static void setupFxToolkit() throws Exception {
        FxToolkit.registerPrimaryStage();
    }

    @Before
    public void setup() throws Exception {
        FxToolkit.setupSceneRoot(() -> {
            pane = new EventTaskListPane(model.getEventTaskList());
            return pane.getRoot();
        });
        FxToolkit.showStage();
        listView = lookup(".list-view").query();
    }

    @After
    public void teardown() throws Exception {
        FxToolkit.cleanupStages();
    }

    @Test
    public void select_selectsCorrectIndex() {
        interact(() -> pane.select(2));
        assertEquals(1, listView.getSelectionModel().getSelectedIndex());
    }

    @Test
    public void clearSelect() {
        interact(() -> listView.getSelectionModel().select(0));
        assertEquals(0, listView.getSelectionModel().getSelectedIndex());
        interact(() -> pane.clearSelect());
        assertEquals(-1, listView.getSelectionModel().getSelectedIndex());
    }

}
