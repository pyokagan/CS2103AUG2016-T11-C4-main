package seedu.address.ui;

import java.time.LocalDateTime;
import java.util.List;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import seedu.address.model.IndexedItem;
import seedu.address.model.ReadOnlyModel;
import seedu.address.model.filter.TaskUnfinishedPredicate;
import seedu.address.model.filter.TaskWillHappenTodayPredicate;
import seedu.address.model.task.EventTask;

public class EventTaskListPane extends UiPart<Pane> {

    private static final String FXML = "/view/EventTaskListPane.fxml";

    @FXML
    private ListView<IndexedItem<EventTask>> eventTaskListView;

    @FXML
    private Label listedEventCounter;

    @FXML
    private Label todayUnfinishedEventCounter;

    @FXML
    private Label unfinishedEventCounter;

    public EventTaskListPane(ReadOnlyModel model) {
        super(FXML);
        eventTaskListView.setItems(model.getEventTaskList());
        eventTaskListView.setCellFactory(listView -> new EventTaskListCell());
        listedEventCounter.textProperty().bind(Bindings.size(model.getEventTaskList())
                                               .asString("Number of Events listed: %d"));
        unfinishedEventCounter.textProperty().bind(Bindings.size(model.getEventTaskList(new TaskUnfinishedPredicate(LocalDateTime.now())))
                                                   .asString("Total upcoming: %d"));
        todayUnfinishedEventCounter.textProperty().bind(Bindings.size(model.getEventTaskList(new TaskWillHappenTodayPredicate(LocalDateTime.now())))
                                                        .asString("Today upcoming: %d"));
    }

    /**
     * Selects an event task as specified by its working index.
     */
    public void select(int workingIndex) {
        final List<IndexedItem<EventTask>> eventTaskList = eventTaskListView.getItems();
        for (int i = 0; i < eventTaskList.size(); i++) {
            if (eventTaskList.get(i).getWorkingIndex() == workingIndex) {
                eventTaskListView.scrollTo(i);
                eventTaskListView.getSelectionModel().select(i);
                return;
            }
        }
    }

    /**
     * Clears any event task selection.
     */
    public void clearSelect() {
        eventTaskListView.getSelectionModel().clearSelection();
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
