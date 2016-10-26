package seedu.address.logic.parser;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

import seedu.address.logic.commands.EditEventCommand;
import seedu.address.logic.parser.CommandLineParser.Argument;
import seedu.address.logic.parser.CommandLineParser.OptionalFlag;
import seedu.address.model.task.Name;

public class EditEventParser implements Parser<EditEventCommand> {

    private final Argument<Integer> indexArg = new Argument<>("INDEX", new IndexParser());
    private final OptionalFlag<LocalDate> newStartDateFlag = new OptionalFlag<>("sd-", "NEW_START_DATE", new DateParser());
    private final OptionalFlag<LocalTime> newStartTimeFlag = new OptionalFlag<>("st-", "NEW_START_TIME", new TimeParser());
    private final OptionalFlag<LocalDate> newEndDateFlag = new OptionalFlag<>("ed-", "NEW_END_DATE", new DateParser());
    private final OptionalFlag<LocalTime> newEndTimeFlag = new OptionalFlag<>("et-", "NEW_END_TIME", new TimeParser());
    private final OptionalFlag<Name> newNameFlag = new OptionalFlag<>("n-", "NEW_NAME", new NameParser());
    private final CommandLineParser cmdParser = new CommandLineParser()
                                                        .addArgument(indexArg)
                                                        .putFlag(newStartDateFlag)
                                                        .putFlag(newStartTimeFlag)
                                                        .putFlag(newEndDateFlag)
                                                        .putFlag(newEndTimeFlag)
                                                        .putFlag(newNameFlag);

    private final Optional<LocalDateTime> referenceDateTime;

    public EditEventParser() {
        this(Optional.empty());
    }

    public EditEventParser(LocalDateTime referenceDateTime) {
        this(Optional.of(referenceDateTime));
    }

    public EditEventParser(Optional<LocalDateTime> referenceDateTime) {
        this.referenceDateTime = referenceDateTime;
    }

    @Override
    public EditEventCommand parse(String str) throws ParseException {
        // Tell date/time parsers the current time
        final LocalDateTime now = referenceDateTime.orElse(LocalDateTime.now());
        newStartDateFlag.setParser(new DateParser(now.toLocalDate()));
        newStartTimeFlag.setParser(new TimeParser(now.toLocalTime()));
        newEndDateFlag.setParser(new DateParser(now.toLocalDate()));
        newEndTimeFlag.setParser(new TimeParser(now.toLocalTime()));

        cmdParser.parse(str);

        return new EditEventCommand(indexArg.getValue(), newNameFlag.getValue(), newStartDateFlag.getValue(),
                                    newStartTimeFlag.getValue(), newEndDateFlag.getValue(),
                                    newEndTimeFlag.getValue());
    }

}
