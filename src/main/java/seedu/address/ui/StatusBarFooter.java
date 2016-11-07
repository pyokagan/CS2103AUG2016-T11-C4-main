package seedu.address.ui;

import org.controlsfx.control.StatusBar;

import javafx.beans.binding.Bindings;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import seedu.address.model.ReadOnlyModel;

/**
 * A ui for the status bar that is displayed at the footer of the application.
 */
public class StatusBarFooter extends UiPart<Pane> {

    private static final String FXML = "/view/StatusBarFooter.fxml";

    @FXML
    private StatusBar taskCounter;

    @FXML
    private StatusBar syncStatus;

    @FXML
    private StatusBar saveLocationStatus;

    public StatusBarFooter(ReadOnlyModel model) {
        super(FXML);
        connectTaskCounter(model);
        connectSaveLocation(model);
    }

    private void connectTaskCounter(ReadOnlyModel model) {
        ObservableValue<String> totalTasks = Bindings.size(model.getTaskBook().getFloatingTasks())
                                                .add(Bindings.size(model.getTaskBook().getDeadlineTasks()))
                                                .add(Bindings.size(model.getTaskBook().getEventTasks()))
                                                .asString("Total number of tasks in Task Tracker: %d");
        taskCounter.textProperty().bind(totalTasks);
    }

    private void connectSaveLocation(ReadOnlyModel model) {
        saveLocationStatus.textProperty().bind(model.getConfig().taskBookFilePathProperty());
    }

}
