package seedu.address.model.task;

import java.time.LocalDateTime;

import seedu.address.commons.util.CollectionUtil;

/**
 * Represents a Task in the address book.
 * Guarantees: Is a POJO. Details are present and not null. Field values are validated and immutable.
 */
public abstract class Task {

    protected final Name name;

    /**
     * Every field must be present and not null.
     */
    public Task(Name name) {
        assert !CollectionUtil.isAnyNull(name);
        this.name = name;
    }

    public Name getName() {
        return name;
    }

    public String localDateTimeToPrettyString(LocalDateTime ldt) {
        String ldtString = ldt.toString();
        String[] parts = ldtString.split("-");
        String yyyy = parts[0];
        String mm = parts[1];
        ldtString = parts[2];
        parts = ldtString.split("T");
        String dd = parts[0];
        String time = parts[1];

        String pretty = dd + "/" + mm + "/" + yyyy + " " + "Time: " + time;

        return pretty;
    }

}
