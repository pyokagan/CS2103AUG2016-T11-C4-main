package seedu.address.commons.util;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ComparisonChain;

/**
 * Represents a subtring of a string.
 */
public class SubstringRange implements Comparable<SubstringRange> {

    private final int start;

    private final int end;

    public SubstringRange(int start, int end) {
        assert start >= 0;
        assert end >= 0;
        assert end >= start;
        this.start = start;
        this.end = end;
    }

    /**
     * Returns a substring range that covers the whole string.
     */
    public static SubstringRange of(String str) {
        return new SubstringRange(0, str.length());
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    public SubstringRange indent(int x) {
        assert start + x >= 0;
        return new SubstringRange(start + x, end + x);
    }

    @Override
    public int compareTo(SubstringRange other) {
        return ComparisonChain.start()
                .compare(start, other.start)
                .compare(end, other.end)
                .result();
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof SubstringRange
                && start == ((SubstringRange)other).start
                && end == ((SubstringRange)other).end
                );
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("start", start)
                .add("end", end)
                .toString();
    }

}
