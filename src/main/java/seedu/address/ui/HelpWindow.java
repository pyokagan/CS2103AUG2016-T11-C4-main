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

import seedu.address.commons.util.FxViewUtil;

/**
 * Controller for a help page
 */
public class HelpWindow extends UiPart<Stage> {

    private static final String ICON = "/images/help_icon.png";
    private static final String FXML = "/view/HelpWindow.fxml";
    private static final String USERGUIDE_URL =
            "https://github.com/se-edu/addressbook-level4/blob/master/docs/UserGuide.md";

    @FXML
    private WebView webView;

    public HelpWindow() {
        super(FXML);
        getRoot().initModality(Modality.WINDOW_MODAL);
        FxViewUtil.setStageIcon(getRoot(), ICON);
        webView.getEngine().load(USERGUIDE_URL);

        Alert helpBox = new Alert(javafx.scene.control.Alert.AlertType.CONFIRMATION);

        //add icon
        Stage stage = (Stage) helpBox.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(ICON));
        helpBox.setTitle("HELP");
        helpBox.setHeaderText(null);
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
