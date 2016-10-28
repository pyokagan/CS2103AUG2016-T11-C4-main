package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.model.Model.Commit;
import seedu.address.model.ModelManager.HeadAtBoundaryException;
import seedu.address.model.task.TypicalDeadlineTasks;
import seedu.address.model.task.TypicalEventTasks;
import seedu.address.model.task.TypicalFloatingTasks;

public class ModelTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private TypicalFloatingTasks tpflt = new TypicalFloatingTasks();

    private TypicalEventTasks tpent = new TypicalEventTasks();

    private TypicalDeadlineTasks tpdue = new TypicalDeadlineTasks();

    private Model model;

    @Before
    public void setupModel() {
        model = new ModelManager();
    }

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), model.getFilteredFloatingTaskList());
        assertEquals(Collections.emptyList(), model.getFilteredEventTaskList());
        assertEquals(Collections.emptyList(), model.getFilteredDeadlineTaskList());
    }

    @Test
    public void addFloatingTask_appendsFloatingTask() throws Exception {
        model.addFloatingTask(tpflt.readABook);
        assertEquals(tpflt.readABook, model.getFloatingTask(0));
        model.addFloatingTask(tpflt.buyAHelicopter);
        assertEquals(tpflt.readABook, model.getFloatingTask(0));
        assertEquals(tpflt.buyAHelicopter, model.getFloatingTask(1));
        assertEquals(tpflt.readABook, model.getFloatingTask(0));
    }

    @Test
    public void getFloatingTask_invalidIndex_throwsException() throws Exception {
        thrown.expect(IllegalValueException.class);
        model.getFloatingTask(0);
    }

    @Test
    public void removeFloatingTask_emptiesIndexInFilteredList() throws Exception {
        model.addFloatingTask(tpflt.readABook);
        model.addFloatingTask(tpflt.buyAHelicopter);
        model.setFloatingTaskFilter(floatingTask -> floatingTask.equals(tpflt.buyAHelicopter));
        model.removeFloatingTask(0);
        model.setFloatingTaskFilter(null);
        assertEquals(Arrays.asList(Optional.of(tpflt.readABook)), model.getFilteredFloatingTaskList());
    }

    @Test
    public void removeFloatingTask_invalidIndex_throwsException() throws Exception {
        thrown.expect(IllegalValueException.class);
        model.removeFloatingTask(0);
    }

    @Test
    public void setFloatingTask_replacesIndexInFilteredList() throws Exception {
        model.addFloatingTask(tpflt.readABook);
        model.addFloatingTask(tpflt.buyAHelicopter);
        model.setFloatingTaskFilter(floatingTask -> floatingTask.equals(tpflt.buyAHelicopter));
        model.setFloatingTask(0, tpflt.readABook);
        model.setFloatingTaskFilter(null);
        assertEquals(Arrays.asList(Optional.of(tpflt.readABook), Optional.of(tpflt.readABook)),
                    model.getFilteredFloatingTaskList());
    }

    @Test
    public void setFloatingTask_invalidIndex_throwsException() throws Exception {
        thrown.expect(IllegalValueException.class);
        model.setFloatingTask(0, tpflt.readABook);
    }

    @Test
    public void addEventTask_appendsEventTask() throws Exception {
        model.addEventTask(tpent.lunchWithBillGates);
        assertEquals(tpent.lunchWithBillGates, model.getEventTask(0));
        model.addEventTask(tpent.launchNuclearWeapons);
        assertEquals(tpent.lunchWithBillGates, model.getEventTask(0));
        assertEquals(tpent.launchNuclearWeapons, model.getEventTask(1));
    }

    @Test
    public void getEventTask_invalidIndex_throwsException() throws Exception {
        thrown.expect(IllegalValueException.class);
        model.getEventTask(0);
    }

    @Test
    public void removeEventTask_removesIndexInFilteredList() throws Exception {
        model.addEventTask(tpent.lunchWithBillGates);
        model.addEventTask(tpent.launchNuclearWeapons);
        model.setEventTaskFilter(eventTask -> eventTask.equals(tpent.launchNuclearWeapons));
        model.removeEventTask(0);
        model.setEventTaskFilter(null);
        assertEquals(Arrays.asList(Optional.of(tpent.lunchWithBillGates)), model.getFilteredEventTaskList());
    }

    @Test
    public void removeEventTask_invalidIndex_throwsException() throws Exception {
        thrown.expect(IllegalValueException.class);
        model.removeEventTask(0);
    }

    @Test
    public void setEventTask_replacesIndexInFilteredList() throws Exception {
        model.addEventTask(tpent.lunchWithBillGates);
        model.addEventTask(tpent.launchNuclearWeapons);
        model.setEventTaskFilter(eventTask -> eventTask.equals(tpent.launchNuclearWeapons));
        model.setEventTask(0, tpent.lunchWithBillGates);
        model.setEventTaskFilter(null);
        assertEquals(Arrays.asList(Optional.of(tpent.lunchWithBillGates), Optional.of(tpent.lunchWithBillGates)),
                    model.getFilteredEventTaskList());
    }

    @Test
    public void setEventTask_invalidIndex_throwsException() throws Exception {
        thrown.expect(IllegalValueException.class);
        model.setEventTask(0, tpent.lunchWithBillGates);
    }

    @Test
    public void addDeadlineTask_appendsDeadlineTask() throws Exception {
        model.addDeadlineTask(tpdue.speechTranscript);
        assertEquals(tpdue.speechTranscript, model.getDeadlineTask(0));
        model.addDeadlineTask(tpdue.assembleTheMissiles);
        assertEquals(tpdue.speechTranscript, model.getDeadlineTask(0));
        assertEquals(tpdue.assembleTheMissiles, model.getDeadlineTask(1));
    }

    @Test
    public void getDeadlineTask_invalidIndex_throwsException() throws Exception {
        thrown.expect(IllegalValueException.class);
        model.getDeadlineTask(0);
    }

    @Test
    public void removeDeadlineTask_removesIndexInFilteredList() throws Exception {
        model.addDeadlineTask(tpdue.speechTranscript);
        model.addDeadlineTask(tpdue.assembleTheMissiles);
        model.setDeadlineTaskFilter(deadlineTask -> deadlineTask.equals(tpdue.assembleTheMissiles));
        model.removeDeadlineTask(0);
        model.setDeadlineTaskFilter(null);
        assertEquals(Arrays.asList(Optional.of(tpdue.speechTranscript)), model.getFilteredDeadlineTaskList());
    }

    @Test
    public void removeDeadlineTask_invalidIndex_throwsException() throws Exception {
        thrown.expect(IllegalValueException.class);
        model.removeDeadlineTask(0);
    }

    @Test
    public void setDeadlineTask_replacesIndexInFilteredList() throws Exception {
        model.addDeadlineTask(tpdue.speechTranscript);
        model.addDeadlineTask(tpdue.assembleTheMissiles);
        model.setDeadlineTaskFilter(deadlineTask -> deadlineTask.equals(tpdue.assembleTheMissiles));
        model.setDeadlineTask(0, tpdue.speechTranscript);
        model.setDeadlineTaskFilter(null);
        assertEquals(Arrays.asList(Optional.of(tpdue.speechTranscript), Optional.of(tpdue.speechTranscript)),
                    model.getFilteredDeadlineTaskList());
    }

    @Test
    public void setDeadlineTask_invalidIndex_throwsException() throws Exception {
        thrown.expect(IllegalValueException.class);
        model.setDeadlineTask(0, tpdue.speechTranscript);
    }

    @Test
    public void undo_redo_throws_HeadAtBoundaryException() throws HeadAtBoundaryException {
        thrown.expect(HeadAtBoundaryException.class);
        model.undo();

        thrown.expect(HeadAtBoundaryException.class);
        model.redo();
    }

    @Test
    public void hasUncommitedChanges_manages_clear() {
        //check for empty taskbook
        assertEquals(new TaskBook(), model.getTaskBook());

        //clear an empty taskbook
        Command clear = new ClearCommand();
        clear.setData(model);
        clear.execute();
        assertFalse(model.hasUncommittedChanges());

        //clear a non-empty taskbook
        model.addFloatingTask(tpflt.buyAHelicopter);
        model.recordState("dummy command");
        clear.setData(model);
        clear.execute();
        assertTrue(model.hasUncommittedChanges());
    }

    @Test
    public void recordStateAndUndo_Redo_properlyUndosConsecutiveAdds() throws Exception {
        //create expected TaskBook
        TaskBook dummybook = new TaskBook();

        //Model task book is initially empty
        assertEquals(dummybook, model.getTaskBook());

        //We 'execute' command1
        model.addFloatingTask(tpflt.buyAHelicopter);
        final Commit command1Commit = model.recordState("command1");

        //update expected TaskBook
        dummybook.addFloatingTask(tpflt.buyAHelicopter);
        assertEquals(dummybook, model.getTaskBook());

        //We 'execute' command2
        model.addFloatingTask(tpflt.readABook);
        final Commit command2Commit = model.recordState("command2");

        //update expected TaskBook
        dummybook.addFloatingTask(tpflt.readABook);
        assertEquals(dummybook, model.getTaskBook());

        //We 'execute' command3
        model.addEventTask(tpent.launchNuclearWeapons);
        final Commit command3Commit = model.recordState("command3");

        //update expected TaskBook
        dummybook.addEventTask(tpent.launchNuclearWeapons);
        TaskBook dummybook3 = new TaskBook(dummybook); //dummybook3 has float:[buyAHelicopter, readAbook], event:[launchNuclearWeapons]

        assertEquals(dummybook, model.getTaskBook());

        //undo command 3
        assertEquals(command3Commit, model.undo());

        //update expected TaskBook
        dummybook.removeEventTask(0);
        TaskBook dummybook2 = new TaskBook(dummybook); //dummybook2 has float:[buyAHelicopter, readAbook]

        //test
        assertEquals(dummybook, model.getTaskBook());

        //undo command 2
        assertEquals(command2Commit, model.undo());

        //test with hasUncommittedChanges
        assertFalse(model.hasUncommittedChanges());

        //update expected taskbook
        dummybook.removeFloatingTask(1);
        TaskBook dummybook1 = new TaskBook(dummybook); //dummybook1 has float:[buyAHelicopter]

        //test
        assertEquals(dummybook, model.getTaskBook());

        //undo command 1
        assertEquals(command1Commit, model.undo());

        //test with hasUncommittedChanges
        assertFalse(model.hasUncommittedChanges());

        //update expected taskbook
        dummybook.removeFloatingTask(0); //empty taskbook

        //test
        assertEquals(new TaskBook(), dummybook);
        assertEquals(dummybook, model.getTaskBook());

        //consecutive redos
        //redo command1
        assertEquals(command1Commit, model.redo());
        assertEquals(dummybook1, model.getTaskBook());

        //redo command2
        assertEquals(command2Commit, model.redo());
        assertEquals(dummybook2, model.getTaskBook());

        //test with hasUncommittedChanges
        assertFalse(model.hasUncommittedChanges());

        //redo command3
        assertEquals(command3Commit, model.redo());
        assertEquals(dummybook3, model.getTaskBook());

        //no more redos expected
        thrown.expect(HeadAtBoundaryException.class);
        model.redo();
    }

    @Test
    public void recordStateAndUndo_properlyManagesStack() throws Exception {
        // Model task book is initially empty
        assertEquals(new TaskBook(), model.getTaskBook());

        // We "execute" command1, adding a floating task
        model.addFloatingTask(tpflt.buyAHelicopter);
        final Commit command1Commit = model.recordState("command 1");

        // Undo command1
        assertEquals(command1Commit, model.undo());
        assertEquals(new TaskBook(), model.getTaskBook()); // Model task book is back to being empty

        // We "execute" command2, adding an event
        model.addEventTask(tpent.lunchWithBillGates);
        final Commit command2Commit = model.recordState("command 2");

        // Undo command2
        assertEquals(command2Commit, model.undo());
        assertEquals(new TaskBook(), model.getTaskBook()); // Model task book is back to being empty

        // At this point, we should not be able to undo anymore.
        thrown.expect(HeadAtBoundaryException.class);
        model.undo();

        //test with hasUncommittedChanges
        assertFalse(model.hasUncommittedChanges());

        //We "execute" command 3, adding an event
        model.addEventTask(tpent.launchNuclearWeapons);
        final Commit command3Commit = model.recordState("command 3");

        //undo command3
        assertEquals(command3Commit, model.undo());
        assertEquals(new TaskBook(), model.getTaskBook()); // Model task book is back to being empty

        // We "execute" 4, adding a floating task
        model.addFloatingTask(tpflt.buyAHelicopter);
        final Commit command4Commit = model.recordState("command 4");

        // Undo command4
        assertEquals(command4Commit, model.undo());
        assertEquals(new TaskBook(), model.getTaskBook()); // Model task book is back to being empty

        // We "execute" command5, adding an event
        model.addEventTask(tpent.lunchWithBillGates);
        final Commit command5Commit = model.recordState("command 5");

        // Undo command5
        assertEquals(command5Commit, model.undo());
        assertEquals(new TaskBook(), model.getTaskBook()); // Model task book is back to being empty
    }
}
