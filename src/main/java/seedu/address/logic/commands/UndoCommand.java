package seedu.address.logic.commands;

import seedu.address.model.Model.Commit;
import seedu.address.model.Model;
import seedu.address.model.ModelManager.HeadAtBoundaryException;

public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n" + "Undos previous action that modifies address book information.\n\t"
            + "Example: " + COMMAND_WORD;

    @Override
    public CommandResult execute(Model model) {
        final Commit undoneCommit;
        try {
            undoneCommit = model.undo();
        } catch (HeadAtBoundaryException e) {
            return new CommandResult("No actions to undo.");
        }
        return new CommandResult("Successfully undid previous " + undoneCommit.getName());
    }

}
