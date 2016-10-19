package seedu.address.ui;

import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;
import org.testfx.util.WaitForAsyncUtils;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Window;
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.ShowHelpRequestEvent;
import seedu.address.logic.Logic;
import seedu.address.model.config.Config;
import seedu.address.model.task.DeadlineTask;
import seedu.address.model.task.EventTask;
import seedu.address.model.task.FloatingTask;
import seedu.address.testutil.GuiTests;

@Category({GuiTests.class})
public class UiManagerTest extends FxRobot {

    private Config config;

    private ObservableList<FloatingTask> floatingTaskList;

    private ObservableList<EventTask> eventTaskList;

    private ObservableList<DeadlineTask> deadlineTaskList;

    @Mock
    private Logic logic;

    private UiManager uiManager;

    @BeforeClass
    public static void setupFxToolkit() throws Exception {
        FxToolkit.registerPrimaryStage();
    }

    @Before
    public void setup() throws Exception {
        MockitoAnnotations.initMocks(this);
        floatingTaskList = FXCollections.observableArrayList();
        eventTaskList = FXCollections.observableArrayList();
        deadlineTaskList = FXCollections.observableArrayList();
        Mockito.when(logic.getFilteredFloatingTaskList())
               .thenReturn(FXCollections.unmodifiableObservableList(floatingTaskList));
        Mockito.when(logic.getFilteredDeadlineTaskList())
               .thenReturn(FXCollections.unmodifiableObservableList(deadlineTaskList));
        Mockito.when(logic.getFilteredEventTaskList())
               .thenReturn(FXCollections.unmodifiableObservableList(eventTaskList));
        config = new Config();
        uiManager = new UiManager(logic, config);
        FxToolkit.setupStage(stage -> {
            uiManager.start(stage);
        });
        FxToolkit.showStage();
    }

    @After
    public void teardown() throws Exception {
        interact(() -> uiManager.stop());
        FxToolkit.cleanupStages();
    }

    @Test
    public void startStop_works() {
    }

    @Test
    public void onShowHelpRequestEvent_showsHelp() throws Exception {
        Platform.runLater((() -> EventsCenter.getInstance().post(new ShowHelpRequestEvent())));
        WaitForAsyncUtils.waitFor(3, TimeUnit.SECONDS, () -> {
            try {
                Window w = window(window -> from(rootNode(window)).lookup("#webView").tryQuery().isPresent());
                interact(() -> w.hide());
                return true;
            } catch (NoSuchElementException e) {
                return false;
            }
        });
    }

}
