package seedu.address.commons.core;

/**
 * Index prefixes for different kinds of tasks.
 */
public enum IndexPrefix {

    FLOAT("f", "floating task index"),
    DEADLINE("d", "deadline task index"),
    EVENT("e", "event task index");

    private final String prefixString;
    private final String name;

    IndexPrefix(String prefixString, String name) {
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
