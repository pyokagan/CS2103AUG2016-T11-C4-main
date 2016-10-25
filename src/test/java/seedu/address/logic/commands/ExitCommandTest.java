package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.commons.events.ui.ExitAppRequestEvent;
import seedu.address.testutil.EventsCollector;

public class ExitCommandTest {

    @Test
    public void execute() {
        final ExitCommand command = new ExitCommand();
        final EventsCollector eventsCollector = new EventsCollector();
        final CommandResult result = command.execute();
        assertEquals("Exiting Task Book as requested ...", result.feedbackToUser);
        assertTrue(eventsCollector.get(0) instanceof ExitAppRequestEvent);
    }

}
