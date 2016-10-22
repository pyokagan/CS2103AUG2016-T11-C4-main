package seedu.address.logic.parser;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.IncorrectCommand;
import seedu.address.logic.commands.SearchCommand;

public class SearchParserTest {
	private SearchParser parser;
	@Before
    public void setupParser() {
        parser = new SearchParser();
    }

	@Test
	public void parse() throws IllegalValueException{
		Set<String> searchWord = new HashSet<String>();
		assertParse("searchWord", searchWord);

	}

	private void assertParse(String args, Set<String> keyword){
		final Command command = parser.parse(args);
		assertTrue(command instanceof SearchCommand);
		assertEquals(keyword, ((SearchCommand)command).getKeyWord());
	}
	private void assertIncorrect(String args){
		final Command command = parser.parse(args);
        assertTrue(command instanceof IncorrectCommand);
	}
}
