package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static seedu.address.commons.time.TypicalLocalDateTimes.UNIX_EPOCH;

import org.junit.BeforeClass;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.task.DeadlineTask;
import seedu.address.model.task.Name;

public class JsonDeadlineTaskMixinTest {

    private static ObjectMapper objectMapper;

    public static final DeadlineTask TEST_DEADLINE1;
    public static final DeadlineTask TEST_DEADLINE2;
    public static final String TEST_DEADLINE1_JSON = "{\"name\":\"deadline name1\",\"due\":[1970,1,2,0,0],"
                                                    + "\"finished\":false}";
    public static final String TEST_DEADLINE2_JSON = "{\"name\":\"deadline name2\",\"due\":[1970,1,2,0,0],"
                                                    + "\"finished\":true}";

    static {
        try {
            TEST_DEADLINE1 = new DeadlineTask(new Name("deadline name1"), UNIX_EPOCH.plusDays(1));
            TEST_DEADLINE2 = new DeadlineTask(new Name("deadline name2"), UNIX_EPOCH.plusDays(1), true);
        } catch (IllegalValueException e) {
            throw new RuntimeException(e);
        }
    }

    @BeforeClass
    public static void setupObjectMapper() {
        final SimpleModule module = new SimpleModule();
        module.setMixInAnnotation(Name.class, JsonNameMixin.class);
        module.setMixInAnnotation(DeadlineTask.class, JsonDeadlineTaskMixin.class);
        objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules().registerModule(module);
    }

    @Test
    public void serialize() throws Exception {
        final String expected1 = TEST_DEADLINE1_JSON;
        final String expected2 = TEST_DEADLINE2_JSON;
        final String actual1 = objectMapper.writeValueAsString(TEST_DEADLINE1);
        final String actual2 = objectMapper.writeValueAsString(TEST_DEADLINE2);
        assertEquals(expected1, actual1);
        assertEquals(expected2, actual2);
    }

    @Test
    public void deserialize() throws Exception {
        final DeadlineTask expected1 = TEST_DEADLINE1;
        final DeadlineTask expected2 = TEST_DEADLINE2;
        final DeadlineTask actual1 = objectMapper.readValue(TEST_DEADLINE1_JSON, DeadlineTask.class);
        final DeadlineTask actual2 = objectMapper.readValue(TEST_DEADLINE2_JSON, DeadlineTask.class);
        assertEquals(expected1, actual1);
        assertEquals(expected2, actual2);
    }

}
