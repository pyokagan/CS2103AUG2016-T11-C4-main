package seedu.address.model;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javafx.collections.ObservableList;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Task;
import seedu.address.model.task.UniqueTaskList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .equals comparison)
 */
public class TaskBook implements ReadOnlyTaskBook {

    private final UniqueTaskList tasks;
    private final UniqueTagList tags;

    {
        tasks = new UniqueTaskList();
        tags = new UniqueTagList();
    }

    public TaskBook() {}

    /**
     * Persons and Tags are copied into this addressbook
     */
    public TaskBook(ReadOnlyTaskBook toBeCopied) {
        this(toBeCopied.getUniqueTaskList(), toBeCopied.getUniqueTagList());
    }

    /**
     * Persons and Tags are copied into this addressbook
     */
    public TaskBook(UniqueTaskList persons, UniqueTagList tags) {
        resetData(persons.getInternalList(), tags.getInternalList());
    }

    public static ReadOnlyTaskBook getEmptyTaskBook() {
        return new TaskBook();
    }

    //// list overwrite operations

    public ObservableList<Task> getTasks() {
        return tasks.getInternalList();
    }

    public void setTasks(List<Task> persons) {
        this.tasks.getInternalList().setAll(persons);
    }

    public void setTags(Collection<Tag> tags) {
        this.tags.getInternalList().setAll(tags);
    }

    public void resetData(Collection<? extends ReadOnlyTask> newPersons, Collection<Tag> newTags) {
        setTasks(newPersons.stream().map(Task::new).collect(Collectors.toList()));
        setTags(newTags);
    }

    public void resetData(ReadOnlyTaskBook newData) {
        resetData(newData.getPersonList(), newData.getTagList());
    }

    //// task-level operations

    /**
     * Adds a task to the task book.
     * Also checks the new tasks's tags and updates {@link #tags} with any new tags found,
     * and updates the Tag objects in the task to point to those in {@link #tags}.
     *
     * @throws UniqueTaskList.DuplicatePersonException if an equivalent person already exists.
     */
    public void addTask(Task task) throws UniqueTaskList.DuplicatePersonException {
        syncTagsWithMasterList(task);
        tasks.add(task);
    }

    /**
     * Ensures that every tag in this person:
     *  - exists in the master list {@link #tags}
     *  - points to a Tag object in the master list
     */
    private void syncTagsWithMasterList(Task person) {
        final UniqueTagList personTags = person.getTags();
        tags.mergeFrom(personTags);

        // Create map with values = tag object references in the master list
        final Map<Tag, Tag> masterTagObjects = new HashMap<>();
        for (Tag tag : tags) {
            masterTagObjects.put(tag, tag);
        }

        // Rebuild the list of person tags using references from the master list
        final Set<Tag> commonTagReferences = new HashSet<>();
        for (Tag tag : personTags) {
            commonTagReferences.add(masterTagObjects.get(tag));
        }
        person.setTags(new UniqueTagList(commonTagReferences));
    }

    public boolean removeTask(ReadOnlyTask key) throws UniqueTaskList.PersonNotFoundException {
        if (tasks.remove(key)) {
            return true;
        } else {
            throw new UniqueTaskList.PersonNotFoundException();
        }
    }

    //// tag-level operations

    public void addTag(Tag t) throws UniqueTagList.DuplicateTagException {
        tags.add(t);
    }

    //// util methods

    @Override
    public String toString() {
        return tasks.getInternalList().size() + " tasks, " + tags.getInternalList().size() + " tags";
        // TODO: refine later
    }

    @Override
    public List<ReadOnlyTask> getPersonList() {
        return Collections.unmodifiableList(tasks.getInternalList());
    }

    @Override
    public List<Tag> getTagList() {
        return Collections.unmodifiableList(tags.getInternalList());
    }

    @Override
    public UniqueTaskList getUniqueTaskList() {
        return this.tasks;
    }

    @Override
    public UniqueTagList getUniqueTagList() {
        return this.tags;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TaskBook // instanceof handles nulls
                && this.tasks.equals(((TaskBook) other).tasks)
                && this.tags.equals(((TaskBook) other).tags));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(tasks, tags);
    }
}
