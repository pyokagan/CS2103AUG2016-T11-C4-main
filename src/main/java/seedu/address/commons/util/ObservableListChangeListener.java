package seedu.address.commons.util;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

/**
 * A utility class that tracks whether ObservableList(s) have changed.
 */
public class ObservableListChangeListener {

    private boolean hasChanged = false;

    private final ObservableList<?>[] lists;

    public ObservableListChangeListener(ObservableList<?>... lists) {
        // Store a reference to the observable lists so they do not get GC'd
        this.lists = lists;
        // Install our listeners on the observable lists
        for (ObservableList<?> list : lists) {
            list.addListener((ListChangeListener.Change<? extends Object> change) -> {
                hasChanged = true;
            });
        }
    }

    public void setHasChanged(boolean hasChanged) {
        this.hasChanged = hasChanged;
    }

    public boolean getHasChanged() {
        return hasChanged;
    }

}
