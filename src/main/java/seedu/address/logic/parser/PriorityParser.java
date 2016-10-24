package seedu.address.logic.parser;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.SubstringRange;
import seedu.address.model.task.Priority;

public class PriorityParser implements Parser<Priority> {

    @Override
    public Priority parse(String str) throws ParseException {
        try {
            return new Priority(str);
        } catch (IllegalValueException e) {
            throw new ParseException(e.getMessage(), e, SubstringRange.of(str));
        }
    }

}
