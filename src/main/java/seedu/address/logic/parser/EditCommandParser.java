package seedu.address.logic.parser;

import java.time.LocalDateTime;
import java.util.Optional;

import seedu.address.logic.commands.Command;

public class EditCommandParser implements Parser<Command> {

    private final OverloadParser<Command> overloadParser;

    public EditCommandParser() {
        this(Optional.empty());
    }

    public EditCommandParser(LocalDateTime referenceDateTime) {
        this(Optional.of(referenceDateTime));
    }

    public EditCommandParser(Optional<LocalDateTime> referenceDateTime) {
        overloadParser = new OverloadParser<Command>()
                            .addParser("Edit an event", new EditEventParser(referenceDateTime))
                            .addParser("Edit a deadline", new EditDeadlineParser(referenceDateTime))
                            .addParser("Edit a floating task", new EditFloatingTaskParser());
    }

    @Override
    public Command parse(String str) throws ParseException {
        return overloadParser.parse(str);
    }

}
