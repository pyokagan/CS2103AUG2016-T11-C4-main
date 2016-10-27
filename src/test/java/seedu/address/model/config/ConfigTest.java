package seedu.address.model.config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ConfigTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void toString_defaultObject_stringReturned() {
        String defaultConfigAsString = "Current log level : INFO\n"
                + "Local data file location : data/taskbook.json";

        assertEquals(defaultConfigAsString, new Config().toString());
    }

    @Test
    public void equalsMethod() {
        Config defaultConfig = new Config();
        assertFalse(defaultConfig.equals(null));
        assertTrue(defaultConfig.equals(defaultConfig));
    }

}
