package seedu.address.logic.parser;

import java.time.LocalDateTime;
import java.util.Optional;

import seedu.address.logic.commands.AddTaskCommand;

public class AddTaskParser implements Parser<AddTaskCommand> {

    private final OverloadParser<AddTaskCommand> overloadParser;

    public AddTaskParser() {
        this(Optional.empty());
    }

    public AddTaskParser(LocalDateTime referenceDateTime) {
        this(Optional.of(referenceDateTime));
    }

    public AddTaskParser(Optional<LocalDateTime> referenceDateTime) {
        overloadParser = new OverloadParser<AddTaskCommand>()
                            .addParser("Add an event", new AddEventParser(referenceDateTime))
                            .addParser("Add a deadline", new AddDeadlineParser(referenceDateTime))
                            .addParser("Add a floating task", new AddFloatingTaskParser());
    }

    @Override
    public AddTaskCommand parse(String str) throws ParseException {
        return overloadParser.parse(str);
    }
}
