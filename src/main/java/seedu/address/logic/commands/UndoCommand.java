package seedu.address.logic.commands;

import seedu.address.commons.exceptions.IllegalValueException;

public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n" + "Undos previous action that modifies address book information.\n\t"
            + "Example: " + COMMAND_WORD;

    @Override
    public CommandResult execute() {

        Command undoneAction;
        try {
            undoneAction = model.undo();
        } catch (IllegalValueException e) {
            return new CommandResult("No actions to undo.");
        }
        return new CommandResult("Successfully undid previous " + undoneAction.toString());
    }

}
