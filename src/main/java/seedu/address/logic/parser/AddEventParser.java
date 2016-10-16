package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddEventCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.IncorrectCommand;

public class AddEventParser {

    private static final Pattern ARG_PATTERN = Pattern.compile("\\s*\"(?<quotedArg>[^\"]+)\"\\s*|\\s*(?<unquotedArg>[^\\s]+)\\s*");

    private final Command incorrectCommand = new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddEventCommand.MESSAGE_USAGE));

    private final LocalDateTime referenceDateTime;

    public AddEventParser() {
        this(null);
    }

    public AddEventParser(LocalDateTime referenceDateTime) {
        this.referenceDateTime = referenceDateTime;
    }

    public Command parse(String str) {
        final ParseResult args;
        try {
            args = parseArguments(str);
        } catch (IllegalValueException e) {
            return incorrectCommand;
        }

        // There must be at least one of { startDate, startTime }, and at least one of { endDate, endTime }.
        if ((args.startDate == null && args.startTime == null) || (args.endDate == null && args.endTime == null)) {
            return incorrectCommand;
        }

        final DateTimeParser parser = referenceDateTime != null ? new DateTimeParser(referenceDateTime)
                                                                : new DateTimeParser();
        try {
            final LocalDate startDate = args.startDate != null ? parser.parseDate(args.startDate)
                                                                : parser.getReferenceDateTime().toLocalDate();
            final LocalTime startTime = args.startTime != null ? parser.parseTime(args.startTime)
                                                                : LocalTime.of(0, 0);
            final LocalDate endDate = args.endDate != null ? parser.parseDate(args.endDate) : startDate;
            final LocalTime endTime = args.endTime != null ? parser.parseTime(args.endTime) : LocalTime.of(23, 59);
            return new AddEventCommand(args.name,
                                       LocalDateTime.of(startDate, startTime),
                                       LocalDateTime.of(endDate, endTime));
        } catch (IllegalValueException e) {
            return new IncorrectCommand(e.getMessage());
        }
    }

    private static class ParseResult {
        String name;
        String startDate;
        String startTime;
        String endDate;
        String endTime;
    }

    private static ParseResult parseArguments(String str) throws IllegalValueException {
        final ParseResult result = new ParseResult();
        final ArrayList<String> args = splitArgs(str);

        // name
        if (args.isEmpty()) {
            throw new IllegalValueException("expected name");
        }
        result.name = args.remove(0);

        // startDate (optional)
        if (args.isEmpty()) {
            return result;
        }
        if (isDate(args.get(0))) {
            result.startDate = args.remove(0);
        }

        // startTime (optional)
        if (args.isEmpty()) {
            return result;
        }
        if (isTime(args.get(0))) {
            result.startTime = args.remove(0);
        }

        // Check Keyword "to"
        if (args.isEmpty()) {
            throw new IllegalValueException("expected ending time or ending date");
        }
        if (isKeywordTo(args.get(0))) {
            args.remove(0);
        } else {
            throw new IllegalValueException("expected keyword \"to\"");
        }

        // endDate (optional)
        if (args.isEmpty()) {
            return result;
        }
        if (isDate(args.get(0))) {
            result.endDate = args.remove(0);
        }

        // endTime (optional)
        if (args.isEmpty()) {
            return result;
        }
        if (isTime(args.get(0))) {
            result.endTime = args.remove(0);
        }

        if (!args.isEmpty()) {
            throw new IllegalValueException("too many arguments");
        }
        return result;
    }

    private static ArrayList<String> splitArgs(String str) {
        final Matcher matcher = ARG_PATTERN.matcher(str);
        final ArrayList<String> args = new ArrayList<>();
        int start = 0;
        while (matcher.find(start)) {
            args.add(matcher.group("quotedArg") != null ? matcher.group("quotedArg")
                                                        : matcher.group("unquotedArg"));
            start = matcher.end();
        }
        return args;
    }

    private static boolean isDate(String str) {
        final DateTimeParser parser = new DateTimeParser();
        try {
            parser.parseDate(str);
            return true;
        } catch (IllegalValueException e) {
            return false;
        }
    }

    private static boolean isTime(String str) {
        final DateTimeParser parser = new DateTimeParser();
        try {
            parser.parseTime(str);
            return true;
        } catch (IllegalValueException e) {
            return false;
        }
    }

    private static boolean isKeywordTo(String str) {
        return str.equals("to");
    }

}
