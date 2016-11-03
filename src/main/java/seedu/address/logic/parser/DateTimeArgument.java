package seedu.address.logic.parser;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import seedu.address.commons.util.SubstringRange;
import seedu.address.logic.parser.CommandLineParser.Argument;
import seedu.address.model.ReadOnlyModel;

public class DateTimeArgument implements CommandLineParser.ArgumentParser {

    private final Argument<LocalDate> dateArg;
    private final Argument<LocalTime> timeArg;

    public DateTimeArgument(String dateName, String timeName) {
        this.dateArg = new Argument<>(dateName, new DateParser());
        this.timeArg = new Argument<>(timeName, new TimeParser());
    }

    public Optional<LocalDate> getDate() {
        return dateArg.isPresent() ? Optional.of(dateArg.getValue()) : Optional.empty();
    }

    public Optional<SubstringRange> getDateRange() {
        return dateArg.isPresent() ? Optional.of(dateArg.getRange()) : Optional.empty();
    }

    public Optional<LocalTime> getTime() {
        return timeArg.isPresent() ? Optional.of(timeArg.getValue()) : Optional.empty();
    }

    public Optional<SubstringRange> getTimeRange() {
        return timeArg.isPresent() ? Optional.of(timeArg.getRange()) : Optional.empty();
    }

    public void setReferenceDateTime(LocalDateTime referenceDateTime) {
        dateArg.setParser(new DateParser(referenceDateTime.toLocalDate()));
        timeArg.setParser(new TimeParser(referenceDateTime.toLocalTime()));
    }

    @Override
    public String getName() {
        return dateArg.getName() + " " + timeArg.getName();
    }

    @Override
    public void reset() {
        dateArg.reset();
        timeArg.reset();
    }

    @Override
    public void parse(CommandLineScanner scanner) throws ParseException {
        try {
            dateArg.parse(scanner);
        } catch (ParseException e) {
            // Okay for now
        }
        try {
            timeArg.parse(scanner);
        } catch (ParseException e) {
            if (!dateArg.isPresent()) {
                throw e;
            }
        }
    }

    @Override
    public List<String> autocomplete(ReadOnlyModel model, CommandLineScanner scanner, int pos) {
        // We simply just advance the scanner if we are able to parse as date/time
        try {
            dateArg.parse(scanner);
        } catch (ParseException e) {
            // Do nothing, we are just advancing the scanner if possible.
        }
        try {
            timeArg.parse(scanner);
        } catch (ParseException e) {
            // Do nothing, we are just advancing the scanner if possible
        }
        return Collections.emptyList();
    }

}
