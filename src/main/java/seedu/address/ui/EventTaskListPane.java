package seedu.address.ui;

import java.util.Optional;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import seedu.address.model.task.EventTask;

public class EventTaskListPane extends UiPart<Pane> {

    private static final String FXML = "/view/EventTaskListPane.fxml";

    @FXML
    private ListView<Optional<EventTask>> eventTaskListView;

    public EventTaskListPane(ObservableList<Optional<EventTask>> eventTaskList) {
        super(FXML);
        eventTaskListView.setItems(eventTaskList);
        eventTaskListView.setCellFactory(listView -> new EventTaskListCell());
    }

    private static class EventTaskListCell extends ListCell<Optional<EventTask>> {
        @Override
        protected void updateItem(Optional<EventTask> eventTask, boolean empty) {
            super.updateItem(eventTask, empty);
            final EventTaskListCard card = new EventTaskListCard(empty ? null : eventTask.orElse(null), getIndex() + 1);
            setGraphic(card.getRoot());
        }
    }

}
