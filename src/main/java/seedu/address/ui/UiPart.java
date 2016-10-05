package seedu.address.ui;

import java.io.IOException;
import java.net.URL;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import seedu.address.MainApp;
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.BaseEvent;
import seedu.address.commons.util.AppUtil;

/**
 * Base class for UI parts.
 * A 'UI part' represents a distinct part of the UI. e.g. Windows, dialogs, panels, status bars, etc.
 */
public class UiPart<T> {
    /**
     * The primary stage for the UI Part.
     */
    public final Stage primaryStage;

    private final FXMLLoader loader;

    public UiPart(URL url, Stage primaryStage) {
        assert url != null;
        this.primaryStage = primaryStage;
        loader = new FXMLLoader(url);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException("Unexpected exception occurred while loading " + url + ": " + e);
        }
        EventsCenter.getInstance().registerHandler(this);
    }

    public UiPart(String name, Stage primaryStage) {
        this(MainApp.class.getResource(name), primaryStage);
    }

    /**
     * Raises the event via {@link EventsCenter#post(BaseEvent)}
     * @param event
     */
    protected void raise(BaseEvent event) {
        EventsCenter.getInstance().post(event);
    }

    /**
     * Creates a modal dialog.
     * @param title Title of the dialog.
     * @param parentStage The owner stage of the dialog.
     * @param scene The scene that will contain the dialog.
     * @return the created dialog, not yet made visible.
     */
    protected Stage createDialogStage(String title, Stage parentStage, Scene scene) {
        Stage dialogStage = new Stage();
        dialogStage.setTitle(title);
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.initOwner(parentStage);
        dialogStage.setScene(scene);
        return dialogStage;
    }

    /**
     * Sets the given image as the icon for the primary stage of this UI Part.
     * @param iconSource e.g. {@code "/images/help_icon.png"}
     */
    protected void setIcon(String iconSource) {
        primaryStage.getIcons().add(AppUtil.getImage(iconSource));
    }

    /**
     * Sets the given image as the icon for the given stage.
     * @param stage
     * @param iconSource e.g. {@code "/images/help_icon.png"}
     */
    protected void setIcon(Stage stage, String iconSource) {
        stage.getIcons().add(AppUtil.getImage(iconSource));
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public T getRoot() {
        return loader.getRoot();
    }
}
