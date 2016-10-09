package seedu.address.ui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import seedu.address.model.task.EventTask;

public class EventTaskListPane extends UiPart<Pane> {

    private static final String FXML = "/view/EventTaskListPane.fxml";

    @FXML
    private ListView<EventTask> eventTaskListView;

    public EventTaskListPane(ObservableList<EventTask> eventTaskList) {
        super(FXML);
        eventTaskListView.setItems(eventTaskList);
    }

}
