package seedu.address.ui;

import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.beans.binding.Bindings;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.IndexedItem;
import seedu.address.model.ReadOnlyModel;
import seedu.address.model.filter.TaskOverduePredicate;
import seedu.address.model.filter.TaskPredicate;
import seedu.address.model.filter.TaskUnfinishedPredicate;
import seedu.address.model.filter.TaskWillHappenTodayPredicate;
import seedu.address.model.task.DeadlineTask;

public class DeadlineTaskListPane extends UiPart<Pane> {

    private static final String FXML = "/view/DeadlineTaskListPane.fxml";

    private static final Logger logger = LogsCenter.getLogger(DeadlineTaskListPane.class);

    private final ObservableList<IndexedItem<DeadlineTask>> listedDeadlineTasks;
    private final FilteredList<IndexedItem<DeadlineTask>> todayDeadlineTasks;
    private final FilteredList<IndexedItem<DeadlineTask>> overdueDeadlineTasks;
    private final FilteredList<IndexedItem<DeadlineTask>> unfinishedDeadlineTasks;

    @FXML
    private ListView<IndexedItem<DeadlineTask>> deadlineTaskListView;

    @FXML
    private Label listedDeadlineCounter;
    @FXML
    private Label todayUnfinishedDeadlineCounter;
    @FXML
    private Label overdueDeadlineCounter;
    @FXML
    private Label unfinishedDeadlineCounter;

    public DeadlineTaskListPane(ReadOnlyModel model) {
        super(FXML);

        // Initialize task lists
        listedDeadlineTasks = model.getDeadlineTaskList();
        todayDeadlineTasks = new FilteredList<>(listedDeadlineTasks);
        overdueDeadlineTasks = new FilteredList<>(listedDeadlineTasks);
        unfinishedDeadlineTasks = new FilteredList<>(listedDeadlineTasks);
        listedDeadlineTasks.addListener((ListChangeListener.Change<? extends IndexedItem<DeadlineTask>> c) -> {
            updatePredicatesToCurrentTime();
            logger.info("DeadlineTask counter updated with current time.");
        });
        updatePredicatesToCurrentTime();

        // Initialize Floating Task List View
        deadlineTaskListView.setItems(listedDeadlineTasks);
        deadlineTaskListView.setCellFactory(listView -> new DeadlineTaskListCell());

        // Initialize Task Counter
        listedDeadlineCounter.textProperty().bind(Bindings.size(listedDeadlineTasks)
                                                  .asString("Number of Deadlines listed: %d"));
        todayUnfinishedDeadlineCounter.textProperty().bind(Bindings.size(todayDeadlineTasks)
                                                           .asString("Today remaining: %d"));
        overdueDeadlineCounter.textProperty().bind(Bindings.size(overdueDeadlineTasks)
                                                   .asString("Overdue: %d"));
        unfinishedDeadlineCounter.textProperty().bind(Bindings.size(unfinishedDeadlineTasks)
                                                      .asString("Total unfinished: %d"));
    }

    /**
     * Selects a deadline task as specified by its working index.
     */
    public void select(int workingIndex) {
        final List<IndexedItem<DeadlineTask>> deadlineTaskList = deadlineTaskListView.getItems();
        for (int i = 0; i < deadlineTaskList.size(); i++) {
            if (deadlineTaskList.get(i).getWorkingIndex() == workingIndex) {
                deadlineTaskListView.scrollTo(i);
                deadlineTaskListView.getSelectionModel().select(i);
                return;
            }
        }
    }

    /**
     * Clears any deadline task selection.
     */
    public void clearSelect() {
        deadlineTaskListView.getSelectionModel().clearSelection();
    }

    private void updatePredicatesToCurrentTime() {
        final LocalDateTime now = LocalDateTime.now();
        todayDeadlineTasks.setPredicate(makePredicate(new TaskWillHappenTodayPredicate(now)));
        overdueDeadlineTasks.setPredicate(makePredicate(new TaskOverduePredicate(now)));
        unfinishedDeadlineTasks.setPredicate(makePredicate(new TaskUnfinishedPredicate(now)));
    }

    private Predicate<IndexedItem<DeadlineTask>> makePredicate(TaskPredicate predicate) {
        return indexedItem -> predicate.test(indexedItem.getItem());
    }

    private static class DeadlineTaskListCell extends ListCell<IndexedItem<DeadlineTask>> {
        @Override
        protected void updateItem(IndexedItem<DeadlineTask> deadlineTask, boolean empty) {
            super.updateItem(deadlineTask, empty);
            final DeadlineTaskListCard card;
            card = new DeadlineTaskListCard(deadlineTask != null ? deadlineTask.getItem() : null,
                                            deadlineTask != null ? deadlineTask.getWorkingIndex() : 0);
            setPrefWidth(10); // This will stop the cards from extending beyond the horizontal width
            setGraphic(card.getRoot());
        }
    }

}
