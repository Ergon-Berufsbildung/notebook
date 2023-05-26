package ch.niculin.notebook.domain.model.notebook;

import ch.niculin.notebook.domain.model.Note.Note;
import ch.niculin.notebook.domain.model.Note.NoteTO;

import java.util.LinkedList;

public class Notebook {
    private NotebookId notebookId;
    private NotebookName notebookName;
    private LinkedList<Note> notes;

    public LinkedList<Note> getNotes() {
        return notes;
    }

    public void setNotes(LinkedList<Note> notes) {
        this.notes = notes;
    }

    public NotebookId getNotebookId() {
        return notebookId;
    }

    public void setNotebookId(NotebookId notebookId) {
        this.notebookId = notebookId;
    }

    public NotebookName getNotebookName() {
        return notebookName;
    }

    public void setNotebookName(NotebookName notebookName) {
        this.notebookName = notebookName;
    }
}
