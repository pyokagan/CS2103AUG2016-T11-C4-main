package seedu.address.logic;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandException;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.parser.ParseException;
import seedu.address.logic.parser.Parser;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.task.TypicalFloatingTasks;
import seedu.address.storage.Storage;

public class LogicManagerTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder saveFolder = new TemporaryFolder();

    private TypicalFloatingTasks tpflt = new TypicalFloatingTasks();

    @Spy
    private Model model = new ModelManager();

    @Mock
    private Storage storage;

    @Mock
    private Parser<Command> parser;

    private Logic logic;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        logic = new LogicManager(model, storage, parser);
    }

    @Test
    public void execute_commandThatReturnsCommandResult_returnsCommandResult() throws Exception {
        final CommandResult expected = new CommandResult("myResult");
        final CommandResult actual = logic.execute(model -> expected);
        assertEquals(expected, actual);
    }

    @Test
    public void execute_commandThrowsCommandException_throwsCommandException() throws Exception {
        thrown.expect(CommandException.class);
        thrown.expectMessage("some exception");
        logic.execute(model -> {
            throw new CommandException("some exception");
        });
    }

    @Test
    public void execute_parserReturnsCommand_executesThatReturnedCommand() throws Exception {
        final CommandResult someResult = new CommandResult("some result");
        final Command someCommand = model -> someResult;
        Mockito.when(parser.parse("some command")).thenReturn(someCommand);
        final CommandResult actual = logic.execute("some command");
        assertEquals(someResult, actual);
    }

    @Test
    public void execute_parserThrowsParseException_throwsParseException() throws Exception {
        Mockito.when(parser.parse("my command")).thenThrow(new ParseException("some parse exception"));
        thrown.expect(ParseException.class);
        thrown.expectMessage("some parse exception");
        logic.execute("my command");
    }

    @Test
    public void execute_withCommandThatModifiesTaskBook_savesTaskBook() throws Exception {
        logic.execute(model -> {
            model.addFloatingTask(tpflt.buyAHelicopter);
            return new CommandResult("task book modified");
        });
        Mockito.verify(storage).saveTaskBook(model.getTaskBook());
    }

    @Test
    public void execute_withCommandThatModifiesTaskBook_recordsState() throws Exception {
        final Command command = model -> {
            model.addFloatingTask(tpflt.readABook);
            return new CommandResult("task book modified");
        };
        logic.execute(command);
        Mockito.verify(model).recordState(command.toString());
    }

    @Test
    public void execute_withCommandThatModifiesConfig_savesConfig() throws Exception {
        logic.execute(model -> {
            model.setTaskBookFilePath("a");
            return new CommandResult("modified config");
        });
        Mockito.verify(storage).moveTaskBook("a");
    }

    @Test
    public void execute_withCommandThatModifiesConfig_recordsState() throws Exception {
        final Command command = model -> {
            model.setTaskBookFilePath("a");
            return new CommandResult("modified config");
        };
        logic.execute(command);
        Mockito.verify(model).recordState(command.toString());
    }

    @Test
    public void execute_withCommandThatModifiesTaskBookPath_movesTaskBook() throws Exception {
        logic.execute(model -> {
            model.setTaskBookFilePath("a");
            return new CommandResult("modified task book path");
        });
        Mockito.verify(storage).moveTaskBook("a");
    }

}
