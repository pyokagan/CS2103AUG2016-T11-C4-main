package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.time.LocalDateTime;
import java.util.Optional;

import seedu.address.logic.commands.AddDeadlineCommand;
import seedu.address.logic.commands.AddEventCommand;
import seedu.address.logic.commands.AddFloatingTaskCommand;
import seedu.address.logic.commands.AddTaskCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.IncorrectCommand;

public class AddTaskParser {

    private final AddDeadlineParser addDeadlineParser;

    private final AddEventParser addEventParser;

    private final AddFloatingTaskParser addFloatingTaskParser;

    private final Command incorrectCommand =
            new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            AddTaskCommand.MESSAGE_USAGE
            + AddDeadlineCommand.MESSAGE_USAGE
            + AddEventCommand.MESSAGE_USAGE
            + AddFloatingTaskCommand.MESSAGE_USAGE));

    public AddTaskParser() {
        this(Optional.empty());
    }

    public AddTaskParser(LocalDateTime referenceDateTime) {
        this(Optional.of(referenceDateTime));
    }

    public AddTaskParser(Optional<LocalDateTime> referenceDateTime) {
        addDeadlineParser = new AddDeadlineParser(referenceDateTime);
        addEventParser = new AddEventParser(referenceDateTime);
        addFloatingTaskParser = new AddFloatingTaskParser();
    }

    public Command parse(String str) {
        try {
            return addEventParser.parse(str);
        } catch (ParseException e) {
            // do nothing
        }

        try {
            return addDeadlineParser.parse(str);
        } catch (ParseException e) {
            // do nothing
        }

        try {
            return addFloatingTaskParser.parse(str);
        } catch (ParseException e) {
            // do nothing
        }

        return incorrectCommand;
    }
}
