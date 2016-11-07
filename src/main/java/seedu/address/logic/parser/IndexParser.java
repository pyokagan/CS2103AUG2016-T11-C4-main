package seedu.address.logic.parser;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.SubstringRange;
import seedu.address.model.task.TaskType;

/**
 * Parses a task index of format <code>&lt;PREFIX&gt;&lt;INDEX&gt;</code>, where
 * <code>&lt;PREFIX&gt;</code> is a prefix which the index must have, and <code>&lt;INDEX&gt;</code>
 * is a 1-index signed integer index.
 */
public class IndexParser implements Parser<Integer> {

    private static final String MSG_NO_PREFIX = "%2$s must start with \"%1$s\"";
    private static final String MSG_NOT_INDEX = "invalid %2$s: %3$s";

    private final String prefix;
    private final String name;

    public IndexParser(String prefix, String name) {
        CollectionUtil.isAnyNull(prefix, name);
        this.prefix = prefix;
        this.name = name;
    }

    public IndexParser(TaskType taskType) {
        this(taskType.getPrefixString(), taskType.getName() + " index");
    }

    @Override
    public Integer parse(String str) throws ParseException {
        assert str != null;
        if (!str.startsWith(prefix)) {
            throw new ParseException(String.format(MSG_NO_PREFIX, prefix, name),
                                     new SubstringRange(0, Math.min(prefix.length(), str.length())));
        }

        String indexStr = str.substring(prefix.length());
        if (!StringUtil.isUnsignedInteger(indexStr)) {
            throw new ParseException(String.format(MSG_NOT_INDEX, prefix, name, indexStr),
                                     new SubstringRange(prefix.length(), str.length()));
        }

        return Integer.parseInt(indexStr);
    }

}
