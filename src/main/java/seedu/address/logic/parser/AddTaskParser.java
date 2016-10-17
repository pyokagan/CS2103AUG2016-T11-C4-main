package seedu.address.logic.parser;

import java.time.LocalDateTime;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.IncorrectCommand;

public class AddTaskParser {

    private final AddDeadlineParser addDeadlineParser;

    private final AddEventParser addEventParser;

    private final AddFloatingTaskParser addFloatingTaskParser;

    public AddTaskParser() {
        addDeadlineParser = new AddDeadlineParser();
        addEventParser = new AddEventParser();
        addFloatingTaskParser = new AddFloatingTaskParser();
    }

    public AddTaskParser(LocalDateTime referenceDateTime) {
        addDeadlineParser = new AddDeadlineParser(referenceDateTime);
        addEventParser = new AddEventParser(referenceDateTime);
        addFloatingTaskParser = new AddFloatingTaskParser();
    }

    public Command parse(String str) {
        Command cmd;

        cmd = addEventParser.parse(str);
        if (!(cmd instanceof IncorrectCommand)) {
            return cmd;
        }

        cmd = addDeadlineParser.parse(str);
        if (!(cmd instanceof IncorrectCommand)) {
            return cmd;
        }

        return addFloatingTaskParser.parse(str);
    }
}
