package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.commons.util.StringUtil;
import seedu.address.logic.commands.AddTaskCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.DeleteDeadlineCommand;
import seedu.address.logic.commands.DeleteEventCommand;
import seedu.address.logic.commands.DeleteFloatingTaskCommand;
import seedu.address.logic.commands.EditDeadlineCommand;
import seedu.address.logic.commands.EditEventCommand;
import seedu.address.logic.commands.EditFloatingTaskCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.IncorrectCommand;
import seedu.address.logic.commands.MarkDeadlineFinishedCommand;
import seedu.address.logic.commands.MarkFloatingTaskFinishedCommand;

/**
 * Parses user input.
 */
public class TaskTrackerParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    private static final Pattern TASK_INDEX_ARGS_FORMAT = Pattern.compile("(?<targetIndex>.+)");

    public TaskTrackerParser() {}

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     */
    public Command parseCommand(String userInput) {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        switch (commandWord) {

        case AddTaskCommand.COMMAND_WORD:
            return new AddTaskParser().parse(arguments);

        case DeleteFloatingTaskCommand.COMMAND_WORD:
            return prepareDeleteFloatingTask(arguments);

        case EditFloatingTaskCommand.COMMAND_WORD:
            return new EditFloatingTaskParser().parse(arguments);

        case MarkFloatingTaskFinishedCommand.COMMAND_WORD:
            return prepareMarkFloatingTaskFinished(arguments);

        case DeleteEventCommand.COMMAND_WORD:
            return prepareDeleteEvent(arguments);

        case EditEventCommand.COMMAND_WORD:
            return new EditEventParser().parse(arguments);

        case DeleteDeadlineCommand.COMMAND_WORD:
            return prepareDeleteDeadline(arguments);

        case EditDeadlineCommand.COMMAND_WORD:
            return new EditDeadlineParser().parse(arguments);

        case MarkDeadlineFinishedCommand.COMMAND_WORD:
            return prepareMarkDeadlineFinished(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case SetDataDirectoryParser.COMMAND_WORD:
            return new SetDataDirectoryParser().parse(arguments);

        default:
            return new IncorrectCommand(MESSAGE_UNKNOWN_COMMAND);
        }
    }

    /**
     * Parses arguments in the context of the delete floating task command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareDeleteFloatingTask(String args) {
        Optional<Integer> index = parseIndex(args);
        if (!index.isPresent()) {
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                                  DeleteFloatingTaskCommand.MESSAGE_USAGE));
        }
        return new DeleteFloatingTaskCommand(index.get());
    }

    /**
     * Parses arguments in the context of the mark floating task finished command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareMarkFloatingTaskFinished(String args) {
        Optional<Integer> index = parseIndex(args);
        if (!index.isPresent()) {
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkFloatingTaskFinishedCommand.MESSAGE_USAGE));
        }
        return new MarkFloatingTaskFinishedCommand(index.get());
    }

    /**
     * Parses arguments in the context of the delete event command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareDeleteEvent(String args) {
        Optional<Integer> index = parseIndex(args);
        if (!index.isPresent()) {
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteEventCommand.MESSAGE_USAGE));
        }
        return new DeleteEventCommand(index.get());
    }

    /**
     * Parses arguments in the context of the delete deadline command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareDeleteDeadline(String args) {
        Optional<Integer> index = parseIndex(args);
        if (!index.isPresent()) {
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteDeadlineCommand.MESSAGE_USAGE));
        }
        return new DeleteDeadlineCommand(index.get());
    }

    /**
     * Parses arguments in the context of the mark deadline finished command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareMarkDeadlineFinished(String args) {
        Optional<Integer> index = parseIndex(args);
        if (!index.isPresent()) {
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkDeadlineFinishedCommand.MESSAGE_USAGE));
        }
        return new MarkDeadlineFinishedCommand(index.get());
    }

    /**
     * Returns the specified index in the {@code command} IF a positive unsigned integer is given as the index.
     *   Returns an {@code Optional.empty()} otherwise.
     */
    private Optional<Integer> parseIndex(String command) {
        final Matcher matcher = TASK_INDEX_ARGS_FORMAT.matcher(command.trim());
        if (!matcher.matches()) {
            return Optional.empty();
        }

        String index = matcher.group("targetIndex");
        if (!StringUtil.isUnsignedInteger(index)) {
            return Optional.empty();
        }
        return Optional.of(Integer.parseInt(index));

    }

}
