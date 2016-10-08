package seedu.address.storage;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import seedu.address.commons.time.LocalDateTimeDuration;
import seedu.address.model.task.Name;

@JsonPropertyOrder({"name", "start", "end"})
public abstract class JsonEventTaskMixin {

    JsonEventTaskMixin(@JsonProperty("name") Name name, @JsonProperty("start") LocalDateTime start,
                       @JsonProperty("end") LocalDateTime end) {
    }

    @JsonIgnore
    abstract LocalDateTimeDuration getDuration();

}
