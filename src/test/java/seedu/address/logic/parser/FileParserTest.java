package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.Arrays;

import org.junit.Test;

import seedu.address.commons.util.SubstringRange;

public class FileParserTest {

    private final FileParser fileParser = new FileParser();

    @Test
    public void parse_withValidPath_returnsFile() throws ParseException {
        final File file = fileParser.parse(" a/b ");
        assertEquals(" a/b ", file.getPath());
    }

    @Test
    public void parse_withEmptyPath_throwsParseException() throws ParseException {
        try {
            fileParser.parse("");
            assert false;
        } catch (ParseException e) {
            assertEquals("path cannot be empty", e.getMessage());
            assertEquals(Arrays.asList(new SubstringRange(0, 0)), e.getRanges());
        }
    }

    @Test
    public void parse_withInvalidPath_throwsParseException() throws ParseException {
        // NUL character should be an invalid character on most operating systems.
        try {
            fileParser.parse("\0");
            assert false;
        } catch (ParseException e) {
            assertEquals(Arrays.asList(new SubstringRange(0, 1)), e.getRanges());
        }
    }

}
