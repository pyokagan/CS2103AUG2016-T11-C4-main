package seedu.address.model;

/**
 * Represents a list item that has an associated working index.
 */
public interface IndexedItem<E> {
    /** Returns the item's working index. This index will remain the same as long as the list is not repopulated. */
    int getWorkingIndex();

    /** Returns the item value. */
    E getItem();
}
