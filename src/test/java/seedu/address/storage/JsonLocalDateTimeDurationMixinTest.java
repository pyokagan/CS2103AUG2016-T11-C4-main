package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static seedu.address.commons.time.TypicalLocalDateTimes.UNIX_EPOCH;

import org.junit.BeforeClass;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.time.LocalDateTimeDuration;

public class JsonLocalDateTimeDurationMixinTest {

    public static final LocalDateTimeDuration UNIX_EPOCH_1HR;
    public static final String UNIX_EPOCH_1HR_JSON = "{\"start\":[1970,1,1,0,0],\"end\":[1970,1,1,1,0]}";

    private static ObjectMapper objectMapper;

    static {
        try {
            UNIX_EPOCH_1HR = new LocalDateTimeDuration(UNIX_EPOCH, UNIX_EPOCH.plusHours(1));
        } catch (IllegalValueException e) {
            throw new RuntimeException(e);
        }
    }

    @BeforeClass
    public static void setupObjectMapper() {
        final SimpleModule module = new SimpleModule();
        module.setMixInAnnotation(LocalDateTimeDuration.class, JsonLocalDateTimeDurationMixin.class);
        objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules().registerModule(module);
    }

    @Test
    public void serialize() throws Exception {
        final String expected = UNIX_EPOCH_1HR_JSON;
        final String actual = objectMapper.writeValueAsString(UNIX_EPOCH_1HR);
        assertEquals(expected, actual);
    }

    @Test
    public void deserialize() throws Exception {
        final LocalDateTimeDuration expected = UNIX_EPOCH_1HR;
        final LocalDateTimeDuration actual = objectMapper.readValue(UNIX_EPOCH_1HR_JSON,
                                                                    LocalDateTimeDuration.class);
        assertEquals(expected, actual);
    }

}
