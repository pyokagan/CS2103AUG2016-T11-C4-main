package seedu.address.logic.parser;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.IncorrectCommand;
import seedu.address.logic.commands.SearchCommand;

public class SearchParser {
	private static final Pattern CMD_PATTERN =
            Pattern.compile("\\s*\"(?<keyword>[^\"])"+"$");


	public Command parse(String args){

		final Matcher matcher = CMD_PATTERN.matcher(args);
    	if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SearchCommand.MESSAGE_USAGE));
        }
    	final String keyword = matcher.group("keyword").trim();

    	final Set<String> SearchKey = new HashSet<String>(Arrays.asList(keyword));
    	//try{
    		//return new SearchCommand(SearchKey);
    	//}
    	//catch (IllegalValueException e){
    		//return new IncorrectCommand(e.getMessage());
    	//}
    }



}
