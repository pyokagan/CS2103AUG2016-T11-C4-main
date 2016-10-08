package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import seedu.address.model.task.Task;

public class TaskListCard extends UiPart<HBox> {

    private static final String FXML = "/view/TaskListCard.fxml";

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;

    public TaskListCard(Task person, int displayedIndex) {
        super(FXML, null);
        name.setText(person.name.toString());
        id.setText(displayedIndex + ". ");
    }
}
