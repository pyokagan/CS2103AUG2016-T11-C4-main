package seedu.address.logic.parser;

import java.io.File;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;

import seedu.address.commons.util.SubstringRange;

/**
 * Parser that parses a valid file path string.
 */
public class FileParser implements Parser<File> {

    @Override
    public File parse(String str) throws ParseException {
        if (str.trim().isEmpty()) {
            throw new ParseException("path cannot be empty", SubstringRange.of(str));
        }
        try {
            return Paths.get(str.trim()).toFile();
        } catch (InvalidPathException e) {
            throw new ParseException(e.getMessage(), e, SubstringRange.of(str));
        }
    }

}
