package ch.niculin.notebook.domain.model.Note;

import java.time.LocalDate;

public class Note {

    private final NoteId noteId;
    private Content content;
    private final LocalDate created;
    private LocalDate updated;

    public Note(NoteId noteId, Content content, LocalDate created, LocalDate updated) {
        this.content = content;
        this.created = created;
        this.updated = updated;
        this.noteId = noteId;
    }


    public NoteId getNoteId() {
        return noteId;
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

    public LocalDate getUpdated() {
        return updated;
    }

    public void setUpdated(LocalDate updated) {
        this.updated = updated;
    }
}
