package seedu.address.logic.parser;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.EditDeadlineCommand;
import seedu.address.logic.parser.CommandLineParser.Argument;
import seedu.address.logic.parser.CommandLineParser.OptionalFlag;
import seedu.address.model.ReadOnlyModel;
import seedu.address.model.task.DeadlineTask;
import seedu.address.model.task.Name;
import seedu.address.model.task.TaskType;

public class EditDeadlineParser implements Parser<EditDeadlineCommand> {

    private final Argument<Integer> indexArg = new Argument<>("INDEX", new IndexParser(TaskType.DEADLINE));
    private final OptionalFlag<LocalDate> newDateFlag = new OptionalFlag<>("dd-", "NEW_DUE_DATE",
            new DateParser().withAutocomplete(this::autocompleteDueDate));
    private final OptionalFlag<LocalTime> newTimeFlag = new OptionalFlag<>("dt-", "NEW_DUE_TIME",
            new TimeParser().withAutocomplete(this::autocompleteDueTime));
    private final OptionalFlag<Name> newNameFlag = new OptionalFlag<>("n-", "NEW_NAME",
            new NameParser().withAutocomplete(this::autocompleteName));
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

    @Override
    public List<String> autocomplete(ReadOnlyModel model, String input, int pos) {
        return cmdParser.autocomplete(model, input, pos);
    }

    private List<String> autocompleteField(ReadOnlyModel model, String input, int pos,
                                           Function<DeadlineTask, String> mapper) {
        if (!indexArg.isPresent() || !input.trim().isEmpty()) {
            return Collections.emptyList();
        }
        final DeadlineTask deadlineTask;
        try {
            deadlineTask = model.getDeadlineTask(indexArg.getValue());
        } catch (IllegalValueException e) {
            return Collections.emptyList(); // Invalid index, so no autocompletions
        }
        return Arrays.asList(mapper.apply(deadlineTask));
    }

    private List<String> autocompleteName(ReadOnlyModel model, String input, int pos) {
        return autocompleteField(model, input, pos, deadlineTask -> deadlineTask.getName().toString());
    }

    private List<String> autocompleteDueDate(ReadOnlyModel model, String input, int pos) {
        return autocompleteField(model, input, pos,
            deadlineTask -> new DateParser().format(deadlineTask.getDue().toLocalDate()));
    }

    private List<String> autocompleteDueTime(ReadOnlyModel model, String input, int pos) {
        return autocompleteField(model, input, pos,
            deadlineTask -> new TimeParser().format(deadlineTask.getDue().toLocalTime()));
    }

}
