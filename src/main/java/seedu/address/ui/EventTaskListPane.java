package seedu.address.ui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import seedu.address.model.IndexedItem;
import seedu.address.model.task.EventTask;

public class EventTaskListPane extends UiPart<Pane> {

    private static final String FXML = "/view/EventTaskListPane.fxml";

    @FXML
    private ListView<IndexedItem<EventTask>> eventTaskListView;

    public EventTaskListPane(ObservableList<IndexedItem<EventTask>> eventTaskList) {
        super(FXML);
        eventTaskListView.setItems(eventTaskList);
        eventTaskListView.setCellFactory(listView -> new EventTaskListCell());
    }

    private static class EventTaskListCell extends ListCell<IndexedItem<EventTask>> {
        @Override
        protected void updateItem(IndexedItem<EventTask> eventTask, boolean empty) {
            super.updateItem(eventTask, empty);
            final EventTaskListCard card = new EventTaskListCard(eventTask != null ? eventTask.getItem() : null, eventTask != null ? eventTask.getWorkingIndex() : 0);
            setGraphic(card.getRoot());
        }
    }

}
