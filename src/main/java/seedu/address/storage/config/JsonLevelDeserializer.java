package seedu.address.storage.config;

import java.io.IOException;
import java.util.logging.Level;

import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.FromStringDeserializer;

public class JsonLevelDeserializer extends FromStringDeserializer<Level> {

    public JsonLevelDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    protected Level _deserialize(String value, DeserializationContext ctxt) throws IOException {
        return Level.parse(value);
    }

}
