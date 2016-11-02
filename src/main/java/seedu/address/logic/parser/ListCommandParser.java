package seedu.address.logic.parser;

import java.time.LocalDateTime;
import java.util.Optional;

import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.parser.CommandLineParser.RestArgument;
import seedu.address.model.filter.TaskPredicate;

/**
 * Parser for the "list" command.
 */
public class ListCommandParser implements Parser<ListCommand> {

    private final RestArgument<TaskPredicate> taskPredicateArg = new RestArgument<>("FILTER", new TaskPredicateParser(LocalDateTime.now()));
    private final CommandLineParser cmdParser = new CommandLineParser()
                                                    .addArgument(taskPredicateArg);

    private final Optional<LocalDateTime> referenceDateTime;

    public ListCommandParser(Optional<LocalDateTime> referenceDateTime) {
        this.referenceDateTime = referenceDateTime;
    }

    public ListCommandParser() {
        this(Optional.empty());
    }

    @Override
    public ListCommand parse(String str) throws ParseException {
        // Tell parsers the current time
        final LocalDateTime now = referenceDateTime.orElse(LocalDateTime.now());
        taskPredicateArg.setParser(new TaskPredicateParser(now));

        cmdParser.parse(str);

        return new ListCommand(taskPredicateArg.getValue());
    }

}
