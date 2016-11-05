package seedu.address.logic.parser;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.SubstringRange;
import seedu.address.model.task.Name;

/**
 * A parser that parses a string {@link Name}.
 */
public class NameParser implements Parser<Name> {

    @Override
    public Name parse(String str) throws ParseException {
        try {
            return new Name(str);
        } catch (IllegalValueException e) {
            throw new ParseException(e.getMessage(), e, SubstringRange.of(str));
        }
    }

}
