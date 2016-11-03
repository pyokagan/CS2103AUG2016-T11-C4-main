package seedu.address.logic.parser;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import seedu.address.commons.core.IndexPrefix;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.EditEventCommand;
import seedu.address.logic.parser.CommandLineParser.Argument;
import seedu.address.logic.parser.CommandLineParser.OptionalFlag;
import seedu.address.model.ReadOnlyModel;
import seedu.address.model.task.EventTask;
import seedu.address.model.task.Name;

public class EditEventParser implements Parser<EditEventCommand> {

    private final Argument<Integer> indexArg = new Argument<>("INDEX", new IndexParser(IndexPrefix.EVENT));
    private final OptionalFlag<LocalDate> newStartDateFlag = new OptionalFlag<>("sd-", "NEW_START_DATE",
            new DateParser().withAutocomplete(this::autocompleteStartDate));
    private final OptionalFlag<LocalTime> newStartTimeFlag = new OptionalFlag<>("st-", "NEW_START_TIME",
            new TimeParser().withAutocomplete(this::autocompleteStartTime));
    private final OptionalFlag<LocalDate> newEndDateFlag = new OptionalFlag<>("ed-", "NEW_END_DATE",
            new DateParser().withAutocomplete(this::autocompleteEndDate));
    private final OptionalFlag<LocalTime> newEndTimeFlag = new OptionalFlag<>("et-", "NEW_END_TIME",
            new TimeParser().withAutocomplete(this::autocompleteEndTime));
    private final OptionalFlag<Name> newNameFlag = new OptionalFlag<>("n-", "NEW_NAME",
            new NameParser().withAutocomplete(this::autocompleteName));
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

    @Override
    public List<String> autocomplete(ReadOnlyModel model, String input, int pos) {
        return cmdParser.autocomplete(model, input, pos);
    }

    private List<String> autocompleteField(ReadOnlyModel model, String input, int pos,
                                           Function<EventTask, String> mapper) {
        if (!indexArg.isPresent() || !input.trim().isEmpty()) {
            return Collections.emptyList();
        }
        final EventTask eventTask;
        try {
            eventTask = model.getEventTask(indexArg.getValue());
        } catch (IllegalValueException e) {
            return Collections.emptyList(); // Invalid index, so no autocompletions
        }
        return Arrays.asList(mapper.apply(eventTask));
    }

    private List<String> autocompleteName(ReadOnlyModel model, String input, int pos) {
        return autocompleteField(model, input, pos, eventTask -> eventTask.getName().toString());
    }

    private List<String> autocompleteStartDate(ReadOnlyModel model, String input, int pos) {
        return autocompleteField(model, input, pos,
            eventTask -> new DateParser().format(eventTask.getStart().toLocalDate()));
    }

    private List<String> autocompleteStartTime(ReadOnlyModel model, String input, int pos) {
        return autocompleteField(model, input, pos,
            eventTask -> new TimeParser().format(eventTask.getStart().toLocalTime()));
    }

    private List<String> autocompleteEndDate(ReadOnlyModel model, String input, int pos) {
        return autocompleteField(model, input, pos,
            eventTask -> new DateParser().format(eventTask.getEnd().toLocalDate()));
    }

    private List<String> autocompleteEndTime(ReadOnlyModel model, String input, int pos) {
        return autocompleteField(model, input, pos,
            eventTask -> new TimeParser().format(eventTask.getEnd().toLocalTime()));
    }

}
