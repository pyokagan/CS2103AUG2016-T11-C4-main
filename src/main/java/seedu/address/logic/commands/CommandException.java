package seedu.address.logic.commands;

/**
 * Indicates an error which occurred while executing a {@link Command}.
 */
public class CommandException extends Exception {

    /**
     * Constructs a new exception with the specified detail message.
     */
    public CommandException(String message) {
        super(message);
    }

    /**
     * Constructs a new exception with the specified cause and the cause's detail message.
     */
    public CommandException(Throwable cause) {
        super(cause.getMessage(), cause);
    }

    /**
     * Constructs a new exception with the specified detail message and cause.
     */
    public CommandException(String message, Throwable cause) {
        super(message, cause);
    }

}
