package seedu.address.logic.parser;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.SubstringRange;
import seedu.address.model.ReadOnlyModel;

/**
 * A utility class that uses a {@link CommandLineScanner} to parse a string, breaking it down into its
 * individual {@link CommandLineParser.Argument} and {@link CommandLineParser.Flag}
 */
public class CommandLineParser {

    private static String MSG_ARG_NOT_PROVIDED = "Required argument not provided: %s";
    private static String MSG_FLAG_NOT_PROVIDED = "Required flag not provided: %s%s";
    private static String MSG_EXPECTED_KEYWORD = "Expected keyword: %s";
    private static String MSG_TOO_MANY_ARGS = "Too many arguments";
    private static String MSG_FLAG_MULTIPLE = "%s specified multiple times";

    private final List<ArgumentParser> arguments = new ArrayList<>();
    private final Map<String, FlagParser> flags = new HashMap<>();

    public CommandLineParser addArgument(ArgumentParser argument) {
        arguments.add(argument);
        return this;
    }

    public CommandLineParser addArgument(String keyword) {
        Parser<Object> parser = str -> {
            if (str.equals(keyword)) {
                return new Object(); // dummy object
            } else {
                SubstringRange range = new SubstringRange(0, str.length());
                throw new ParseException(String.format(MSG_EXPECTED_KEYWORD, keyword), range);
            }
        };
        arguments.add(new Argument<Object>(keyword, parser));
        return this;
    }

    public CommandLineParser putFlag(FlagParser flag) {
        flags.put(flag.getPrefix(), flag);
        return this;
    }

    private Collection<String> getPrefixesForFlags() {
        return flags.values().stream().map(flag -> flag.getPrefix()).collect(Collectors.toList());
    }

    private void reset() {
        // Reset all argument parsers
        for (ArgumentParser argument : arguments) {
            argument.reset();
        }

        // Reset all flag parsers
        for (FlagParser flag : flags.values()) {
            flag.reset();
        }
    }

    public void parse(String str) throws ParseException {
        reset();

        final CommandLineScanner scanner = new CommandLineScanner(str, getPrefixesForFlags());

        // Parse arguments
        for (ArgumentParser argument : arguments) {
            argument.parse(scanner);
        }

        if (!flags.isEmpty()) {
            // Parse flags
            Optional<CommandLineScanner.Flag> scannerFlag;
            while ((scannerFlag = scanner.nextFlag()).isPresent()) {
                FlagParser flag = flags.get(scannerFlag.get().prefix);
                assert flag != null; // CommandLineScanner should only return flags it recognizes
                flag.parseFlag(scannerFlag.get());
            }

            // Any non-optional flags that are not specified?
            for (FlagParser flag : flags.values()) {
                if (!flag.isOptional() && !flag.isPresent()) {
                    throw new ParseException(String.format(MSG_FLAG_NOT_PROVIDED, flag.getPrefix(), flag.getName()));
                }
            }
        } else {
            // Do we have any extra arguments that should not be there?
            scanner.skipWhitespace();
            if (!scanner.getRemainingInput().isEmpty()) {
                SubstringRange range = new SubstringRange(scanner.getInputPosition(), scanner.getInput().length());
                throw new ParseException(MSG_TOO_MANY_ARGS, range);
            }
        }
    }

    public List<String> autocomplete(ReadOnlyModel model, String str, int pos) {
        reset();

        final CommandLineScanner scanner = new CommandLineScanner(str, getPrefixesForFlags());

        // Autocomplete arguments
        for (ArgumentParser argument : arguments) {
            final List<String> candidates = argument.autocomplete(model, scanner, pos);
            if (scanner.getInputPosition() >= pos) {
                return candidates;
            }
        }

        // Autocomplete flags
        try {
            Optional<CommandLineScanner.Flag> scannerFlag;
            while ((scannerFlag = scanner.nextFlag()).isPresent()) {
                final FlagParser flag = flags.get(scannerFlag.get().prefix);
                final List<String> candidates = flag.autocomplete(model, scannerFlag.get(), pos);
                if (scanner.getInputPosition() >= pos) {
                    return candidates;
                }
            }
        } catch (ParseException e) {
            // The CommandLineScanner found an unrecognized flag prefix and so can't continue any more.
            return Collections.emptyList();
        }

        return Collections.emptyList();
    }

    public interface ArgumentParser {
        /** Name of the argument. */
        String getName();

        /** Resets the parser */
        default void reset() {}

        /**
         * Scans and parses in input from the provided {@link CommandLineScanner}.
         * @throws ParseException of there was an error parsing the input. The input position of the
         * CommandLineScanner must NOT be modified in this case.
         */
        void parse(CommandLineScanner scanner) throws ParseException;

        /**
         * Autocompletes the input from the provided {@link CommandLineScanner}, with the "caret position"
         * at <code>pos</code>.
         * @return the list of autocomplete suggestions if pos is within the range of the input handled by
         * this argument.
         */
        List<String> autocomplete(ReadOnlyModel model, CommandLineScanner scanner, int pos);
    }

    /**
     * An command line argument parser for a single argument of type T.
     */
    public static class Argument<T> implements ArgumentParser {
        private final String name;
        private Parser<? extends T> parser;
        private boolean present;
        private T value;
        private SubstringRange range;

        public Argument(String name, Parser<? extends T> parser) {
            assert !CollectionUtil.isAnyNull(name, parser);
            this.name = name;
            this.parser = parser;
        }

        @Override
        public String getName() {
            return name;
        }

        public void setParser(Parser<? extends T> parser) {
            assert parser != null;
            this.parser = parser;
        }

        public boolean isPresent() {
            return present;
        }

        public void set(T value, SubstringRange range) {
            assert !CollectionUtil.isAnyNull(value, range);
            this.value = value;
            this.range = range;
            present = true;
        }

        @Override
        public void reset() {
            present = false;
            value = null;
            range = null;
        }

        public T getValue() {
            assert present;
            return value;
        }

        public SubstringRange getRange() {
            assert present;
            return range;
        }

        /**
         * Parses the provided {@link CommandLineScanner.Argument}.
         */
        public void parse(CommandLineScanner.Argument arg) throws ParseException {
            final T value;
            try {
                value = parser.parse(arg.value);
            } catch (ParseException e) {
                final ParseExceptionBuilder builder = new ParseExceptionBuilder(e);
                builder.prependMessage(getName() + ": ");
                if (arg.quoted) {
                    builder.clearRanges()
                            .addRange(arg.range);
                } else {
                    builder.indentRanges(arg.range.getStart());
                }
                throw builder.build();
            }
            set(value, arg.range);
        }

        @Override
        public void parse(CommandLineScanner scanner) throws ParseException {
            Optional<CommandLineScanner.Argument> arg = scanner.peekNextArgument();
            if (!arg.isPresent()) {
                SubstringRange range = new SubstringRange(scanner.getInputPosition(), scanner.getInput().length());
                throw new ParseException(String.format(MSG_ARG_NOT_PROVIDED, name), range);
            }
            parse(arg.get());
            scanner.nextArgument();
        }

        /**
         * Autocompletes the input from the provided {@link CommandLineScanner.Argument}, with the
         * "caret position" at <code>pos</code>.
         */
        public List<String> autocomplete(ReadOnlyModel model, CommandLineScanner.Argument arg, int pos) {
            if (arg.quoted) {
                // TODO: we don't support quoted arguments yet because autocompleting them will be tricky
                // (e.g. need to requote the arguments)
                return Collections.emptyList();
            } else if (arg.range.contains(pos)) {
                return parser.autocomplete(model, arg.value, pos - arg.range.getStart());
            } else if (pos > arg.range.getEnd()) {
                // Attempt to parse this argument, in case other arguments/flags need this argument as
                // context for autocompletion
                try {
                    parse(arg);
                } catch (ParseException e) {
                    // This is okay as the validity of this argument may not be required for a successful
                    // autocomplete.
                }
            }
            return Collections.emptyList();
        }

        @Override
        public List<String> autocomplete(ReadOnlyModel model, CommandLineScanner scanner, int pos) {
            Optional<CommandLineScanner.Argument> arg = scanner.nextArgument();
            if (!arg.isPresent() && pos <= scanner.getInputPosition()) {
                // Caret position is within the blank space between the arguments and the flags sections
                return parser.autocomplete(model, "", 0);
            } else if (arg.isPresent()) {
                // Caret position is within the argument
                return autocomplete(model, arg.get(), pos);
            } else {
                // Caret is within the "flags" section
                return Collections.emptyList();
            }
        }
    }

    /**
     * A command line argument parser for parsing a rest argument of type T.
     */
    public static class RestArgument<T> extends Argument<T> {
        public RestArgument(String name, Parser<? extends T> parser) {
            super(name, parser);
        }

        @Override
        public void parse(CommandLineScanner scanner) throws ParseException {
            final CommandLineScanner.Argument restArgument = scanner.peekNextRestArgument();
            parse(restArgument);
            scanner.nextRestArgument();
        }

        @Override
        public List<String> autocomplete(ReadOnlyModel model, CommandLineScanner scanner, int pos) {
            return autocomplete(model, scanner.nextRestArgument(), pos);
        }
    }

    /**
     * A command line argument parser for parsing a variable number of arguments of type T.
     */
    public static class ListArgument<T> implements ArgumentParser {
        private final String name;
        private Parser<? extends T> parser;
        private final ArrayList<Argument<T>> arguments = new ArrayList<>();
        private SubstringRange range;

        public ListArgument(String name, Parser<? extends T> parser) {
            assert !CollectionUtil.isAnyNull(name, parser);
            this.name = name;
            this.parser = parser;
        }

        @Override
        public String getName() {
            return name;
        }

        public void setParser(Parser<? extends T> parser) {
            assert parser != null;
            this.parser = parser;
        }

        @Override
        public void reset() {
            arguments.clear();
            range = null;
        }

        public List<T> getValues() {
            return arguments.stream()
                            .map(arg -> arg.getValue())
                            .collect(Collectors.toList());
        }

        public SubstringRange getRange() {
            assert range != null;
            return range;
        }

        @Override
        public void parse(CommandLineScanner scanner) throws ParseException {
            scanner.skipWhitespace();
            range = new SubstringRange(scanner.getInputPosition(), scanner.getInputPosition());
            while (scanner.peekNextArgument().isPresent()) {
                final Argument<T> argument = new Argument<>(name + "[" + arguments.size() + "]", parser);
                argument.parse(scanner);
                arguments.add(argument);
                range = new SubstringRange(range.getStart(), scanner.getInputPosition());
            }
        }

        @Override
        public List<String> autocomplete(ReadOnlyModel model, CommandLineScanner scanner, int pos) {
            while (scanner.nextArgument().isPresent()) {
                final Argument<T> argument = new Argument<>(name + "[" + arguments.size() + "]", parser);
                final List<String> candidates = argument.autocomplete(model, scanner, pos);
                if (scanner.getInputPosition() >= pos) {
                    return candidates;
                }
            }
            return Collections.emptyList();
        }

    }

    public interface FlagParser {
        /** Returns the flag prefix */
        String getPrefix();

        /** Returns the flag name */
        String getName();

        /** Resets the flag parsing state */
        default void reset() {}

        /** Returns true if the flag is optional */
        boolean isOptional();

        /** Returns true if the flag has a value */
        boolean isPresent();

        /** Parses the {@link CommandLineScanner.Flag} */
        void parseFlag(CommandLineScanner.Flag flag) throws ParseException;

        /**
         * Autocompletes the {@link CommandLineScanner.Flag}, with the "caret position" at <code>pos</code>.
         * <code>pos</code> is guaranteed to be within the range of the flag.
         */
        List<String> autocomplete(ReadOnlyModel model, CommandLineScanner.Flag flag, int pos);
    }

    private abstract static class BaseFlag<T> implements FlagParser {

        private final String prefix;
        private final String name;
        private Parser<? extends T> parser;
        protected T value;
        protected boolean present;
        protected SubstringRange range;

        protected BaseFlag(String prefix, String name, Parser<? extends T> parser) {
            assert !CollectionUtil.isAnyNull(prefix, name, parser);
            this.prefix = prefix;
            this.name = name;
            this.parser = parser;
        }

        @Override
        public String getPrefix() {
            return prefix;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public boolean isPresent() {
            return present;
        }

        public void setParser(Parser<? extends T> parser) {
            assert parser != null;
            this.parser = parser;
        }

        @Override
        public void reset() {
            present = false;
        }

        @Override
        public void parseFlag(CommandLineScanner.Flag flag) throws ParseException {
            assert flag.prefix.equals(prefix);
            if (present) {
                throw new ParseException(String.format(MSG_FLAG_MULTIPLE, prefix), flag.range);
            }
            try {
                value = parser.parse(flag.value);
            } catch (ParseException e) {
                final ParseExceptionBuilder builder = new ParseExceptionBuilder(e);
                builder.prependMessage(getName() + ": ");
                if (flag.quoted) {
                    builder.clearRanges().addRange(flag.range);
                } else {
                    builder.indentRanges(flag.range.getStart());
                }
                throw builder.build();
            }
            present = true;
            range = flag.range;
        }

        @Override
        public List<String> autocomplete(ReadOnlyModel model, CommandLineScanner.Flag flag, int pos) {
            if (flag.quoted) {
                // TODO: we don't support quoted arguments yet because autocompleting them will be tricky
                // (e.g. need to requote the arguments)
                return Collections.emptyList();
            }
            return parser.autocomplete(model, flag.value, pos - flag.range.getStart());
        }
    }

    public static class Flag<T> extends BaseFlag<T> {

        public Flag(String prefix, String name, Parser<? extends T> parser) {
            super(prefix, name, parser);
        }

        public T getValue() {
            assert present;
            return value;
        }

        public SubstringRange getRange() {
            assert present;
            return range;
        }

        @Override
        public boolean isOptional() {
            return false;
        }

    }

    public static class OptionalFlag<T> extends BaseFlag<T> {

        public OptionalFlag(String prefix, String name, Parser<? extends T> parser) {
            super(prefix, name, parser);
        }

        public Optional<T> getValue() {
            return isPresent() ? Optional.of(value) : Optional.empty();
        }

        public Optional<SubstringRange> getRange() {
            return isPresent() ? Optional.of(range) : Optional.empty();
        }

        @Override
        public boolean isOptional() {
            return true;
        }

    }

}
