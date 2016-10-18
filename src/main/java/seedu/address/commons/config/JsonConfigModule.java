package seedu.address.commons.config;

import java.util.logging.Level;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleDeserializers;
import com.fasterxml.jackson.databind.module.SimpleSerializers;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

/**
 * Jackson module for serialization/deserialization of {@link Config} objects.
 */
public class JsonConfigModule extends Module {

    @Override
    public String getModuleName() {
        return "JsonConfigModule";
    }

    @Override
    public Version version() {
        return Version.unknownVersion();
    }

    @Override
    public void setupModule(SetupContext context) {
        final SimpleSerializers serializers = new SimpleSerializers();
        final SimpleDeserializers deserializers = new SimpleDeserializers();

        serializers.addSerializer(Level.class, new ToStringSerializer());
        deserializers.addDeserializer(Level.class, new JsonLevelDeserializer(Level.class));

        context.addSerializers(serializers);
        context.addDeserializers(deserializers);
        context.setMixInAnnotations(Config.class, JsonConfigMixin.class);
    }

}
