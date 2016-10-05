package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import seedu.address.commons.util.FxViewUtil;
import seedu.address.model.task.ReadOnlyPerson;

/**
 * The Browser Panel of the App.
 */
public class BrowserPanel extends UiPart<WebView> {

    private static String FXML = "/view/BrowserPanel.fxml";

    @FXML
    private WebView browser;

    public BrowserPanel(Stage primaryStage) {
        super(FXML, primaryStage);
        FxViewUtil.applyAnchorBoundaryParameters(browser, 0.0, 0.0, 0.0, 0.0);
    }

    public void loadPersonPage(ReadOnlyPerson person) {
        loadPage("https://www.google.com.sg/#safe=off&q=" + person.getName().fullName.replaceAll(" ", "+"));
    }

    public void loadPage(String url) {
        browser.getEngine().load(url);
    }

    /**
     * Frees resources allocated to the browser.
     */
    public void freeResources() {
        browser = null;
    }

}
