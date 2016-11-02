package seedu.address.logic.parser;

import java.util.HashSet;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.parser.CommandLineParser.ListArgument;
import seedu.address.model.filter.TaskNameContainsKeywordsPredicate;
import seedu.address.model.filter.TaskPredicate;

/**
 * Parser for the "find" command.
 */
public class FindCommandParser implements Parser<Command> {
    private final ListArgument<String> keywordsArg = new ListArgument<>("KEYWORDS", x -> x);
    private final CommandLineParser cmdParser = new CommandLineParser()
                                                    .addArgument(keywordsArg);

    @Override
    public Command parse(String str) throws ParseException {
        cmdParser.parse(str);

        final HashSet<String> keywords = new HashSet<>(keywordsArg.getValues());
        if (keywords.isEmpty()) {
            throw new ParseException("must provide at least one keyword", keywordsArg.getRange());
        }

        final TaskPredicate predicate = new TaskNameContainsKeywordsPredicate(keywords);
        return new ListCommand(predicate);
    }

}
