package seedu.address.ui;

import java.util.Optional;
import java.util.logging.Logger;

import com.melloware.jintellitype.JIntellitypeConstants;

import javafx.application.Platform;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;
import seedu.address.MainApp;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.AppUtil;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.Logic;
import seedu.address.model.config.Config;
import seedu.address.model.config.WindowRect;
import seedu.address.ui.TrayIcon.MessageType;

/**
 * The manager of the UI component.
 */
public class UiManager extends ComponentManager implements Ui {
    private static final Logger logger = LogsCenter.getLogger(UiManager.class);
    private static final String ICON_APPLICATION = "/images/address_book_32.png";
    private static final String[] FONTS = {
        "/view/fonts/opensans/OpenSans-Light.ttf",
        "/view/fonts/opensans/OpenSans-LightItalic.ttf",
        "/view/fonts/opensans/OpenSans-Bold.ttf",
        "/view/fonts/opensans/OpenSans-ExtraBold.ttf",
        "/view/fonts/opensans/OpenSans-SemiboldItalic.ttf",
        "/view/fonts/opensans/OpenSans-Italic.ttf",
        "/view/fonts/opensans/OpenSans-BoldItalic.ttf",
        "/view/fonts/opensans/OpenSans-ExtraBoldItalic.ttf",
        "/view/fonts/opensans/OpenSans-Regular.ttf",
        "/view/fonts/opensans/OpenSans-Semibold.ttf",
        "/view/fonts/RobotoMono/RobotoMono-LightItalic.ttf",
        "/view/fonts/RobotoMono/RobotoMono-Medium.ttf",
        "/view/fonts/RobotoMono/RobotoMono-Thin.ttf",
        "/view/fonts/RobotoMono/RobotoMono-ThinItalic.ttf",
        "/view/fonts/RobotoMono/RobotoMono-Regular.ttf",
        "/view/fonts/RobotoMono/RobotoMono-Italic.ttf",
        "/view/fonts/RobotoMono/RobotoMono-MediumItalic.ttf",
        "/view/fonts/RobotoMono/RobotoMono-BoldItalic.ttf",
        "/view/fonts/RobotoMono/RobotoMono-Bold.ttf",
        "/view/fonts/RobotoMono/RobotoMono-Light.ttf"
    };

    private Logic logic;
    private Config config;
    private MainWindow mainWindow;
    private Stage primaryStage;
    private TrayIcon trayIcon;
    private HotkeyListener hotkeyListener;
    private Optional<WindowRect> savedWindowRect = Optional.empty();
    private boolean stillRunningMessageShown;

    public UiManager(Logic logic, Config config) {
        super();
        this.logic = logic;
        this.config = config;
    }

    @Override
    public void start(Stage primaryStage) {
        logger.info("Starting UI...");
        this.primaryStage = primaryStage;
        loadFonts();
        primaryStage.getIcons().add(getImage(ICON_APPLICATION));
        trayIcon = new TrayIcon(AppUtil.getImage(ICON_APPLICATION), MainApp.NAME);
        trayIcon.setTrayIconAction(this::toggleHide);
        hotkeyListener = new HotkeyListener(JIntellitypeConstants.MOD_CONTROL, (int)' ');
        hotkeyListener.setAction(this::toggleHide);

        try {
            mainWindow = new MainWindow(primaryStage, config, logic);
            primaryStage.setScene(mainWindow.getRoot());
            primaryStage.show();
        } catch (Throwable e) {
            logger.severe(StringUtil.getDetails(e));
            showFatalErrorDialogAndShutdown("Fatal error during initializing", e);
        }
    }

    @Override
    public void stop() {
        if (hotkeyListener != null) {
            hotkeyListener.destroy();
            hotkeyListener = null;
        }
        if (trayIcon != null) {
            trayIcon.destroy();
            trayIcon = null;
        }
        primaryStage = null;
    }

    private Image getImage(String imagePath) {
        return new Image(MainApp.class.getResourceAsStream(imagePath));
    }

    void showAlertDialogAndWait(Alert.AlertType type, String title, String headerText, String contentText) {
        showAlertDialogAndWait(primaryStage, type, title, headerText, contentText);
    }

    private static void showAlertDialogAndWait(Stage owner, AlertType type, String title, String headerText,
                                               String contentText) {
        final Alert alert = new Alert(type);
        alert.getDialogPane().getStylesheets().add("view/DarkTheme.css");
        if (owner != null && owner.getScene() != null) {
            alert.initOwner(owner);
        }
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);

        alert.showAndWait();
    }

    private void showFatalErrorDialogAndShutdown(String title, Throwable e) {
        logger.severe(title + " " + e.getMessage() + StringUtil.getDetails(e));
        showAlertDialogAndWait(Alert.AlertType.ERROR, title, e.getMessage(), e.toString());
        Platform.exit();
        System.exit(1);
    }

    /**
     * Hide the UI, running it in the background.
     */
    public void hide() {
        Platform.setImplicitExit(false);
        savedWindowRect = primaryStage.isMaximized() ? Optional.empty() : Optional.of(getWindowRect());
        primaryStage.hide();
        if (!stillRunningMessageShown) {
            trayIcon.displayMessage("Task Tracker is still running!", "Double-click here to re-open it!",
                                    MessageType.INFO);
            stillRunningMessageShown = true;
        }
    }

    /**
     * Show the UI, bringing it to the foreground.
     */
    public void show() {
        primaryStage.show();
        primaryStage.toFront();
        primaryStage.requestFocus();
        Platform.setImplicitExit(true);
        if (savedWindowRect.isPresent()) {
            setWindowRect(savedWindowRect.get());
        }
    }

    /**
     * Toggle the hiding/showing of the UI.
     */
    public void toggleHide() {
        if (primaryStage.isShowing()) {
            hide();
        } else {
            show();
        }
    }

    /**
     * Returns the dimensions of the primary window of the UI.
     */
    public WindowRect getWindowRect() {
        return new WindowRect(primaryStage.getWidth(), primaryStage.getHeight(),
                              primaryStage.getX(), primaryStage.getY());
    }

    /**
     * Sets the dimensions of the primary window of the UI.
     */
    public void setWindowRect(WindowRect rect) {
        assert rect != null;
        primaryStage.setWidth(rect.getWidth());
        primaryStage.setHeight(rect.getHeight());
        final Rectangle2D bounds = Screen.getPrimary().getVisualBounds();
        primaryStage.setX(rect.getX().orElse((bounds.getWidth() - primaryStage.getWidth()) / 2));
        primaryStage.setY(rect.getY().orElse((bounds.getHeight() - primaryStage.getHeight()) / 2));
    }

    /**
     * Loads fonts contained within our /view/
     */
    private void loadFonts() {
        for (String font : FONTS) {
            Font.loadFont(MainApp.class.getResource(font).toExternalForm(), 10);
        }
    }

}
