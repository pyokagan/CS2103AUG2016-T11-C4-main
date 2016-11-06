package seedu.address.logic.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import seedu.address.model.ReadOnlyModel;

/**
 * A parser that tries to parse the input string with multiple parsers, and returns the first successful
 * result.
 */
public class OverloadParser<T> implements Parser<T> {

    private final List<Candidate<T>> candidates = new ArrayList<>();

    public OverloadParser<T> addParser(String name, Parser<? extends T> parser) {
        candidates.add(new Candidate<T>(name, parser));
        return this;
    }

    @Override
    public T parse(String str) throws ParseException {
        List<CandidateException> candidateExceptions = new ArrayList<>();

        // Try all parsers, while collecting their exceptions
        for (Candidate<T> candidate : candidates) {
            try {
                return candidate.parser.parse(str);
            } catch (ParseException e) {
                candidateExceptions.add(new CandidateException(candidate.name, e));
            }
        }

        throw makeParseException(candidateExceptions);
    }

    @Override
    public List<String> autocomplete(ReadOnlyModel model, String input, int pos) {
        // Try all parsers, collecting their autocompletions
        return candidates.stream()
                        .map(candidate -> candidate.parser.autocomplete(model, input, pos))
                        .flatMap(x -> x.stream())
                        .collect(Collectors.toList());
    }

    private ParseException makeParseException(List<CandidateException> candidateExceptions) {
        final ParseExceptionBuilder builder = new ParseExceptionBuilder("Tried to parse the input as the following but failed:");
        for (CandidateException candidateException : candidateExceptions) {
            builder.appendMessage("\n\n").appendMessage(candidateException.name).appendMessage(":\n    ")
                .appendMessage(candidateException.exception.getMessage());
            builder.addRanges(candidateException.exception.getRanges());
        }
        return builder.build();
    }

    private static class Candidate<T> {
        final String name;
        final Parser<? extends T> parser;

        Candidate(String name, Parser<? extends T> parser) {
            this.name = name;
            this.parser = parser;
        }
    }

    private static class CandidateException {
        final String name;
        final ParseException exception;

        CandidateException(String name, ParseException exception) {
            this.name = name;
            this.exception = exception;
        }
    }

}
