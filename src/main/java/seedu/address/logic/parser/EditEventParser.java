package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.EditEventCommand;
import seedu.address.logic.commands.IncorrectCommand;

public class EditEventParser {
    private static final Pattern CMD_PATTERN = Pattern.compile("^(?<index>\\d+)"
                                                               + "(\\s+sd-(?<newStartDate>[^\\s]+))?"
                                                               + "(\\s+st-(?<newStartTime>[^\\s]+))?"
                                                               + "(\\s+ed-(?<newEndDate>[^\\s]+))?"
                                                               + "(\\s+et-(?<newEndTime>[^\\s]+))?"
                                                               + "(\\s+n-(?<newName>.+))?"
                                                               + "$");

    private final LocalDateTime referenceDateTime;

    public EditEventParser() {
        this(null);
    }

    public EditEventParser(LocalDateTime referenceDateTime) {
        this.referenceDateTime = referenceDateTime;
    }

    public Command parse(String args) {
        final Matcher matcher = CMD_PATTERN.matcher(args.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditEventCommand.MESSAGE_USAGE));
        }

        final String indexString = matcher.group("index").trim();
        if (!StringUtil.isUnsignedInteger(indexString)) {
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditEventCommand.MESSAGE_USAGE));
        }
        final int index = Integer.parseInt(indexString);

        final DateTimeParser parser = referenceDateTime != null ? new DateTimeParser(referenceDateTime)
                                                                : new DateTimeParser();
        LocalDate newStartDate = null;
        LocalTime newStartTime = null;
        LocalDate newEndDate = null;
        LocalTime newEndTime = null;
        String newName = matcher.group("newName");
        try {
            if (matcher.group("newStartDate") != null) {
                newStartDate = parser.parseDate(matcher.group("newStartDate"));
            }
            if (matcher.group("newStartTime") != null) {
                newStartTime = parser.parseTime(matcher.group("newStartTime"));
            }
            if (matcher.group("newEndDate") != null) {
                newEndDate = parser.parseDate(matcher.group("newEndDate"));
            }
            if (matcher.group("newEndTime") != null) {
                newEndTime = parser.parseTime(matcher.group("newEndTime"));
            }
        } catch (IllegalValueException e) {
            return new IncorrectCommand(e.getMessage());
        }

        try {
            return new EditEventCommand(index, newName, newStartDate, newStartTime, newEndDate, newEndTime);
        } catch (IllegalValueException e) {
            return new IncorrectCommand(e.getMessage());
        }
    }

}
