package ch.niculin.notebook.domain.model;

import java.time.LocalDate;

public class Note {
    private NoteId noteId;
    private Content content;
    private LocalDate created;
    private LocalDate updated;

    public Note(NoteId noteId, Content content, LocalDate created, LocalDate updated) {
        this.noteId = noteId;
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

    public void setContent(Content content) {
        this.content = content;
    }

    public LocalDate getCreated() {
        return created;
    }

    public void setCreated(LocalDate created) {
        this.created = created;
    }

    public LocalDate getUpdated() {
        return updated;
    }

    public void setUpdated(LocalDate updated) {
        this.updated = updated;
    }
}
