package seedu.address.logic.parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.SubstringRange;

/**
 * Signals that a string input could not be parsed.
 */
public class ParseException extends IllegalValueException {

    private final List<SubstringRange> ranges = new ArrayList<>();

    /**
     * @param message should contain relevant information on why the input is invalid.
     * @param ranges substring range(s) of the invalid input.
     */
    public ParseException(String message, Collection<SubstringRange> ranges) {
        super(message);
        this.ranges.addAll(ranges);
    }

    public ParseException(String message, SubstringRange... ranges) {
        this(message, Arrays.asList(ranges));
    }

    /**
     * @param message should contain relevant information on why the input is invalid.
     * @param cause the exception which caused this exception to be thrown.
     * @param ranges substring range(s) of the invalid input.
     */
    public ParseException(String message, Throwable cause, Collection<SubstringRange> ranges) {
        super(message, cause);
        this.ranges.addAll(ranges);
    }

    public ParseException(String message, Throwable cause, SubstringRange... ranges) {
        this(message, cause, Arrays.asList(ranges));
    }

    public List<SubstringRange> getRanges() {
        return Collections.unmodifiableList(ranges);
    }

}
