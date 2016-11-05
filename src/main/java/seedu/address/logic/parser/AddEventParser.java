package seedu.address.logic.parser;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.time.LocalDateTimeDuration;
import seedu.address.logic.commands.AddEventCommand;
import seedu.address.logic.parser.CommandLineParser.Argument;
import seedu.address.model.task.EventTask;
import seedu.address.model.task.Name;

/**
 * Parser for {@link AddEventCommand}
 */
public class AddEventParser implements Parser<AddEventCommand> {

    private final Argument<Name> nameArg = new Argument<>("NAME", new NameParser());
    private final DateTimeArgument startArg = new DateTimeArgument("START_DATE", "END_DATE");
    private final DateTimeArgument endArg = new DateTimeArgument("END_DATE", "END_TIME");
    private final CommandLineParser cmdParser = new CommandLineParser()
                                                        .addArgument(nameArg)
                                                        .addArgument(startArg)
                                                        .addArgument("to")
                                                        .addArgument(endArg);

    private final Optional<LocalDateTime> referenceDateTime;

    public AddEventParser() {
        this(Optional.empty());
    }

    public AddEventParser(LocalDateTime referenceDateTime) {
        this(Optional.of(referenceDateTime));
    }

    public AddEventParser(Optional<LocalDateTime> referenceDateTime) {
        this.referenceDateTime = referenceDateTime;
    }

    @Override
    public AddEventCommand parse(String str) throws ParseException {
        // Tell date/time parsers the current time
        final LocalDateTime now = referenceDateTime.orElse(LocalDateTime.now());
        startArg.setReferenceDateTime(now);
        endArg.setReferenceDateTime(now);

        // Parse command
        cmdParser.parse(str);

        final LocalDateTimeDuration duration = makeDuration(now);
        final EventTask toAdd = new EventTask(nameArg.getValue(), duration);
        return new AddEventCommand(toAdd);
    }

    private LocalDateTimeDuration makeDuration(LocalDateTime now) throws ParseException {
        final LocalDate startDate = startArg.getDate().orElse(now.toLocalDate());
        final LocalTime startTime = startArg.getTime().orElse(LocalTime.of(0, 0));
        final LocalDate endDate = endArg.getDate().orElse(startDate);
        final LocalTime endTime = endArg.getTime().orElse(LocalTime.of(23, 59));
        try {
            return new LocalDateTimeDuration(LocalDateTime.of(startDate, startTime),
                                             LocalDateTime.of(endDate, endTime));
        } catch (IllegalValueException e) {
            throw new ParseExceptionBuilder(e)
                    .addRangeOptional(startArg.getDateRange())
                    .addRangeOptional(startArg.getTimeRange())
                    .addRangeOptional(endArg.getDateRange())
                    .addRangeOptional(endArg.getTimeRange())
                    .build();
        }
    }

}
