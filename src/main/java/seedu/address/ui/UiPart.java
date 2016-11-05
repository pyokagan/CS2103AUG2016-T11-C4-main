package seedu.address.ui;

import java.io.IOException;
import java.net.URL;

import javafx.fxml.FXMLLoader;
import seedu.address.MainApp;

/**
 * Base class for UI parts.
 * A 'UI part' represents a distinct part of the UI. e.g. Windows, dialogs, panels, status bars, etc.
 */
public class UiPart<T> {

    private final FXMLLoader loader;

    public UiPart(URL url) {
        assert url != null;
        loader = new FXMLLoader(url);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException("Unexpected exception occurred while loading " + url + ": " + e);
        }
    }

    public UiPart(String name) {
        this(MainApp.class.getResource(name));
    }

    public T getRoot() {
        return loader.getRoot();
    }
}
