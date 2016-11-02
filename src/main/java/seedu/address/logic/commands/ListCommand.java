package seedu.address.logic.commands;

import seedu.address.model.Model;

public class ListCommand implements Command {

    @Override
    public CommandResult execute(Model model) {
        assert model != null;
        model.setTaskPredicate(null);
        return new CommandResult("Listed all tasks.");
    }

}
