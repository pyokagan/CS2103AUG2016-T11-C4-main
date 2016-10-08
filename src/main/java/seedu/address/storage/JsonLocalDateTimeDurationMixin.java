package seedu.address.storage;

import java.time.LocalDateTime;
import java.time.temporal.TemporalUnit;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"start", "end"})
public abstract class JsonLocalDateTimeDurationMixin {

    public JsonLocalDateTimeDurationMixin(@JsonProperty("start") LocalDateTime start,
                                          @JsonProperty("end") LocalDateTime end) {
    }

    @JsonIgnore
    abstract List<TemporalUnit> getUnits();

}
