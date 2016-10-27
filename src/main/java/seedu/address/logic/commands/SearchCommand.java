package seedu.address.logic.commands;
import java.util.Set;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.filter.NameContainsKeywordsPredicate;
/**
 * Searches for names in the task book that contain the input keyword.
 */
public class SearchCommand extends Command {
	 public static final String COMMAND_WORD = "search";

	    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Displays the list of tasks containing the keyword "
	            + "\n"
	            + "Example: " + COMMAND_WORD
	            + " \"bob\" ";

	    public static final String MESSAGE_SUCCESS = "Search results displayed: %1$s";

	    private final Set<String> keywords;

	    public SearchCommand(Set<String> search) throws IllegalValueException{
	    	this.keywords = search;
	    }

	    public Set<String> getKeyWords(){
	    	return keywords;
	    }

	    @Override
	    public CommandResult execute() {
	        assert model != null;
	        model.setFilter(new NameContainsKeywordsPredicate(keywords));
	        return new CommandResult(getMessageForTaskListShownSummary(model.getFilteredTaskList().size()));
	    }


}
