package seedu.address.logic.parser;

public interface Parser<T> {

    T parse(String str) throws ParseException;

}
