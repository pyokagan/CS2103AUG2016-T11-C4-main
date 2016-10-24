package seedu.address.logic.parser;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import seedu.address.commons.util.SubstringRange;

public class ParseExceptionBuilder {

    private StringBuilder message = new StringBuilder();
    private Throwable cause;
    private ArrayList<SubstringRange> ranges = new ArrayList<>();

    public ParseExceptionBuilder(String message) {
        assert message != null;
        this.message.append(message);
    }

    public ParseExceptionBuilder(Throwable cause) {
        this.message.append(cause.getMessage());
        this.cause = cause;
    }

    public ParseExceptionBuilder(ParseException cause) {
        this((Throwable)cause);
        this.ranges.addAll(cause.getRanges());
    }

    public ParseExceptionBuilder prependMessage(String message) {
        this.message.insert(0, message);
        return this;
    }

    public ParseExceptionBuilder appendMessage(String message) {
        this.message.append(message);
        return this;
    }

    public ParseExceptionBuilder addRange(SubstringRange range) {
        ranges.add(range);
        return this;
    }

    public ParseExceptionBuilder addRangeOptional(Optional<SubstringRange> range) {
        if (range.isPresent()) {
            addRange(range.get());
        }
        return this;
    }

    public ParseExceptionBuilder addRanges(Collection<SubstringRange> ranges) {
        this.ranges.addAll(ranges);
        return this;
    }

    public ParseExceptionBuilder indentRanges(int x) {
        ranges = ranges.stream()
                    .map(range -> range.indent(x))
                    .collect(Collectors.toCollection(ArrayList::new));
        return this;
    }

    public ParseExceptionBuilder clearRanges() {
        ranges.clear();
        return this;
    }

    public ParseException build() {
        return new ParseException(message.toString(), cause, ranges);
    }

}
