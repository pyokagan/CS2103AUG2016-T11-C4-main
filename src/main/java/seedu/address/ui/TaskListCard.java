package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import seedu.address.model.task.ReadOnlyTask;

public class TaskListCard extends UiPart<HBox> {

    private static final String FXML = "/view/TaskListCard.fxml";

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label tags;

    public TaskListCard(ReadOnlyTask person, int displayedIndex) {
        super(FXML, null);
        name.setText(person.getName().fullName);
        id.setText(displayedIndex + ". ");
        tags.setText(person.tagsString());
    }
}
