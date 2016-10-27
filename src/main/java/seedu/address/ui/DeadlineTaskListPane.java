package seedu.address.ui;

import java.util.Optional;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import seedu.address.model.task.DeadlineTask;

public class DeadlineTaskListPane extends UiPart<Pane> {

    private static final String FXML = "/view/DeadlineTaskListPane.fxml";

    @FXML
    private ListView<Optional<DeadlineTask>> deadlineTaskListView;

    public DeadlineTaskListPane(ObservableList<Optional<DeadlineTask>> deadlineTaskList) {
        super(FXML);
        deadlineTaskListView.setItems(deadlineTaskList);
        deadlineTaskListView.setCellFactory(listView -> new DeadlineTaskListCell());
    }

    private static class DeadlineTaskListCell extends ListCell<Optional<DeadlineTask>> {
        @Override
        protected void updateItem(Optional<DeadlineTask> deadlineTask, boolean empty) {
            super.updateItem(deadlineTask, empty);
            final DeadlineTaskListCard card = new DeadlineTaskListCard(empty ? null : deadlineTask.orElse(null), getIndex() + 1);
            setGraphic(card.getRoot());
        }
    }

}
