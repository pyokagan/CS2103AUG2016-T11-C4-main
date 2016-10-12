package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static seedu.address.commons.time.TypicalLocalDateTimes.UNIX_EPOCH;

import org.junit.BeforeClass;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.task.EventTask;
import seedu.address.model.task.Name;

public class JsonEventTaskMixinTest {

    private static ObjectMapper objectMapper;

    public static final EventTask TEST_EVENT;
    public static final String TEST_EVENT_JSON = "{\"name\":\"event name\",\"start\":[1970,1,1,0,0],"
                                                 + "\"end\":[1970,1,1,1,0]}";

    static {
        try {
            TEST_EVENT = new EventTask(new Name("event name"), UNIX_EPOCH, UNIX_EPOCH.plusHours(1));
        } catch (IllegalValueException e) {
            throw new RuntimeException(e);
        }
    }

    @BeforeClass
    public static void setupObjectMapper() {
        final SimpleModule module = new SimpleModule();
        module.setMixInAnnotation(Name.class, JsonNameMixin.class);
        module.setMixInAnnotation(EventTask.class, JsonEventTaskMixin.class);
        objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules().registerModule(module);
    }

    @Test
    public void serialize() throws Exception {
        final String expected = TEST_EVENT_JSON;
        final String actual = objectMapper.writeValueAsString(TEST_EVENT);
        assertEquals(expected, actual);
    }

    @Test
    public void deserialize() throws Exception {
        final EventTask expected = TEST_EVENT;
        final EventTask actual = objectMapper.readValue(TEST_EVENT_JSON, EventTask.class);
        assertEquals(expected, actual);
    }

}
