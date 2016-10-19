package seedu.address.model;

import seedu.address.logic.commands.Command;

public class Commit {
    private TaskBook taskBook;
    private Command command;

    public Commit(Command command, TaskBook state) {
        this.taskBook = state;
        this.command = command;
    }

    public TaskBook getTaskBook() {
        return this.taskBook;
    }

    public Command getCommand() {
        return this.command;
    }
}
