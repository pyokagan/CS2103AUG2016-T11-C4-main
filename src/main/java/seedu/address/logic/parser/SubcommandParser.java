package seedu.address.logic.parser;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import seedu.address.commons.util.SubstringRange;
import seedu.address.logic.commands.Command;
import seedu.address.model.ReadOnlyModel;

/**
 * A parser that implements subcommands (selection of parser based on first word of input)
 */
public class SubcommandParser implements Parser<Command> {

    private static final String MSG_NO_COMMAND = "No command name given";

    private static final String MSG_UNKNOWN_COMMAND = "Unknown command: %s";

    private Map<String, Parser<? extends Command>> parsers = new HashMap<>();

    public SubcommandParser putSubcommand(String name, Parser<? extends Command> parser) {
        parsers.put(name, parser);
        return this;
    }

    @Override
    public Command parse(String str) throws ParseException {
        final CommandLineScanner scanner = new CommandLineScanner(str);
        Optional<CommandLineScanner.Argument> subcommandArg = scanner.nextArgument();
        if (!subcommandArg.isPresent()) {
            throw new ParseException(MSG_NO_COMMAND, SubstringRange.of(str));
        }
        if (!parsers.containsKey(subcommandArg.get().value)) {
            throw new ParseException(String.format(MSG_UNKNOWN_COMMAND, subcommandArg.get().value),
                                     subcommandArg.get().range);
        }
        Parser<? extends Command> parser = parsers.get(subcommandArg.get().value);
        try {
            return parser.parse(scanner.getRemainingInput());
        } catch (ParseException e) {
            throw new ParseExceptionBuilder(e)
                        .prependMessage(subcommandArg.get().value + ": ")
                        .indentRanges(scanner.getInputPosition())
                        .build();
        }
    }

    @Override
    public List<String> autocomplete(ReadOnlyModel model, String input, int pos) {
        final CommandLineScanner scanner = new CommandLineScanner(input);
        Optional<CommandLineScanner.Argument> subcommandArg = scanner.nextArgument();
        if (!subcommandArg.isPresent()) {
            return autocompleteCommand(model, "");
        } else if (pos - scanner.getInputPosition() <= 0) {
            // Cursor must be at the end of the command word and word must not be quoted
            if (pos != subcommandArg.get().range.getEnd() || subcommandArg.get().quoted) {
                return Collections.emptyList();
            } else {
                return autocompleteCommand(model, subcommandArg.get().value);
            }
        } else if (parsers.containsKey(subcommandArg.get().value)) {
            final Parser<? extends Command> parser = parsers.get(subcommandArg.get().value);
            return parser.autocomplete(model, scanner.getRemainingInput(), pos - scanner.getInputPosition());
        } else {
            return Collections.emptyList(); // invalid command
        }
    }

    private List<String> autocompleteCommand(ReadOnlyModel model, String input) {
        return parsers.keySet().stream()
                .filter(commandWord -> commandWord.startsWith(input))
                .map(commandWord -> commandWord.substring(input.length()))
                .sorted()
                .collect(Collectors.toList());
    }

}
