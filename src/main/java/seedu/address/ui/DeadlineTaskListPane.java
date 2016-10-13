package seedu.address.ui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import seedu.address.model.task.DeadlineTask;

public class DeadlineTaskListPane extends UiPart<Pane> {

    private static final String FXML = "/view/DeadlineTaskListPane.fxml";

    @FXML
    private ListView<DeadlineTask> deadlineTaskListView;

    public DeadlineTaskListPane(ObservableList<DeadlineTask> deadlineTaskList) {
        super(FXML);
        deadlineTaskListView.setItems(deadlineTaskList);
        deadlineTaskListView.setCellFactory(listView -> new DeadlineTaskListCell());
    }

    private static class DeadlineTaskListCell extends ListCell<DeadlineTask> {
        @Override
        protected void updateItem(DeadlineTask deadlineTask, boolean empty) {
            super.updateItem(deadlineTask, empty);
            final DeadlineTaskListCard card = new DeadlineTaskListCard(deadlineTask, getIndex() + 1);
            setGraphic(card.getRoot());
        }
    }

}
