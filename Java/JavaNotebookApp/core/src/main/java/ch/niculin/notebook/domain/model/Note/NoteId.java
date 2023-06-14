package ch.niculin.notebook.domain.model.Note;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

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
