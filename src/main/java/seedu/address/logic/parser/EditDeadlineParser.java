package seedu.address.logic.parser;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

import seedu.address.logic.commands.EditDeadlineCommand;
import seedu.address.logic.parser.CommandLineParser.Argument;
import seedu.address.logic.parser.CommandLineParser.OptionalFlag;
import seedu.address.model.task.Name;

public class EditDeadlineParser implements Parser<EditDeadlineCommand> {

    private final Argument<Integer> indexArg = new Argument<>("INDEX", new IndexParser());
    private final OptionalFlag<LocalDate> newDateFlag = new OptionalFlag<>("dd-", "NEW_DUE_DATE", new DateParser());
    private final OptionalFlag<LocalTime> newTimeFlag = new OptionalFlag<>("dt-", "NEW_DUE_TIME", new TimeParser());
    private final OptionalFlag<Name> newNameFlag = new OptionalFlag<>("n-", "NEW_NAME", new NameParser());
    private final CommandLineParser cmdParser = new CommandLineParser()
                                                        .addArgument(indexArg)
                                                        .putFlag(newDateFlag)
                                                        .putFlag(newTimeFlag)
                                                        .putFlag(newNameFlag);

    private final Optional<LocalDateTime> referenceDateTime;

    public EditDeadlineParser() {
        this(Optional.empty());
    }

    public EditDeadlineParser(LocalDateTime referenceDateTime) {
        this(Optional.of(referenceDateTime));
    }

    public EditDeadlineParser(Optional<LocalDateTime> referenceDateTime) {
        this.referenceDateTime = referenceDateTime;
    }

    @Override
    public EditDeadlineCommand parse(String str) throws ParseException {
        // Tell date/time parsers the current time
        final LocalDateTime now = referenceDateTime.orElse(LocalDateTime.now());
        newDateFlag.setParser(new DateParser(now.toLocalDate()));
        newTimeFlag.setParser(new TimeParser(now.toLocalTime()));

        cmdParser.parse(str);

        return new EditDeadlineCommand(indexArg.getValue(), newNameFlag.getValue(), newDateFlag.getValue(),
                                       newTimeFlag.getValue());
    }

}
