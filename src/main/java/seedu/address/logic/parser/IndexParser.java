package seedu.address.logic.parser;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.SubstringRange;

public class IndexParser implements Parser<Integer> {

    @Override
    public Integer parse(String str) throws ParseException {
        if (!StringUtil.isUnsignedInteger(str)) {
            throw new ParseException("not an index", SubstringRange.of(str));
        }
        return Integer.parseInt(str) - 1;
    }

}
