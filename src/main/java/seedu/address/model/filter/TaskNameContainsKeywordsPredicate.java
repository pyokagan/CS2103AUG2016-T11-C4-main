package seedu.address.model.filter;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.task.DeadlineTask;
import seedu.address.model.task.EventTask;
import seedu.address.model.task.FloatingTask;
import seedu.address.model.task.Task;

public class TaskNameContainsKeywordsPredicate implements TaskPredicate {

    private final HashSet<String> keywords;

    public TaskNameContainsKeywordsPredicate(Set<String> keywords) {
        assert !keywords.isEmpty();
        this.keywords = new HashSet<>(keywords);
    }

    private boolean testTask(Task task) {
        return keywords.stream()
                        .filter(keyword -> StringUtil.containsIgnoreCase(task.getName().toString(), keyword))
                        .findAny()
                        .isPresent();
    }

    @Override
    public boolean test(FloatingTask floatingTask) {
        return testTask(floatingTask);
    }

    @Override
    public boolean test(DeadlineTask deadlineTask) {
        return testTask(deadlineTask);
    }

    @Override
    public boolean test(EventTask eventTask) {
        return testTask(eventTask);
    }

    @Override
    public String toHumanReadableString() {
        return keywords.stream()
                    .collect(Collectors.joining(", ", "name contains any of keywords: ", ""));
    }

}
