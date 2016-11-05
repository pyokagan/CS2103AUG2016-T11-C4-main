package seedu.address.logic.parser;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import seedu.address.commons.util.SubstringRange;
import seedu.address.logic.commands.Command;

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

}
