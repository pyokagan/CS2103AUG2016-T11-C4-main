package seedu.address.logic.parser;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import seedu.address.logic.commands.SetDataDirectoryCommand;
import seedu.address.logic.parser.CommandLineParser.RestArgument;
import seedu.address.model.ReadOnlyModel;

/**
 * Parser for the "setdatadir" command.
 */
public class SetDataDirectoryParser implements Parser<SetDataDirectoryCommand> {

    private final RestArgument<File> newDirArg = new RestArgument<>("NEW_DIRECTORY",
            new FileParser().withAutocomplete(this::autocompleteNewDir));
    private final CommandLineParser cmdParser = new CommandLineParser().addArgument(newDirArg);

    @Override
    public SetDataDirectoryCommand parse(String str) throws ParseException {
        cmdParser.parse(str);
        return new SetDataDirectoryCommand(newDirArg.getValue());
    }

    @Override
    public List<String> autocomplete(ReadOnlyModel model, String input, int pos) {
        return cmdParser.autocomplete(model, input, pos);
    }

    private List<String> autocompleteNewDir(ReadOnlyModel model, String input, int pos) {
        if (!input.trim().isEmpty()) {
            return Collections.emptyList();
        }
        return Arrays.asList(model.getTaskBookFilePath());
    }

}
