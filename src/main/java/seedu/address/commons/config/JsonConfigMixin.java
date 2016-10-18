package seedu.address.commons.config;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"logLevel", "userPrefsFilePath", "taskBookFilePath"})
public abstract class JsonConfigMixin {

}
