package seedu.address.ui;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import org.fxmisc.richtext.InlineCssTextArea;
import org.fxmisc.richtext.PopupAlignment;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Popup;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.CommandException;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.parser.ParseException;

public class CommandBox extends UiPart<Pane> {
    private final Logger logger = LogsCenter.getLogger(CommandBox.class);

    private static final String FXML = "/view/CommandBox.fxml";

    private final ResultDisplay resultDisplay;

    private final Logic logic;

    @FXML
    private InlineCssTextArea commandTextField;

    private OnCommandResultCallback onCommandResultCallback;

    private final Popup autocompletePopup;
    private final ListView<String> autocompleteListView;

    public CommandBox(ResultDisplay resultDisplay, Logic logic) {
        super(FXML);
        this.resultDisplay = resultDisplay;
        this.logic = logic;
        autocompleteListView = new ListView<>();
        autocompleteListView.setOnKeyPressed(this::handleListViewKeyPressed);
        autocompletePopup = new Popup();
        autocompletePopup.getContent().add(autocompleteListView);
        commandTextField.setPopupWindow(autocompletePopup);
        commandTextField.setPopupAlignment(PopupAlignment.CARET_CENTER);
        commandTextField.setPopupAnchorOffset(new Point2D(4, 0));
    }

    public void setOnCommandResult(OnCommandResultCallback callback) {
        this.onCommandResultCallback = callback;
    }

    /**
     * Focus on the command box text field.
     */
    public void requestFocus() {
        commandTextField.requestFocus();
    }

    @FXML
    private void handleKeyPressed(KeyEvent ke) {
        if (ke.getCode() == KeyCode.TAB) {
            ke.consume();
            handleAutocomplete();
        } else if (ke.getCode() == KeyCode.ENTER) {
            ke.consume();
            handleCommandInputChanged();
        } else {
            commandTextField.getStyleClass().removeAll("error");
            autocompletePopup.hide();
        }
    }

    private void handleListViewKeyPressed(KeyEvent ke) {
        if (ke.getCode() == KeyCode.TAB) {
            ke.consume();
            if (autocompleteListView.getSelectionModel().getSelectedIndex() == autocompleteListView.getItems().size() - 1) {
                autocompleteListView.getSelectionModel().selectFirst();
            } else {
                autocompleteListView.getSelectionModel().selectNext();
            }
        } else if (ke.getCode() == KeyCode.ENTER) {
            ke.consume();
            commandTextField.insertText(commandTextField.getCaretPosition(), autocompleteListView.getSelectionModel().getSelectedItem());
            autocompletePopup.hide();
        }
    }

    private void handleAutocomplete() {
        final List<String> candidates = logic.autocomplete(commandTextField.getText(),
                                                           commandTextField.getCaretPosition());
        if (candidates.size() == 1) {
            commandTextField.insertText(commandTextField.getCaretPosition(), candidates.get(0));
        } else if (candidates.size() > 1) {
            autocompleteListView.setItems(FXCollections.observableList(candidates));
            autocompleteListView.getSelectionModel().selectFirst();
            autocompletePopup.show(commandTextField.getScene().getWindow());
        }
    }

    private void handleCommandInputChanged() {
        try {
            final CommandResult result = logic.execute(commandTextField.getText());
            logger.info("Result: " + result.feedbackToUser);
            resultDisplay.postMessage(result.feedbackToUser);
            setStyleToIndicateCorrectCommand();
            if (onCommandResultCallback != null) {
                onCommandResultCallback.call(result);
            }
        } catch (ParseException | CommandException | IOException e) {
            logger.info("Result: " + e.toString());
            setStyleToIndicateIncorrectCommand();
            resultDisplay.postMessage(e.getMessage());
        }
    }

    /**
     * Sets the command box style to indicate a correct command.
     */
    private void setStyleToIndicateCorrectCommand() {
        commandTextField.getStyleClass().removeAll("error");
        commandTextField.replaceText("");
    }

    /**
     * Sets the command box style to indicate an error
     */
    private void setStyleToIndicateIncorrectCommand() {
        commandTextField.getStyleClass().add("error");
    }

    public interface OnCommandResultCallback {
        void call(CommandResult result);
    }

}
