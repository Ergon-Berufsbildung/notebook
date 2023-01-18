package ch.niculin.notebook.domain.model;

import java.time.LocalDate;
import java.util.Objects;

public class Note {
    private NoteId noteId;
    private final Content content;
    private final LocalDate created;
    private final LocalDate updated;

    public Note(Content content, LocalDate created, LocalDate updated) {
        this.content = content;
        this.created = created;
        this.updated = updated;
    }

    public NoteId getNoteId() {
        return noteId;
    }

    public void setNoteId(NoteId noteId) {
        this.noteId = noteId;
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
}
