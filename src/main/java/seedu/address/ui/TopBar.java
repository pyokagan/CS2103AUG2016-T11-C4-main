package seedu.address.ui;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Labeled;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import seedu.address.model.filter.TaskPredicate;

/**
 * The application header bar which is usually displayed at the top of the user interface.
 */
public class TopBar extends UiPart<Region> {
    private static final String FXML = "/view/TopBar.fxml";
    private static final String MSG_FILTER = "Filtering by: %s";

    @FXML
    private Pane statusBox;

    @FXML
    private Labeled filterLabel;

    private final SimpleObjectProperty<TaskPredicate> predicate = new SimpleObjectProperty<>();

    public TopBar() {
        super(FXML);
        predicate.addListener((observable, oldValue, newValue) -> {
            if (oldValue != null && newValue == null) {
                statusBox.getStyleClass().removeAll("with-filter");
            } else if (oldValue == null && newValue != null) {
                statusBox.getStyleClass().add("with-filter");
            }
            filterLabel.setText(String.format(MSG_FILTER, newValue != null ? newValue.toHumanReadableString() : ""));
        });
    }

    public TopBar(ReadOnlyProperty<TaskPredicate> predicate) {
        this();
        this.predicate.bind(predicate);
    }

    public ObjectProperty<TaskPredicate> predicateProperty() {
        return predicate;
    }

}
