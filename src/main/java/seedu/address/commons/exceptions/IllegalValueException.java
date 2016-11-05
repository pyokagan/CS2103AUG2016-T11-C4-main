package seedu.address.commons.exceptions;

/**
 * Signals that a given piece of data does not fulfill some constraint(s).
 */
public class IllegalValueException extends Exception {
    /**
     * @param message should contain relevant information on the failed constraint(s)
     */
    public IllegalValueException(String message) {
        super(message);
    }

    /**
     * @param message should contain relevant information on the failed constraint(s).
     * @param cause The Throwable which caused this exception to be thrown.
     */
    public IllegalValueException(String message, Throwable cause) {
        super(message, cause);
    }
}
