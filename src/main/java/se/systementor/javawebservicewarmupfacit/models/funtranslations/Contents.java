package se.systementor.javawebservicewarmupfacit.models.funtranslations;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "translated",
        "text",
        "translation"
})
public class Contents {

    @JsonProperty("translated")
    public String translated;
    @JsonProperty("text")
    public String text;
    @JsonProperty("translation")
    public String translation;

}