package seedu.address.logic.parser;

import java.util.Collections;
import java.util.List;

import seedu.address.model.ReadOnlyModel;

/**
 * Represents a parser which can parse a string and produce a result of type T.
 *
 * This is a functional interface whose functional method is {@link #parse}.
 */
public interface Parser<T> {

    /**
     * Parses an input string and returns the parsed result as an object with type T.
     * @throws ParseException if the input string could not be parsed. The ranges of the ParseException will
     * contain the substring ranges of the input string which caused the parsing to fail.
     */
    T parse(String str) throws ParseException;

    default List<String> autocomplete(ReadOnlyModel model, String input, int pos) {
        return Collections.emptyList();
    }

    /**
     * Returns a parser that overrides {@link #autocomplete} with the specified function.
     */
    default Parser<T> withAutocomplete(final AutocompleteCallback callback) {
        final Parser<T> parent = this;
        return new Parser<T>() {
            @Override
            public T parse(String str) throws ParseException {
                return parent.parse(str);
            }

            @Override
            public List<String> autocomplete(ReadOnlyModel model, String input, int pos) {
                return callback.call(model, input, pos);
            }
        };
    }

    interface AutocompleteCallback {
        List<String> call(ReadOnlyModel model, String input, int pos);
    }

}
