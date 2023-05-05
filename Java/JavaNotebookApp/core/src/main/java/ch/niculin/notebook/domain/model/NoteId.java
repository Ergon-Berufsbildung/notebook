package ch.niculin.notebook.domain.model;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

public record NoteId(int id) {

    public NoteId {
        if (id < 0) {
            throw new IllegalArgumentException("Id can't be lower than 0");
        }
    }
    @Override
    @JsonValue
    @JsonProperty("id")
    public int id() {
        return id;
    }
}
