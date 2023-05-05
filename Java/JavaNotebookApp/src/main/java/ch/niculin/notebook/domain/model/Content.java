package ch.niculin.notebook.domain.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

public record Content(String content) {
    @Override
    @JsonValue
    @JsonProperty("content")
    public String content() {
        return content;
    }
}
