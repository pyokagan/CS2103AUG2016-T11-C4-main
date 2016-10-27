package seedu.address.storage.config;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"logLevel", "userPrefsFilePath", "taskBookFilePath"})
public abstract class JsonConfigMixin {

}
