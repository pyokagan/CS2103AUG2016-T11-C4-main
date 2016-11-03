package seedu.address.ui;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;


/**
 * Controller for a help page
 */
public class HelpWindow {

    private static final String USERGUIDE_URL = "https://github.com/CS2103AUG2016-T11-C4/main/blob/master/docs/UserGuide.md";
    private static final String HELPBOX_CONTENT =
              "Add Floating Task             | add \"FLOATING_TASK_NAME\"  [p-PRIORITY]\n"
            + "Add Deadline Task             | add \"DEADLINE_NAME\" <DATE> <TIME>\n"
            + "Add Events                    | add \"EVENT_NAME\" <STARTING_DATE> <STARTING_TIME> to <ENDING_DATE> <ENDING_TIME>\n"
            + "Delete                          | del <INDEX>\n"
            + "Edit Floating Tasks           | edit <INDEX> [n-NEW_NAME] [p-PRIORITY]\n"
            + "Edit Deadline                | edit <INDEX> [dd-DUE_DATE] [dt-DUE_TIME] [n-NEW_NAME]\n"
            + "Mark task as finished      | fin <INDEX>\n"
            + "Mark a task as unfinished | unfin <INDEX>\n"
            + "Search for keywords         | search KEYWORD\n"
            + "Undo                             | undo\n"
            + "Redo                              | redo\n"
            + "Clear                              | clear\n"
            + "Exit                                 | exit\n"
            + "Hide and show Task Tracker  | Ctrl + SPACE\n\n"
            + "For full guide: " + USERGUIDE_URL;

    private static final String ICON = "/images/help_icon.png";
    private static final String FXML = "/view/HelpWindow.fxml";
    private static final String TITLE = "Help";

    @FXML
    private Alert helpBox;

    public HelpWindow() {

        this.helpBox = new Alert(javafx.scene.control.Alert.AlertType.NONE);
        helpBox.initModality(Modality.NONE);

        //add icon
        Stage stage = (Stage) helpBox.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(ICON));

        //window settings
        stage.setAlwaysOnTop(true);
        helpBox.initModality(Modality.NONE);
        helpBox.setResizable(true);
        helpBox.getDialogPane().setPrefWidth(700.0);
        helpBox.getDialogPane().setPrefHeight(340.0);

        //set contents
        helpBox.setTitle("HELP");
        helpBox.setHeaderText("Press <ESC> to close");
        helpBox.setContentText(HELPBOX_CONTENT);

        //press escape to close
        stage.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode().equals(KeyCode.ESCAPE)) {
                    stage.close();
                    keyEvent.consume();
                }
            }
        });

        helpBox.showAndWait();
    }

    public Stage getStage() {
        return (Stage) helpBox.getDialogPane().getScene().getWindow();
    }

}
