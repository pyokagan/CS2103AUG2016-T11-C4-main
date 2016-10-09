package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import seedu.address.model.task.EventTask;

public class EventTaskListCard extends UiPart<Pane> {

    private static final String FXML = "/view/EventTaskListCard.fxml";

    @FXML
    private Label indexLabel;

    @FXML
    private Label nameLabel;

    @FXML
    private Label startLabel;

    @FXML
    private Label endLabel;

    /**
     * @param eventTask The event task to display. Can be null to not display anything.
     */
    public EventTaskListCard(EventTask eventTask, int index) {
        super(FXML);
        if (eventTask != null) {
            indexLabel.setText(index + ". ");
            nameLabel.setText(eventTask.name.toString());
            startLabel.setText(eventTask.getStart().toString());
            endLabel.setText(eventTask.getEnd().toString());
        } else {
            getRoot().setVisible(false);
        }
    }

}
