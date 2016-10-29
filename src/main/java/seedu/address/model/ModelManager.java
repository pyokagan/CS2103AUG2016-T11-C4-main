package seedu.address.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.TaskBookChangedEvent;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.Command;
import seedu.address.model.config.Config;
import seedu.address.model.config.ReadOnlyConfig;
import seedu.address.model.task.DeadlineTask;
import seedu.address.model.task.EventTask;
import seedu.address.model.task.FloatingTask;

/**
 * Represents the in-memory model of the address book data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final Config config;
    private final WorkingTaskBook workingTaskBook;

    //for undo
    private ArrayList<Commit> commits = new ArrayList<Commit>();
    private int head; //head points to a the current commit which holds the TaskBook displayed by the UI

    /**
     * Initializes a ModelManager with the given config and TaskBook
     * TaskBook and its variables should not be null
     */
    public ModelManager(ReadOnlyConfig config, ReadOnlyTaskBook taskBook) {
        super();
        assert taskBook != null;

        logger.fine("Initializing with config: " + config + " and task book: " + taskBook);

        this.config = new Config(config);
        this.workingTaskBook = new WorkingTaskBook(taskBook);
        recordState(null);
    }

    public ModelManager() {
        this(new Config(), new TaskBook());
    }

    //// Config

    @Override
    public ReadOnlyConfig getConfig() {
        return config;
    }

    @Override
    public String getTaskBookFilePath() {
        return config.getTaskBookFilePath();
    }

    @Override
    public void setTaskBookFilePath(String filePath) {
        config.setTaskBookFilePath(filePath);
    }

    //// Task Book

    @Override
    public void resetTaskBook(ReadOnlyTaskBook newData) {
        workingTaskBook.resetData(newData);
        indicateTaskBookChanged();
    }

    @Override
    public ReadOnlyTaskBook getTaskBook() {
        return workingTaskBook.getTaskBook();
    }

    /** Raises an event to indicate the model has changed */
    private void indicateTaskBookChanged() {
        raise(new TaskBookChangedEvent(workingTaskBook.getTaskBook()));
    }

    //// Floating tasks

    @Override
    public synchronized int addFloatingTask(FloatingTask floatingTask) {
        final int workingIndex = workingTaskBook.addFloatingTask(floatingTask);
        indicateTaskBookChanged();
        return workingIndex;
    }

    @Override
    public synchronized FloatingTask getFloatingTask(int workingIndex) throws IllegalValueException {
        return workingTaskBook.getFloatingTask(workingIndex);
    }

    @Override
    public synchronized FloatingTask removeFloatingTask(int workingIndex) throws IllegalValueException {
        final FloatingTask removedFloating = workingTaskBook.removeFloatingTask(workingIndex);
        indicateTaskBookChanged();
        return removedFloating;
    }

    @Override
    public synchronized void setFloatingTask(int workingIndex, FloatingTask newFloatingTask)
            throws IllegalValueException {
        workingTaskBook.setFloatingTask(workingIndex, newFloatingTask);
        indicateTaskBookChanged();
    }

    @Override
    public ObservableList<IndexedItem<FloatingTask>> getFloatingTaskList() {
        return workingTaskBook.getFloatingTaskList();
    }

    @Override
    public void setFloatingTaskPredicate(Predicate<? super FloatingTask> predicate) {
        workingTaskBook.setFloatingTaskPredicate(predicate);
    }

    @Override
    public Comparator<? super FloatingTask> getFloatingTaskComparator() {
        return workingTaskBook.getFloatingTaskComparator();
    }

    @Override
    public void setFloatingTaskComparator(Comparator<? super FloatingTask> comparator) {
        workingTaskBook.setFloatingTaskComparator(comparator);
    }

    //// Deadline tasks

    @Override
    public synchronized int addDeadlineTask(DeadlineTask deadlineTask) {
        assert deadlineTask.isFinished() == false;
        final int workingIndex = workingTaskBook.addDeadlineTask(deadlineTask);
        indicateTaskBookChanged();
        return workingIndex;
    }

    @Override
    public synchronized DeadlineTask getDeadlineTask(int workingIndex) throws IllegalValueException {
        return workingTaskBook.getDeadlineTask(workingIndex);
    }

    @Override
    public synchronized DeadlineTask removeDeadlineTask(int workingIndex) throws IllegalValueException {
        final DeadlineTask removedDeadline = workingTaskBook.removeDeadlineTask(workingIndex);
        indicateTaskBookChanged();
        return removedDeadline;
    }

    @Override
    public synchronized void setDeadlineTask(int workingIndex, DeadlineTask newDeadlineTask)
            throws IllegalValueException {
        workingTaskBook.setDeadlineTask(workingIndex, newDeadlineTask);
        indicateTaskBookChanged();
    }

    @Override
    public ObservableList<IndexedItem<DeadlineTask>> getDeadlineTaskList() {
        return workingTaskBook.getDeadlineTaskList();
    }

    @Override
    public void setDeadlineTaskPredicate(Predicate<? super DeadlineTask> predicate) {
        workingTaskBook.setDeadlineTaskPredicate(predicate);
    }

    @Override
    public Comparator<? super DeadlineTask> getDeadlineTaskComparator() {
        return workingTaskBook.getDeadlineTaskComparator();
    }

    @Override
    public void setDeadlineTaskComparator(Comparator<? super DeadlineTask> comparator) {
        workingTaskBook.setDeadlineTaskComparator(comparator);
    }

    //// Event tasks

    @Override
    public synchronized int addEventTask(EventTask eventTask) {
        final int workingIndex = workingTaskBook.addEventTask(eventTask);
        indicateTaskBookChanged();
        return workingIndex;
    }

    @Override
    public synchronized EventTask getEventTask(int workingIndex) throws IllegalValueException {
        return workingTaskBook.getEventTask(workingIndex);
    }

    @Override
    public synchronized EventTask removeEventTask(int workingIndex) throws IllegalValueException {
        final EventTask removedEvent = workingTaskBook.removeEventTask(workingIndex);
        indicateTaskBookChanged();
        return removedEvent;
    }

    @Override
    public synchronized void setEventTask(int workingIndex, EventTask newEventTask)
            throws IllegalValueException {
        workingTaskBook.setEventTask(workingIndex, newEventTask);
        indicateTaskBookChanged();
    }

    @Override
    public ObservableList<IndexedItem<EventTask>> getEventTaskList() {
        return workingTaskBook.getEventTaskList();
    }

    @Override
    public void setEventTaskPredicate(Predicate<? super EventTask> predicate) {
        workingTaskBook.setEventTaskPredicate(predicate);
    }

    @Override
    public Comparator<? super EventTask> getEventTaskComparator() {
        return workingTaskBook.getEventTaskComparator();
    }

    @Override
    public void setEventTaskComparator(Comparator<? super EventTask> comparator) {
        workingTaskBook.setEventTaskComparator(comparator);
    }

    ////undo redo

    @Override
    public Command undo() throws HeadAtBoundaryException {
        if (head <= 0) {
            throw new HeadAtBoundaryException();
        }
        Command undoneAction = commits.get(head).getCommand();
        head--;
        Commit commit = commits.get(head);
        resetTaskBook(commit.getTaskBook());
        return undoneAction;
    }

    @Override
    public void recordState(Command command) {
        //clear redoable, which are the commits above head
        head ++;
        while (this.head < (commits.size())) {
            commits.remove(head);
        }
        commits.add(new Commit(command, new TaskBook(getTaskBook())));
        head = commits.size() - 1;
    }

    @Override
    public Command redo() throws HeadAtBoundaryException {
        if (head >= commits.size() - 1) {
            throw new HeadAtBoundaryException();
        }
        head++;
        Commit commit = commits.get(head);
        resetTaskBook(commit.getTaskBook());
        return commit.getCommand();
    }

    /**
     * check if taskBook has changed.
     * @return true if TaskBook changed
     */
    @Override
    public boolean hasUncommittedChanges() {
        return !(getTaskBook().equals(commits.get(head).getTaskBook()));
    }

    private class Commit {
        private TaskBook taskBook;
        private Command command;

        Commit(Command command, TaskBook state) {
            this.taskBook = state;
            this.command = command;
        }

        public TaskBook getTaskBook() {
            return this.taskBook;
        }

        public Command getCommand() {
            return this.command;
        }
    }

    public class HeadAtBoundaryException extends Exception {
        public HeadAtBoundaryException() {
            super();
        }
    }
}
