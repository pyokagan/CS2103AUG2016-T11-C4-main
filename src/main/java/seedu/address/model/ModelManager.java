package seedu.address.model;

import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.commons.events.model.TaskBookChangedEvent;
import seedu.address.model.task.Task;
import seedu.address.model.task.TaskNotFoundException;

/**
 * Represents the in-memory model of the address book data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final TaskBook addressBook;
    private final FilteredList<Task> filteredTasks;

    /**
     * Initializes a ModelManager with the given TaskBook
     * TaskBook and its variables should not be null
     */
    public ModelManager(TaskBook src) {
        super();
        assert src != null;

        logger.fine("Initializing with address book: " + src);

        addressBook = new TaskBook(src);
        filteredTasks = new FilteredList<>(addressBook.getTasks());
    }

    public ModelManager() {
        this(new TaskBook());
    }

    public ModelManager(ReadOnlyTaskBook initialData) {
        addressBook = new TaskBook(initialData);
        filteredTasks = new FilteredList<>(addressBook.getTasks());
    }

    @Override
    public void resetData(ReadOnlyTaskBook newData) {
        addressBook.resetData(newData);
        indicateAddressBookChanged();
    }

    @Override
    public ReadOnlyTaskBook getAddressBook() {
        return addressBook;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateAddressBookChanged() {
        raise(new TaskBookChangedEvent(addressBook));
    }

    @Override
    public synchronized void deleteTask(Task target) throws TaskNotFoundException {
        addressBook.removeTask(target);
        indicateAddressBookChanged();
    }

    @Override
    public synchronized void addTask(Task person) {
        addressBook.addTask(person);
        setFilter(null);
        indicateAddressBookChanged();
    }

    //=========== Filtered Task List Accessors ===============================================================

    @Override
    public UnmodifiableObservableList<Task> getFilteredTaskList() {
        return new UnmodifiableObservableList<>(filteredTasks);
    }

    @Override
    public void setFilter(Predicate<Task> predicate) {
        filteredTasks.setPredicate(predicate);
    }

}
