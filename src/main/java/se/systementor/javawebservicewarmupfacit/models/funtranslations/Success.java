package se.systementor.javawebservicewarmupfacit.models.funtranslations;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "total"
})
public class Success {

    @JsonProperty("total")
    public Integer total;

}
