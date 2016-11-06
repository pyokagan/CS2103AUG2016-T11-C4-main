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
import seedu.address.model.filter.TaskOverduePredicate;
import seedu.address.model.filter.TaskUnfinishedPredicate;
import seedu.address.model.task.DeadlineTask;

public class DeadlineTaskListPane extends UiPart<Pane> {

    private static final String FXML = "/view/DeadlineTaskListPane.fxml";

    @FXML
    private ListView<IndexedItem<DeadlineTask>> deadlineTaskListView;

    @FXML
    private Label listedDeadlineCounter;

    @FXML
    private Label unfinishedDeadlineCounter;

    @FXML
    private Label overdueDeadlineCounter;

    public DeadlineTaskListPane(ReadOnlyModel model) {
        super(FXML);
        deadlineTaskListView.setItems(model.getDeadlineTaskList());
        deadlineTaskListView.setCellFactory(listView -> new DeadlineTaskListCell());
        listedDeadlineCounter.textProperty().bind(Bindings.size(model.getDeadlineTaskList())
                                                    .asString("Number of Deadlines listed: %d"));
        unfinishedDeadlineCounter.textProperty().bind(Bindings.size(model.getDeadlineTaskList(new TaskUnfinishedPredicate(LocalDateTime.now())))
                                                        .asString("Unfinished: %d"));
        overdueDeadlineCounter.textProperty().bind(Bindings.size(model.getDeadlineTaskList(new TaskOverduePredicate(LocalDateTime.now())))
                                                        .asString("Overdue: %d"));
    }

    /**
     * Selects a deadline task as specified by its working index.
     */
    public void select(int workingIndex) {
        final List<IndexedItem<DeadlineTask>> deadlineTaskList = deadlineTaskListView.getItems();
        for (int i = 0; i < deadlineTaskList.size(); i++) {
            if (deadlineTaskList.get(i).getWorkingIndex() == workingIndex) {
                deadlineTaskListView.scrollTo(i);
                deadlineTaskListView.getSelectionModel().select(i);
                return;
            }
        }
    }

    /**
     * Clears any deadline task selection.
     */
    public void clearSelect() {
        deadlineTaskListView.getSelectionModel().clearSelection();
    }

    private static class DeadlineTaskListCell extends ListCell<IndexedItem<DeadlineTask>> {
        @Override
        protected void updateItem(IndexedItem<DeadlineTask> deadlineTask, boolean empty) {
            super.updateItem(deadlineTask, empty);
            final DeadlineTaskListCard card = new DeadlineTaskListCard(deadlineTask != null ? deadlineTask.getItem() : null, deadlineTask != null ? deadlineTask.getWorkingIndex() : 0);
            setGraphic(card.getRoot());
        }
    }

}
