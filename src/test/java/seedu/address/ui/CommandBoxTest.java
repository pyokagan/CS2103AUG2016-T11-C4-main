package seedu.address.ui;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.loadui.testfx.GuiTest;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.IncorrectCommandAttemptedEvent;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.CommandException;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.parser.ParseException;
import seedu.address.testutil.GuiTests;

/**
 * Unit Tests for CommandBox.
 */
@Category({GuiTests.class})
public class CommandBoxTest extends GuiTest {

    @Mock
    private Logic logic;

    @Mock
    private ResultDisplay resultDisplay;

    private CommandBox commandBox;

    private TextField textField;

    @Override
    protected Parent getRootNode() {
        MockitoAnnotations.initMocks(this);
        commandBox = new CommandBox(resultDisplay, logic);
        return commandBox.getRoot();
    }

    @Before
    public void setupNodes() {
        textField = find("#commandTextField");
    }

    @Test
    public void commandInputChanged_callsLogicExecute() throws ParseException, CommandException {
        final String inputCommand = "some command";
        final CommandResult result = new CommandResult("some result");
        Mockito.when(logic.execute(inputCommand)).thenReturn(result);

        textField.setText(inputCommand);
        clickOn(textField).push(KeyCode.ENTER);

        Mockito.verify(logic).execute(inputCommand);
        Mockito.verify(resultDisplay).postMessage(result.feedbackToUser);
        assertEquals("", textField.getText());
    }

    @Test
    public void incorrectCommand_restoresCommandText() throws ParseException, CommandException {
        final String inputCommand = "some command";
        final CommandResult result = new CommandResult("some result");
        Mockito.when(logic.execute(inputCommand)).thenReturn(result);

        textField.setText(inputCommand);
        clickOn(textField).push(KeyCode.ENTER);
        assertEquals("", textField.getText());

        EventsCenter.getInstance().post(new IncorrectCommandAttemptedEvent(null));
        assertEquals(inputCommand, textField.getText());
    }

}
