package seedu.address.logic.parser;

import java.time.LocalTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.commons.util.SubstringRange;

/**
 * A parser for 12-hour clock times.
 */
public class TimeParser implements Parser<LocalTime> {

    private static final Pattern PATTERN_TIME = Pattern.compile("(?<hour>\\d{1,2})"
            + "(?:[.:](?<minute>\\d{2}))?(?<ampm>am|pm)");

    private final LocalTime referenceTime;

    public TimeParser(LocalTime referenceTime) {
        this.referenceTime = referenceTime;
    }

    public TimeParser() {
        this(LocalTime.now());
    }

    public LocalTime getReferenceTime() {
        return referenceTime;
    }

    @Override
    public LocalTime parse(String str) throws ParseException {
        final Matcher matcher = PATTERN_TIME.matcher(str.trim());
        if (!matcher.matches()) {
            throw new ParseException("invalid time format", SubstringRange.of(str));
        }

        int hour = Integer.parseInt(matcher.group("hour"));
        if (hour > 12) {
            throw new ParseException("invalid hour: " + hour,
                                     new SubstringRange(matcher.start("hour"), matcher.end("hour")));
        } else if (hour == 12) {
            hour = 0;
        }

        final int minute = matcher.group("minute") != null ? Integer.parseInt(matcher.group("minute")) : 0;
        if (minute >= 60) {
            assert matcher.group("minute") != null;
            throw new ParseException("invalid minute: " + minute,
                                     new SubstringRange(matcher.start("minute"), matcher.end("minute")));
        }

        final boolean isPM = matcher.group("ampm").equals("pm");
        return LocalTime.of(hour + (isPM ? 12 : 0), minute);
    }

    /**
     * Returns the string representation of a LocalTime. This string can be parsed by {@link #parse} to
     * get back a LocalTime with the same hour and minute.
     */
    public String format(LocalTime time) {
        final StringBuilder sb = new StringBuilder();
        int hour = time.getHour() % 12 == 0 ? 12 : (time.getHour() % 12);
        sb.append(hour);
        if (time.getMinute() != 0) {
            sb.append(":").append(String.format("%02d", time.getMinute()));
        }
        sb.append(time.getHour() >= 12 ? "pm" : "am");
        return sb.toString();
    }

}
