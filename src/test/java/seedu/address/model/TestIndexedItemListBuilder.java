package seedu.address.model;

import java.util.ArrayList;
import java.util.List;

/**
 * A builder for a list of {@link TestIndexedItem}
 */
public class TestIndexedItemListBuilder<E> {
    private final ArrayList<TestIndexedItem<E>> list = new ArrayList<>();

    public TestIndexedItemListBuilder<E> add(int workingIndex, E item) {
        list.add(new TestIndexedItem<>(workingIndex, item));
        return this;
    }

    public List<TestIndexedItem<E>> build() {
        return new ArrayList<>(list);
    }
}
