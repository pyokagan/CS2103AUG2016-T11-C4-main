package seedu.address.logic.parser;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.time.temporal.ChronoField;
import java.util.Optional;

import seedu.address.commons.util.SubstringRange;

/**
 * A parser for dates in day/month/year format.
 */
public class DateParser implements Parser<LocalDate> {

    private final LocalDate referenceDate;

    private final DateTimeFormatter dateFormatter;

    public DateParser(LocalDate referenceDate) {
        this.referenceDate = referenceDate;
        dateFormatter = new DateTimeFormatterBuilder()
                .appendPattern("d[/M[/uuuu]]")
                .parseDefaulting(ChronoField.MONTH_OF_YEAR, this.referenceDate.getMonthValue())
                .parseDefaulting(ChronoField.YEAR, this.referenceDate.getYear())
                .toFormatter()
                .withResolverStyle(ResolverStyle.STRICT);
    }

    public DateParser() {
        this(LocalDate.now());
    }

    public LocalDate getReferenceDate() {
        return referenceDate;
    }

    @Override
    public LocalDate parse(String str) throws ParseException {
        assert str != null;
        final Optional<LocalDate> nameDate = parseAsName(str.trim());
        if (nameDate.isPresent()) {
            return nameDate.get();
        }
        try {
            return LocalDate.parse(str.trim(), dateFormatter);
        } catch (DateTimeParseException e) {
            throw new ParseException(e.toString(), e, SubstringRange.of(str));
        }
    }

    private Optional<LocalDate> parseAsName(String name) {
        switch (name) {
        case "tdy": // today
            return Optional.of(referenceDate);
        case "tmr": // tomorrow
            return Optional.of(referenceDate.plusDays(1));
        case "yst": // yesterday
            return Optional.of(referenceDate.minusDays(1));
        default:
            return Optional.empty();
        }
    }

    /**
     * Returns the string representation of a LocalDate. This string can be parsed by {@link #parse} to
     * get back the same LocalDate.
     * @see #parse
     */
    public String format(LocalDate date) {
        return dateFormatter.format(date);
    }

}
