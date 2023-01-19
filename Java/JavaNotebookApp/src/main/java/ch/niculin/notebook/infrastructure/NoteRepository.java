package ch.niculin.notebook.infrastructure;

import ch.niculin.notebook.domain.model.Note;
import ch.niculin.notebook.domain.model.NoteId;

import java.util.List;

/**
 *
 */
public interface NoteRepository {
    /**
     * add Notes to the notebook
     * @param note
     *
     */
    void add(Note note);

    /**
     * load all notes from notebook
     * @return list with all notes
     */
    List<Note> loadAll();

    /**
     * Deletes a note from notebook by noteId
     * @param id
     */
    void delete(NoteId id);

    /**
     * Updates note in a notebook
     * @param note
     */
    void update(Note note);
}
