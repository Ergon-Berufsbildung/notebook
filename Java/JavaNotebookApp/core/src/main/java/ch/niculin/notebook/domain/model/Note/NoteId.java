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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NoteId noteId = (NoteId) o;

        return id == noteId.id;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
