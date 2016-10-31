package seedu.address.logic;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import com.google.common.eventbus.Subscribe;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.model.TaskBookChangedEvent;
import seedu.address.logic.commands.CommandException;
import seedu.address.logic.parser.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyTaskBook;
import seedu.address.model.TaskBook;
import seedu.address.storage.StorageManager;

public class LogicManagerTest {

    /**
     * See https://github.com/junit-team/junit4/wiki/rules#temporaryfolder-rule
     */
    @Rule
    public TemporaryFolder saveFolder = new TemporaryFolder();

    private Model model;
    private Logic logic;

    //These are for checking the correctness of the events raised
    private ReadOnlyTaskBook latestSavedTaskBook;

    @Subscribe
    private void handleLocalModelChangedEvent(TaskBookChangedEvent abce) {
        latestSavedTaskBook = new TaskBook(abce.data);
    }

    @Before
    public void setup() {
        model = new ModelManager();
        String tempConfigFile = saveFolder.getRoot().getPath() + "tempConfig.json";
        String tempTaskBookFile = saveFolder.getRoot().getPath() + "TempTaskBook.json";
        logic = new LogicManager(model, new StorageManager(tempConfigFile, tempTaskBookFile));
        EventsCenter.getInstance().registerHandler(this);

        latestSavedTaskBook = new TaskBook(model.getTaskBook()); // last saved assumed to be up to date before.
    }

    @After
    public void teardown() {
        EventsCenter.clearSubscribers();
    }

    @Test
    public void execute_invalid() throws CommandException {
        try {
            logic.execute("    ");
            assert false : "expected ParseException";
        } catch (ParseException e) {
            assertEquals("No command name given", e.getMessage());
        }
    }

    @Test
    public void execute_unknownCommandWord() throws CommandException {
        try {
            logic.execute("uicfhmowqewca");
            assert false : "expected ParseException";
        } catch (ParseException e) {
            assertEquals("Unknown command: uicfhmowqewca", e.getMessage());
        }
    }

}
