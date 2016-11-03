package seedu.address.ui;

import javafx.fxml.FXML;
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
    }

}
