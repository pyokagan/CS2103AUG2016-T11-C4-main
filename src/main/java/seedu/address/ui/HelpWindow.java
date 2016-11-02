package seedu.address.ui;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;


/**
 * Controller for a help page
 */
public class HelpWindow {

    private static final String ICON = "/images/help_icon.png";
    private static final String FXML = "/view/HelpWindow.fxml";
    private static final String USERGUIDE_URL =
            "https://github.com/se-edu/addressbook-level4/blob/master/docs/UserGuide.md";

    @FXML
    private WebView webView;

    public HelpWindow() {

        Alert helpBox = new Alert(javafx.scene.control.Alert.AlertType.NONE);
        helpBox.initModality(Modality.NONE);

        //add icon
        Stage stage = (Stage) helpBox.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(ICON));

        //set help box to be on top even when command tray is in focus
        stage.setAlwaysOnTop(true);
        helpBox.initModality(Modality.NONE);

        //set contents
        helpBox.setTitle("HELP");
        helpBox.setHeaderText("Press <ESC> to close");
        helpBox.setContentText("");

        //press escape to close
        stage.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode().equals(KeyCode.ESCAPE)) {
                    stage.close();
                    keyEvent.consume();
                }
            }
        });

        helpBox.showAndWait();
    }
}
