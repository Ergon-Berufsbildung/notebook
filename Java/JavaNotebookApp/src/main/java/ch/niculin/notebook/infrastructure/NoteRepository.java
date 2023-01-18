package ch.niculin.notebook.infrastructure;

import ch.niculin.notebook.domain.model.Note;
import ch.niculin.notebook.domain.model.NoteId;

import java.util.List;

public interface NoteRepository {
    void add(Note note);

    List<Note> loadAll();

    void delete(NoteId id);

    void update(Note note);
}
