package seedu.address.ui;

import org.controlsfx.control.StatusBar;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;

/**
 * A ui for the status bar that is displayed at the footer of the application.
 */
public class StatusBarFooter extends UiPart<Pane> {

    private static final String FXML = "/view/StatusBarFooter.fxml";

    @FXML
    private StatusBar syncStatus;

    @FXML
    private StatusBar saveLocationStatus;

    public StatusBarFooter(String saveLocation) {
        super(FXML);
        setSaveLocation("./" + saveLocation);
    }

    private void setSaveLocation(String location) {
        this.saveLocationStatus.setText(location);
    }

}
