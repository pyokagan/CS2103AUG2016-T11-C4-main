package seedu.address.ui;

import java.awt.event.KeyEvent;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
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

        Alert helpBox = new Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
        helpBox.setTitle("Information Dialog");
        helpBox.setHeaderText(null);
        helpBox.setContentText("I have a great message for you!");
        helpBox.showAndWait();
    }
}
