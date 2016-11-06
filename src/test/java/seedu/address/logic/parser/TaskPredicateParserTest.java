package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;
import static seedu.address.commons.time.TypicalLocalDateTimes.UNIX_EPOCH;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Test;

import seedu.address.commons.util.SubstringRange;
import seedu.address.model.ModelManager;
import seedu.address.model.filter.TaskFinishedPredicate;
import seedu.address.model.filter.TaskUnfinishedPredicate;

public class TaskPredicateParserTest {

    private final ModelManager model = new ModelManager();

    private final TaskPredicateParser parser = new TaskPredicateParser(UNIX_EPOCH);

    @Test
    public void parse_withAll_returnsNull() throws ParseException {
        assertEquals(null, parser.parse("  "));
        assertEquals(null, parser.parse(" all "));
    }

    @Test
    public void parse_withFin_returnsTaskFinishedPredicate() throws ParseException {
        assertEquals(new TaskFinishedPredicate(UNIX_EPOCH), parser.parse(" fin "));
    }

    @Test
    public void parse_withUnfin_returnsTaskUnfinishedPredicate() throws ParseException {
        assertEquals(new TaskUnfinishedPredicate(UNIX_EPOCH), parser.parse(" unfin "));
    }

    @Test
    public void parse_unknownPredicateName_throwsParseException() {
        try {
            parser.parse(" invalid ");
            assert false;
        } catch (ParseException e) {
            assertEquals("unknown predicate: invalid", e.getMessage());
            assertEquals(Arrays.asList(new SubstringRange(0, 9)), e.getRanges());
        }
    }

    @Test
    public void autocomplete_returnsPredicateNamesStartingWithInput() {
        assertEquals(Arrays.asList("in"), parser.autocomplete(model, " f ", 2));
        assertEquals(Collections.emptyList(), parser.autocomplete(model, " f ", 0));
        assertEquals(Collections.emptyList(), parser.autocomplete(model, " f ", 3));
        assertEquals(Arrays.asList("all", "fin", "unfin"), parser.autocomplete(model, "  ", 1));
    }

}
