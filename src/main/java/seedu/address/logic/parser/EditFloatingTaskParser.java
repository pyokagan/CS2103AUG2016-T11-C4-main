package seedu.address.logic.parser;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

import seedu.address.commons.core.IndexPrefix;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.EditFloatingTaskCommand;
import seedu.address.logic.parser.CommandLineParser.Argument;
import seedu.address.logic.parser.CommandLineParser.OptionalFlag;
import seedu.address.model.ReadOnlyModel;
import seedu.address.model.task.FloatingTask;
import seedu.address.model.task.Name;
import seedu.address.model.task.Priority;

public class EditFloatingTaskParser implements Parser<EditFloatingTaskCommand> {

    private final Argument<Integer> indexArg = new Argument<>("INDEX", new IndexParser(IndexPrefix.FLOAT));
    private final OptionalFlag<Name> newNameFlag = new OptionalFlag<>("n-", "NEW_NAME",
            new NameParser().withAutocomplete(this::autocompleteName));
    private final OptionalFlag<Priority> newPriorityFlag = new OptionalFlag<>("p-", "NEW_PRIORITY",
            new PriorityParser().withAutocomplete(this::autocompletePriority));
    private final CommandLineParser cmdParser = new CommandLineParser()
                                                        .addArgument(indexArg)
                                                        .putFlag(newNameFlag)
                                                        .putFlag(newPriorityFlag);

    @Override
    public EditFloatingTaskCommand parse(String args) throws ParseException {
        cmdParser.parse(args);

        return new EditFloatingTaskCommand(indexArg.getValue(), newNameFlag.getValue(),
                                           newPriorityFlag.getValue());
    }

    @Override
    public List<String> autocomplete(ReadOnlyModel model, String input, int pos) {
        return cmdParser.autocomplete(model, input, pos);
    }

    private List<String> autocompleteField(ReadOnlyModel model, String input, int pos,
                                           Function<FloatingTask, String> mapper) {
        if (!indexArg.isPresent() || !input.trim().isEmpty()) {
            return Collections.emptyList();
        }
        final FloatingTask floatingTask;
        try {
            floatingTask = model.getFloatingTask(indexArg.getValue());
        } catch (IllegalValueException e) {
            return Collections.emptyList(); // Invalid index, so no autocompletions
        }
        return Arrays.asList(mapper.apply(floatingTask));
    }

    private List<String> autocompleteName(ReadOnlyModel model, String input, int pos) {
        return autocompleteField(model, input, pos, floatingTask -> floatingTask.getName().toString());
    }

    private List<String> autocompletePriority(ReadOnlyModel model, String input, int pos) {
        return autocompleteField(model, input, pos, floatingTask -> floatingTask.getPriority().toString());
    }

}
