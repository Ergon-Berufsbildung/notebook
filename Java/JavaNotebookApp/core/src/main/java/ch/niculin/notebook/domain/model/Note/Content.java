package ch.niculin.notebook.domain.model.Note;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

public record Content(String content) {
    @Override
    @JsonValue
    @JsonProperty("content")
    public String content() {
        return content;
    }
}
