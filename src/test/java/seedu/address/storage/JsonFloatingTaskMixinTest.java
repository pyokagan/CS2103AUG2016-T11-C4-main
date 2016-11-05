package seedu.address.storage;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.task.FloatingTask;
import seedu.address.model.task.Name;
import seedu.address.model.task.Priority;

public class JsonFloatingTaskMixinTest {

    private static ObjectMapper objectMapper;

    public static final FloatingTask TEST_FLOATING_TASK1;
    public static final String TEST_FLOATING_TASK1_JSON = "{\"name\":\"task name 1\","
                                                         + "\"priority\":\"3\","
                                                         + "\"finished\":false}";
    public static final FloatingTask TEST_FLOATING_TASK2;
    public static final String TEST_FLOATING_TASK2_JSON = "{\"name\":\"task name 2\","
                                                         + "\"priority\":\"4\","
                                                         + "\"finished\":true}";

    static {
        try {
            TEST_FLOATING_TASK1 = new FloatingTask(new Name("task name 1"), new Priority("3"));
            TEST_FLOATING_TASK2 = new FloatingTask(new Name("task name 2"), new Priority("4"), true);
        } catch (IllegalValueException e) {
            throw new RuntimeException(e);
        }
    }

    @BeforeClass
    public static void setupObjectMapper() {
        final SimpleModule module = new SimpleModule();
        module.setMixInAnnotation(Name.class, JsonNameMixin.class);
        module.setMixInAnnotation(Priority.class, JsonPriorityMixin.class);
        module.setMixInAnnotation(FloatingTask.class, JsonFloatingTaskMixin.class);
        objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules().registerModule(module);
    }

    @Test
    public void serialize() throws Exception {
        final String expected1 = TEST_FLOATING_TASK1_JSON;
        final String expected2 = TEST_FLOATING_TASK2_JSON;
        final String actual1 = objectMapper.writeValueAsString(TEST_FLOATING_TASK1);
        final String actual2 = objectMapper.writeValueAsString(TEST_FLOATING_TASK2);
        assertEquals(expected1, actual1);
        assertEquals(expected2, actual2);
    }

    @Test
    public void deserialize() throws Exception {
        final FloatingTask expected1 = TEST_FLOATING_TASK1;
        final FloatingTask expected2 = TEST_FLOATING_TASK2;
        final FloatingTask actual1 = objectMapper.readValue(TEST_FLOATING_TASK1_JSON, FloatingTask.class);
        final FloatingTask actual2 = objectMapper.readValue(TEST_FLOATING_TASK2_JSON, FloatingTask.class);
        assertEquals(expected1, actual1);
        assertEquals(expected2, actual2);
    }

}
