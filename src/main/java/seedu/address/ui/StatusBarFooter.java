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

    public StatusBarFooter(String saveLocation, ReadOnlyModel model) {
        super(FXML);
        connectTaskCounter(model);
        setSaveLocation("./" + saveLocation);
    }

    private void connectTaskCounter(ReadOnlyModel model) {
        ObservableValue<String> totalTasks = Bindings.size(model.getFloatingTaskList())
                                                .add(Bindings.size(model.getDeadlineTaskList()))
                                                .add(Bindings.size(model.getEventTaskList()))
                                                .asString("Total number of tasks in Task Tracker: %d");
        taskCounter.textProperty().bind(totalTasks);
    }

    private void setTaskCounter(String counterValue) {
        taskCounter.setText("Total number of tasks in Task Tracker: " + counterValue);
    }

    private void setSaveLocation(String location) {
        this.saveLocationStatus.setText(location);
    }

}
