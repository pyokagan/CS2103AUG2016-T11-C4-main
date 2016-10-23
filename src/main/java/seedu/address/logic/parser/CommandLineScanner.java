package seedu.address.logic.parser;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.common.base.MoreObjects;

import seedu.address.commons.util.SubstringRange;

/**
 * Tokenizes command line strings made up of zero or more of the following:
 *
 * <ul>
 * <li>Quoted strings, which are surrounded by quotes (e.g. <code>"quoted string"</code>).
 * Use backslashes to embed quotes inside the string. (e.g. <code>"escaped \" backslash"</code>).</li>
 * <li>Unquoted strings, which are surrounded by word boundaries. (e.g. <code>word</code> is a single
 * unquoted string, while <code>two words</code> are two unquoted strings.</li>
 * <li>Arguments, which are either quoted or unquoted strings that must come before flags.</li>
 * <li>A single rest argument, which is either a quoted string or multiple unquoted strings that come before
 * flags.</li>
 * <li>Flags, which are strings which have prefix which belong to the list of <code>flagPrefixes</code></li>
 * </ul>
 */
public class CommandLineScanner {

    /** Pattern for parsing unquoted strings */
    private static Pattern PAT_UNQUOTED = Pattern.compile("^(?<unquotedString>[^\\s]+)");

    /** Pattern for parsing quoted strings */
    private static Pattern PAT_QUOTED = Pattern.compile("^\"(?<quotedString>(?:\\\\\"|[^\"])*)\"");

    private final String input;

    private int inputPosition;

    private final List<String> flagPrefixes;

    public CommandLineScanner(String input, Collection<String> flagPrefixes) {
        assert input != null;
        this.input = input;
        this.flagPrefixes = new ArrayList<>(flagPrefixes);
    }

    public CommandLineScanner(String input) {
        this(input, Collections.emptyList());
    }

    public CommandLineScanner(CommandLineScanner toBeCopied) {
        this(toBeCopied.input, toBeCopied.flagPrefixes);
        this.inputPosition = toBeCopied.inputPosition;
    }

    public String getInput() {
        return input;
    }

    public Collection<String> getFlagPrefixes() {
        return Collections.unmodifiableList(flagPrefixes);
    }

    public int getInputPosition() {
        return inputPosition;
    }

    /**
     * Returns the substring of input that has not been processed yet.
     */
    public String getRemainingInput() {
        return input.substring(inputPosition);
    }

    /**
     * Attempts to match the string with a flag prefix, and returns the first successfully matched one.
     */
    private Optional<String> matchFlagPrefix(String str) {
        for (String flagPrefix : flagPrefixes) {
            if (str.startsWith(flagPrefix)) {
                return Optional.of(flagPrefix);
            }
        }
        return Optional.empty();
    }

    /**
     * Skip over whitespace in the input, if any.
     */
    public void skipWhitespace() {
        while (inputPosition < input.length() && Character.isWhitespace(input.charAt(inputPosition))) {
            inputPosition++;
        }
    }

    /**
     * Returns the next unquoted string (if any).
     */
    public Optional<String> nextUnquotedString() {
        skipWhitespace();
        final Matcher matcher = PAT_UNQUOTED.matcher(input);
        matcher.region(inputPosition, input.length());
        if (matcher.find()) {
            inputPosition = matcher.end();
            return Optional.of(matcher.group("unquotedString"));
        } else {
            return Optional.empty();
        }
    }

    /**
     * Returns the next unquoted string (if any), but does not advance the input position.
     */
    public Optional<String> peekNextUnquotedString() {
        return new CommandLineScanner(this).nextUnquotedString();
    }

    /**
     * Returns the next quoted string (if any).
     */
    public Optional<String> nextQuotedString() {
        skipWhitespace();
        final Matcher matcher = PAT_QUOTED.matcher(input);
        matcher.region(inputPosition, input.length());
        if (matcher.find()) {
            inputPosition = matcher.end();
            return Optional.of(unescapeQuotes(matcher.group("quotedString")));
        } else {
            return Optional.empty();
        }
    }

    private String unescapeQuotes(String str) {
        return str.replaceAll("\\\\\"", "\"");
    }

    /**
     * Returns the next quoted string (if any), but does not advance the input position.
     */
    public Optional<String> peekNextQuotedString() {
        return new CommandLineScanner(this).nextQuotedString();
    }

    /**
     * Returns the next argument (if any).
     */
    public Optional<Argument> nextArgument() {
        Optional<String> value;
        skipWhitespace();
        int startInputPosition = inputPosition;
        if ((value = nextQuotedString()).isPresent()) {
            return Optional.of(new Argument(value.get(), startInputPosition, inputPosition, true));
        } else if ((value = nextUnquotedString()).isPresent()) {
            if (matchFlagPrefix(value.get()).isPresent()) {
                return Optional.empty();
            } else {
                return Optional.of(new Argument(value.get(), startInputPosition, inputPosition, false));
            }
        } else {
            return Optional.empty();
        }
    }

    /**
     * Returns the next argument (if any), but does not advance the input position.
     */
    public Optional<Argument> peekNextArgument() {
        return new CommandLineScanner(this).nextArgument();
    }

    /**
     * Returns the "rest argument", which is either:
     * 1. A single quoted argument.
     * 2. Or the reset of the input until the next flag as a single argument.
     */
    public Argument nextRestArgument() {
        skipWhitespace();
        int startPosition = inputPosition;
        Optional<String> quotedString = nextQuotedString();
        if (quotedString.isPresent()) {
            return new Argument(quotedString.get(), startPosition, inputPosition, true);
        } else {
            while (!matchFlagPrefix(getRemainingInput()).isPresent()) {
                if (!nextUnquotedString().isPresent()) {
                    break;
                }
                skipWhitespace();
            }
            String value = input.substring(startPosition, inputPosition);
            String trimmedValue = value.trim();
            int endPosition = inputPosition - (value.length() - trimmedValue.length());
            return new Argument(trimmedValue, startPosition, endPosition, false);
        }
    }

    /**
     * Returns the next flag (if any).
     * @throws ParseException if the input cannot be parsed as a flag
     */
    public Optional<Flag> nextFlag() throws ParseException {
        skipWhitespace();
        if (getRemainingInput().isEmpty()) {
            return Optional.empty();
        }
        Optional<String> flagPrefix = matchFlagPrefix(getRemainingInput());
        if (flagPrefix.isPresent()) {
            inputPosition += flagPrefix.get().length();
            if (peekNextQuotedString().isPresent()) {
                skipWhitespace();
                int startPosition = inputPosition;
                String value = nextQuotedString().get();
                return Optional.of(new Flag(flagPrefix.get(), value, startPosition, inputPosition, true));
            } else {
                Argument rest = nextRestArgument();
                return Optional.of(new Flag(flagPrefix.get(), rest.value, rest.range, false));
            }
        } else {
            String flag = peekNextUnquotedString().orElse("");
            throw new ParseException("not a flag: " + flag, new SubstringRange(inputPosition, input.length()));
        }
    }

    public static final class Argument {
        public final String value;
        public final SubstringRange range;
        public final boolean quoted;

        public Argument(String value, SubstringRange range, boolean quoted) {
            this.value = value;
            this.range = range;
            this.quoted = quoted;
        }

        public Argument(String value, int start, int end, boolean quoted) {
            this(value, new SubstringRange(start, end), quoted);
        }

        @Override
        public boolean equals(Object other) {
            return other == this
                    || (other instanceof Argument
                    && value.equals(((Argument)other).value)
                    && range.equals(((Argument)other).range)
                    && quoted == ((Argument)other).quoted
                    );
        }

        @Override
        public String toString() {
            return MoreObjects.toStringHelper(this)
                    .add("value", value)
                    .add("range", range)
                    .add("quoted", quoted)
                    .toString();
        }
    }

    public static final class Flag {
        public final String prefix;
        public final String value;
        public final SubstringRange range;
        public final boolean quoted;

        public Flag(String prefix, String value, SubstringRange range, boolean quoted) {
            this.prefix = prefix;
            this.value = value;
            this.range = range;
            this.quoted = quoted;
        }

        public Flag(String prefix, String value, int start, int end, boolean quoted) {
            this(prefix, value, new SubstringRange(start, end), quoted);
        }

        @Override
        public boolean equals(Object other) {
            return other == this
                    || (other instanceof Flag
                    && prefix.equals(((Flag)other).prefix)
                    && value.equals(((Flag)other).value)
                    && range.equals(((Flag)other).range)
                    && quoted == ((Flag)other).quoted
                    );
        }

        @Override
        public String toString() {
            return MoreObjects.toStringHelper(this)
                    .add("prefix", prefix)
                    .add("value", value)
                    .add("range", range)
                    .add("quoted", quoted)
                    .toString();
        }

    }

}
