package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.hamcrest.CoreMatchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;

import seedu.address.logic.commands.Command;
import seedu.address.model.ModelManager;

public class SubcommandParserTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final ModelManager model = new ModelManager();

    private final SubcommandParser parser = new SubcommandParser();

    @Test
    public void parse_emptyString_throwsParseException() throws ParseException {
        thrown.expect(ParseException.class);
        thrown.expectMessage("No command name given");
        parser.parse("");
    }

    @Test
    public void parse_unknownCommand_throwsParseException() throws ParseException {
        thrown.expect(ParseException.class);
        thrown.expectMessage("Unknown command: banana");
        parser.parse(" banana ");
    }

    @Test
    public void parse_knownCommand_callsParseOfSubparser() throws ParseException {
        final Command someCommand = model -> {
            throw new AssertionError("should not be called");
        };
        final Parser<Command> subparser = mockParser();
        Mockito.when(subparser.parse(" ")).thenReturn(someCommand);
        parser.putSubcommand("apple", subparser);
        final Command command = parser.parse("apple ");
        assertTrue(command == someCommand);
        Mockito.verify(subparser).parse(" ");
    }

    @Test
    public void parse_subparserThrowsParseException_rethrowsParseException() throws ParseException {
        final ParseException cause = new ParseException("some parse exception");
        final Parser<Command> subparser = str -> {
            throw cause;
        };
        parser.putSubcommand("banana", subparser);
        thrown.expect(ParseException.class);
        thrown.expectMessage("banana: some parse exception");
        thrown.expectCause(CoreMatchers.is(cause));
        parser.parse("banana");
    }

    @Test
    public void autocomplete_caretAtCommandName_autocompletesCommand() {
        final Parser<Command> subparser = str -> {
            throw new AssertionError("should not be called");
        };
        parser.putSubcommand("banana", subparser);
        parser.putSubcommand("basketball", subparser);
        parser.putSubcommand("apple", subparser);
        // No command word
        assertEquals(Arrays.asList("apple", "banana", "basketball"), parser.autocomplete(model, "  ", 1));
        // Caret at end of command word
        assertEquals(Arrays.asList("anana", "asketball"), parser.autocomplete(model, " b", 2));
        // Caret must not be before end of command word
        assertEquals(Collections.emptyList(), parser.autocomplete(model, "ban", 1));
        // Caret must not be after end of command word
        assertEquals(Collections.emptyList(), parser.autocomplete(model, "ba ", 3));
        // Command word must not be quoted
        assertEquals(Collections.emptyList(), parser.autocomplete(model, "\"ba\"", 4));
    }

    @Test
    public void autocomplete_caretAfterValidCommandName_autocompletesSubcommand() {
        final List<String> suggestions = Arrays.asList("apple", "pineapple");
        final Parser<Command> subparser = mockParser();
        Mockito.when(subparser.autocomplete(model, " ", 1)).thenReturn(suggestions);
        parser.putSubcommand("banana", subparser);
        assertEquals(suggestions, parser.autocomplete(model, "banana ", 7));
    }

    @SuppressWarnings("unchecked")
    private Parser<Command> mockParser() {
        return Mockito.mock(Parser.class);
    }

}
