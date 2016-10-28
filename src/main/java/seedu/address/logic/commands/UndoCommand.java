package seedu.address.logic.commands;

import java.util.List;

import seedu.address.model.Model.Commit;
import seedu.address.model.ModelManager.HeadAtBoundaryException;

public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n" + "Undos previous action that modifies address book information.\n\t"
            + "Example: " + COMMAND_WORD;

    @Override
    public CommandResult execute() {
        final List<Commit> undoneCommits;
        try {
            undoneCommits = model.undo(1);
        } catch (HeadAtBoundaryException e) {
            return new CommandResult("No actions to undo.");
        }
        assert undoneCommits.size() == 1;
        return new CommandResult("Successfully undid previous " + undoneCommits.get(0).getName());
    }

}
