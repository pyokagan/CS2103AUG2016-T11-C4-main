package seedu.address.logic.parser;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import seedu.address.logic.commands.Command;
import seedu.address.model.ReadOnlyModel;

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

    @Override
    public List<String> autocomplete(ReadOnlyModel model, String input, int pos) {
        return overloadParser.autocomplete(model, input, pos);
    }

}
