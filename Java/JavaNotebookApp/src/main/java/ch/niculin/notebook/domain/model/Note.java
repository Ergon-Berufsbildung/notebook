package ch.niculin.notebook.domain.model;

import ch.niculin.notebook.infrastructure.LocalDateDeserializer;
import ch.niculin.notebook.infrastructure.LocalDateSerializer;
import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.time.LocalDate;
import java.util.Objects;

//TODO custom serializer/deserializer: https://www.baeldung.com/jackson-object-mapper-tutorial#bd-Advanced
public class Note {
    private NoteId noteId;
    private final Content content;
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private final LocalDate created;
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private final LocalDate updated;


    @JsonCreator()
    public Note(@JsonProperty("content")
                Content content, @JsonProperty("created")
                LocalDate created, @JsonProperty("updated")
                LocalDate updated) {
        this.content = content;
        this.created = created;
        this.updated = updated;
    }

    public NoteId getNoteId() {
        return noteId;
    }

    public Content getContent() {
        return content;
    }

    public LocalDate getCreated() {
        return created;
    }

    public LocalDate getUpdated() {
        return updated;
    }

    @JsonSetter("noteId")
    public void setNoteId(NoteId noteId) {
        this.noteId = noteId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Note note = (Note) o;

        if (!Objects.equals(noteId, note.noteId)) return false;
        if (!Objects.equals(content, note.content)) return false;
        if (!Objects.equals(created, note.created)) return false;
        return Objects.equals(updated, note.updated);
    }

    @Override
    public int hashCode() {
        int result = noteId != null ? noteId.hashCode() : 0;
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (created != null ? created.hashCode() : 0);
        result = 31 * result + (updated != null ? updated.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Note{" +
               "noteId=" + noteId +
               ", content=" + content +
               ", created=" + created +
               ", updated=" + updated +
               '}';
    }
}
