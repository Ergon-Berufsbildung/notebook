package ch.niculin.notebook.infrastructure.notebook;

import ch.niculin.notebook.domain.model.Note.Content;
import ch.niculin.notebook.domain.model.Note.NoteId;
import ch.niculin.notebook.domain.model.notebook.NotebookName;
import ch.niculin.notebook.infrastructure.note.NoteTO;

import java.util.List;
import java.util.Set;

public interface NotebookRepository {

    void addNotebook(String name);

    void deleteNotebookByName(NotebookName notebookName);

    Set<NotebookTO> getAllNotebooks();

    NotebookTO getNotebookByName(NotebookName notebookName);

    void addNote(Content content, NotebookName notebookName);

    List<NoteTO> getAllNotes(NotebookName notebookName);

    void deleteNote(NotebookName notebookName, NoteId noteId);

    NoteTO getNoteById(NotebookName notebookName, NoteId noteId);

    void updateNoteContent(NotebookName notebookName, NoteId noteId, Content newContent);
}
