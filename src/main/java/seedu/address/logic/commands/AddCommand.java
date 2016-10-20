package seedu.address.logic.commands;

/**
 * Adds a person to the task book.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a task to the task book. "
            + "Parameters: NAME\n"
            + "Example: " + COMMAND_WORD
            + " John Doe";

    public static final String MESSAGE_SUCCESS = "New task added: %1$s";

    @Override
    public CommandResult execute() {
        // TODO Auto-generated method stub
        return null;
    }
}
