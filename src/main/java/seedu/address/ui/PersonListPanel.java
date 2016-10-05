package seedu.address.ui;

import java.util.logging.Logger;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.model.task.ReadOnlyPerson;

/**
 * Panel containing the list of persons.
 */
public class PersonListPanel extends UiPart<VBox> {

    private final Logger logger = LogsCenter.getLogger(PersonListPanel.class);

    private static final String FXML = "/view/PersonListPanel.fxml";

    @FXML
    private ListView<ReadOnlyPerson> personListView;

    public PersonListPanel(Stage primaryStage, ObservableList<ReadOnlyPerson> personList) {
        super(FXML, primaryStage);
        personListView.setItems(personList);
        personListView.setCellFactory(listView -> new PersonListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    private void setEventHandlerForSelectionChangeEvent() {
        personListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                logger.fine("Selection in person list panel changed to : '" + newValue + "'");
                raise(new PersonPanelSelectionChangedEvent(newValue));
            }
        });
    }

    public void scrollTo(int index) {
        Platform.runLater(() -> {
            personListView.scrollTo(index);
            personListView.getSelectionModel().clearAndSelect(index);
        });
    }

    class PersonListViewCell extends ListCell<ReadOnlyPerson> {

        PersonListViewCell() {
        }

        @Override
        protected void updateItem(ReadOnlyPerson person, boolean empty) {
            super.updateItem(person, empty);

            if (empty || person == null) {
                setGraphic(null);
                setText(null);
            } else {
                final PersonCard personCard = new PersonCard(person, getIndex() + 1);
                setGraphic(personCard.getRoot());
            }
        }
    }

}
