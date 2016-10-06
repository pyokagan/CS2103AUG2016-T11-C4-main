package seedu.address.model.task;

import java.util.Objects;

import seedu.address.commons.util.CollectionUtil;

/**
 * Represents a Task in the address book.
 * Guarantees: Is a POJO. Details are present and not null. Field values are validated and immutable.
 */
public class Task {

    private final Name name;

    /**
     * Every field must be present and not null.
     */
    public Task(Name name) {
        assert !CollectionUtil.isAnyNull(name);
        this.name = name;
    }

    /**
     * Copy constructor.
     */
    public Task(Task source) {
        this(source.getName());
    }

    public Name getName() {
        return name;
    }

    @Override
    public boolean equals(Object other) {
        return other == this
               || (other instanceof Task
               && ((Task)other).getName().equals(getName()));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName());
        return builder.toString();
    }

}
