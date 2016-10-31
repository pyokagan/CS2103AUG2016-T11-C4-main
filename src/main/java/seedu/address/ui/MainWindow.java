package seedu.address.ui;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import seedu.address.MainApp;
import seedu.address.commons.util.AppUtil;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.ExitCommandResult;
import seedu.address.logic.commands.HelpCommandResult;
import seedu.address.model.config.Config;
import seedu.address.model.task.TaskType;

/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart<Scene> {

    private static final String ICON = "/images/address_book_32.png";
    private static final String FXML = "/view/MainWindow.fxml";
    public static final int MIN_HEIGHT = 600;
    public static final int MIN_WIDTH = 450;

    // Independent Ui parts residing in this Ui container
    private ResultDisplay resultDisplay;
    private CommandBox commandBox;
    private StatusBarFooter statusBarFooter;

    // Handles to elements of this Ui container
    @FXML
    private VBox rootLayout;

    @FXML
    private UiRegion commandBoxPlaceholder;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private UiRegion taskListPanelPlaceholder;

    private FloatingTaskListPane floatingTaskListPane;

    @FXML
    private UiRegion floatingTaskListRegion;

    private EventTaskListPane eventTaskListPane;

    @FXML
    private UiRegion deadlineTaskListRegion;

    private DeadlineTaskListPane deadlineTaskListPane;

    @FXML
    private UiRegion eventTaskListRegion;

    @FXML
    private UiRegion resultDisplayPlaceholder;

    @FXML
    private UiRegion statusbarPlaceholder;

    private final Stage primaryStage;
    private final Logic logic;

    public MainWindow(Stage primaryStage, Config config, Logic logic) {
        super(FXML);
        this.primaryStage = primaryStage;
        this.logic = logic;

        //Configure the UI
        setTitle(MainApp.NAME);
        setIcon(ICON);
        setWindowMinSize();
        fillInnerParts(config, logic);
        setAccelerators();
        primaryStage.setOnShown(this::onShown);
    }

    private void setAccelerators() {
        helpMenuItem.setAccelerator(KeyCombination.valueOf("F1"));
    }

    void fillInnerParts(Config config, Logic logic) {
        floatingTaskListPane = new FloatingTaskListPane(logic.getFloatingTaskList());
        floatingTaskListRegion.setNode(floatingTaskListPane.getRoot());
        eventTaskListPane = new EventTaskListPane(logic.getEventTaskList());
        eventTaskListRegion.setNode(eventTaskListPane.getRoot());
        deadlineTaskListPane = new DeadlineTaskListPane(logic.getDeadlineTaskList());
        deadlineTaskListRegion.setNode(deadlineTaskListPane.getRoot());
        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.setNode(resultDisplay.getRoot());
        commandBox = new CommandBox(resultDisplay, logic);
        commandBox.setOnCommandResult(this::onCommandResult);
        commandBoxPlaceholder.setNode(commandBox.getRoot());
        statusBarFooter = new StatusBarFooter(config.getTaskBookFilePath());
        statusbarPlaceholder.setNode(statusBarFooter.getRoot());
    }

    private void setTitle(String appTitle) {
        primaryStage.setTitle(appTitle);
    }

    private void setWindowMinSize() {
        primaryStage.setMinHeight(MIN_HEIGHT);
        primaryStage.setMinWidth(MIN_WIDTH);
    }

    @FXML
    private void handleHelp() {
        final HelpWindow helpWindow = new HelpWindow();
        helpWindow.getRoot().showAndWait();
    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        Platform.exit();
    }

    /**
     * Sets the given image as the icon for the primary stage of this UI Part.
     * @param iconSource e.g. {@code "/images/help_icon.png"}
     */
    private void setIcon(String iconSource) {
        primaryStage.getIcons().add(AppUtil.getImage(iconSource));
    }

    private void onCommandResult(CommandResult result) {
        updateTaskSelection();

        if (result instanceof ExitCommandResult) {
            handleExit();
        }

        if (result instanceof HelpCommandResult) {
            handleHelp();
        }
    }

    /**
     * Update task selection in UI.
     */
    private void updateTaskSelection() {
        if (!logic.getTaskSelect().isPresent()) {
            return;
        }
        final TaskType taskType = logic.getTaskSelect().get().getTaskType();
        final int workingIndex = logic.getTaskSelect().get().getWorkingIndex();

        // Floating task list pane
        if (taskType == TaskType.FLOAT) {
            floatingTaskListPane.select(workingIndex);
        } else {
            floatingTaskListPane.clearSelect();
        }

        // Deadline task list pane
        if (taskType == TaskType.DEADLINE) {
            deadlineTaskListPane.select(workingIndex);
        } else {
            deadlineTaskListPane.clearSelect();
        }

        // Event task list pane
        if (taskType == TaskType.EVENT) {
            eventTaskListPane.select(workingIndex);
        } else {
            eventTaskListPane.clearSelect();
        }
    }

    /**
     * Called after the window (primaryStage) is shown.
     */
    private void onShown(WindowEvent ev) {
        commandBox.requestFocus();
    }

}
