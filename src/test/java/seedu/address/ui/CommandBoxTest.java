package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.fxmisc.richtext.InlineCssTextArea;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;

import javafx.scene.control.ListView;
import javafx.scene.input.KeyCode;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.CommandException;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.parser.ParseException;
import seedu.address.testutil.GuiTests;

/**
 * Unit Tests for CommandBox.
 */
@Category({GuiTests.class})
public class CommandBoxTest extends FxRobot {

    @Mock
    private Logic logic;

    @Mock
    private ResultDisplay resultDisplay;

    private CommandBox commandBox;

    private InlineCssTextArea textField;

    @BeforeClass
    public static void setupFxToolkit() throws Exception {
        FxToolkit.registerPrimaryStage();
    }

    @Before
    public void setup() throws Exception {
        MockitoAnnotations.initMocks(this);
        FxToolkit.setupSceneRoot(() -> {
            commandBox = new CommandBox(resultDisplay, logic);
            return commandBox.getRoot();
        });
        FxToolkit.showStage();
        textField = lookup("#commandTextField").query();
    }

    @After
    public void teardown() throws Exception {
        FxToolkit.cleanupStages();
    }

    @Test
    public void pressEnter_callsLogicExecute() throws ParseException, CommandException, IOException {
        final String inputCommand = "some command";
        final CommandResult result = new CommandResult("some result");
        Mockito.when(logic.execute(inputCommand)).thenReturn(result);

        interact(() -> textField.replaceText(inputCommand));
        clickOn(textField).push(KeyCode.ENTER);

        Mockito.verify(logic).execute(inputCommand);
        Mockito.verify(resultDisplay).postMessage(result.feedbackToUser);
        assertEquals("", textField.getText());
    }

    @Test
    public void pressEnter_withIncorrectCommand_keepsCommandText() throws ParseException, CommandException, IOException {
        final String inputCommand = "some command";
        Mockito.when(logic.execute(inputCommand)).thenThrow(new CommandException("some exception"));

        interact(() -> textField.replaceText(inputCommand));
        clickOn(textField).push(KeyCode.ENTER);
        assertEquals(inputCommand, textField.getText());
        assertTrue(textField.getStyleClass().contains("error"));

        // Pressing any key will clear the error style
        clickOn(textField).push(KeyCode.RIGHT);
        assertFalse(textField.getStyleClass().contains("error"));
    }

    @Test
    public void pressTab_withMultipleAutocomplete_showsAutocompleteBox()
            throws ParseException, CommandException, IOException {
        final List<String> autocomplete = Arrays.asList(" wonderful", " fantastic");
        final String inputCommand = "some command";
        Mockito.when(logic.autocomplete("some command", 4)).thenReturn(autocomplete);
        interact(() -> textField.replaceText(inputCommand));
        interact(() -> textField.selectRange(4, 4));
        push(KeyCode.TAB);
        assertTrue(lookup(".list-view").tryQuery().isPresent());
        final ListView<String> listView = lookup(".list-view").query();
        assertTrue(listView.getScene().getWindow().isShowing());
        assertEquals(autocomplete, listView.getItems());
        assertEquals(0, listView.getSelectionModel().getSelectedIndex());

        // Pressing tab when the listview is focussed will scroll the focus
        push(KeyCode.TAB);
        assertEquals(1, listView.getSelectionModel().getSelectedIndex());

        // And when we reach the end we wrap back to the top
        push(KeyCode.TAB);
        assertEquals(0, listView.getSelectionModel().getSelectedIndex());

        // Pressing enter will insert the selected text
        push(KeyCode.ENTER);
        assertEquals("some wonderful command", textField.getText());
        assertEquals(14, textField.getCaretPosition());
        assertFalse(listView.getScene().getWindow().isShowing());
    }

    @Test
    public void pressTab_withOneAutocomplete_insertsAutocompleteDirectly()
            throws ParseException, CommandException, IOException {
        final List<String> autocomplete = Arrays.asList(" wonderful");
        final String inputCommand = "some command";
        Mockito.when(logic.autocomplete("some command", 4)).thenReturn(autocomplete);
        interact(() -> textField.replaceText(inputCommand));
        interact(() -> textField.selectRange(4, 4));
        push(KeyCode.TAB);
        assertEquals("some wonderful command", textField.getText());
        assertEquals(14, textField.getCaretPosition());
    }

}
