package seedu.address.logic.parser;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

import seedu.address.logic.commands.AddDeadlineCommand;
import seedu.address.logic.parser.CommandLineParser.Argument;
import seedu.address.model.task.DeadlineTask;
import seedu.address.model.task.Name;

public class AddDeadlineParser implements Parser<AddDeadlineCommand> {

    private final Argument<Name> nameArg = new Argument<>("NAME", new NameParser());
    private final DateTimeArgument dateTimeArg = new DateTimeArgument("DATE", "TIME");

    private final CommandLineParser cmdParser = new CommandLineParser()
                                                    .addArgument(nameArg)
                                                    .addArgument(dateTimeArg);

    private final Optional<LocalDateTime> referenceDateTime;

    public AddDeadlineParser() {
        this(Optional.empty());
    }

    public AddDeadlineParser(LocalDateTime referenceDateTime) {
        this(Optional.of(referenceDateTime));
    }

    public AddDeadlineParser(Optional<LocalDateTime> referenceDateTime) {
        this.referenceDateTime = referenceDateTime;
    }

    @Override
    public AddDeadlineCommand parse(String str) throws ParseException {
        // Tell date/time parsers the current time
        final LocalDateTime now = referenceDateTime.orElse(LocalDateTime.now());
        dateTimeArg.setReferenceDateTime(now);

        // Parse command
        cmdParser.parse(str);

        final LocalDate date = dateTimeArg.getDate().orElse(now.toLocalDate());
        final LocalTime time = dateTimeArg.getTime().orElse(LocalTime.of(23, 59));
        final DeadlineTask toAdd = new DeadlineTask(nameArg.getValue(), LocalDateTime.of(date, time));
        return new AddDeadlineCommand(toAdd);
    }

}
