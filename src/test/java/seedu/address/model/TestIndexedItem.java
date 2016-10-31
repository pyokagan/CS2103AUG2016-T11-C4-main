package seedu.address.model;

import com.google.common.base.MoreObjects;

/**
 * A test utility class for doing comparisons with other {@link IndexedItem}.
 */
public class TestIndexedItem<E> implements IndexedItem<E> {
    private final int workingIndex;
    private final E item;

    public TestIndexedItem(int workingIndex, E item) {
        this.workingIndex = workingIndex;
        this.item = item;
    }

    @Override
    public int getWorkingIndex() {
        return workingIndex;
    }

    @Override
    public E getItem() {
        return item;
    }

    @Override
    public boolean equals(Object other) {
        return other == this
               || (other instanceof IndexedItem<?>
               && workingIndex == ((IndexedItem<?>)other).getWorkingIndex()
               && item.equals(((IndexedItem<?>)other).getItem()));
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("workingIndex", workingIndex)
                .add("item", item)
                .toString();
    }
}
