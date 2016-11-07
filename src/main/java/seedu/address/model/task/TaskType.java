package seedu.address.model.task;

/**
 * Enumeration of task types and their associated info.
 */
public enum TaskType {
    FLOAT("f", "floating task"),
    DEADLINE("d", "deadline task"),
    EVENT("e", "event task");

    private final String prefixString;
    private final String name;

    TaskType(String prefixString, String name) {
        this.prefixString = prefixString;
        this.name = name;
    }

    public String getPrefixString() {
        return prefixString;
    }

    public String getName() {
        return name;
    }

}
