package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import seedu.address.commons.util.SubstringRange;

public class IndexParserTest {

    @Test
    public void parse_emptyString_throwsException() {
        final IndexParser parser = new IndexParser("abc", "ultra parser index");
        try {
            parser.parse("");
            assert false;
        } catch (ParseException e) {
            assertEquals("ultra parser index must start with \"abc\"", e.getMessage());
            List<SubstringRange> expected = Arrays.asList(new SubstringRange(0, 0));
            assertEquals(expected, e.getRanges());
        }
    }

    @Test
    public void parse_notPrefixed_throwsException() {
        final IndexParser parser = new IndexParser("gh", "super parser index");
        try {
            parser.parse("g14");
        } catch (ParseException e) {
            assertEquals("super parser index must start with \"gh\"", e.getMessage());
            List<SubstringRange> expected = Arrays.asList(new SubstringRange(0, 2));
            assertEquals(expected, e.getRanges());
        }
    }

    @Test
    public void parse_notUnsignedInteger_throwsException() {
        final IndexParser parser = new IndexParser("abc", "banana");
        try {
            parser.parse("abc0");
        } catch (ParseException e) {
            assertEquals("invalid banana: 0", e.getMessage());
            List<SubstringRange> expected = Arrays.asList(new SubstringRange(3, 4));
            assertEquals(expected, e.getRanges());
        }
    }

    @Test
    public void parse_validIndex_returnsIntegerMinusOne() throws Exception {
        final IndexParser parser = new IndexParser("453", "super parser index");
        int index = parser.parse("4539001");
        assertEquals(9000, index);
    }

}
