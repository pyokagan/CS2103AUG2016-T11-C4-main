package seedu.address.logic.parser;

import java.time.LocalDateTime;
import java.util.Optional;

import seedu.address.logic.commands.ClearFinishedCommand;

public class ClearFinishedParser implements Parser<ClearFinishedCommand> {

    private final CommandLineParser cmdParser = new CommandLineParser()
                                                .addArgument(ClearFinishedCommand.COMMAND_PARAMETER);

    private final Optional<LocalDateTime> referenceDateTime;

    public ClearFinishedParser(Optional<LocalDateTime> referenceDateTime) {
        this.referenceDateTime = referenceDateTime;
    }

    public ClearFinishedParser() {
        this(Optional.empty());
    }

    @Override
    public ClearFinishedCommand parse(String str) throws ParseException {
        cmdParser.parse(str);
        final LocalDateTime now = referenceDateTime.orElse(LocalDateTime.now());
        return new ClearFinishedCommand(now);
    }

}
