package ch.niculin.notebook.infrastructure.note;

import ch.niculin.notebook.domain.model.Note.Content;
import ch.niculin.notebook.domain.model.Note.NoteId;
import ch.niculin.notebook.infrastructure.LocalDateDeserializer;
import ch.niculin.notebook.infrastructure.LocalDateSerializer;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.time.LocalDate;
import java.util.Objects;

@JsonSerialize(using = NoteSerializer.class)
@JsonDeserialize(using = NoteDeserializer.class)
public class NoteTO {
    private NoteId noteId;
    private Content content;
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate created;
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate updated;

    public NoteTO() {
    }

    @JsonCreator()
    public NoteTO(@JsonProperty("content")
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

    public void setNoteId(NoteId noteId) {
        this.noteId = noteId;
    }

    public void setContent(Content content) {
        this.content = content;
    }

    public void setCreated(LocalDate created) {
        this.created = created;
    }

    public void setUpdated(LocalDate updated) {
        this.updated = updated;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NoteTO noteTO = (NoteTO) o;

        if (!Objects.equals(noteId, noteTO.noteId)) return false;
        if (!Objects.equals(content, noteTO.content)) return false;
        if (!Objects.equals(created, noteTO.created)) return false;
        return Objects.equals(updated, noteTO.updated);
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
