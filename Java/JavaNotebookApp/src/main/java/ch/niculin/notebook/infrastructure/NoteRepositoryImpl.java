package ch.niculin.notebook.infrastructure;

import ch.niculin.notebook.domain.model.Note;
import ch.niculin.notebook.domain.model.NoteId;

import java.util.ArrayList;
import java.util.List;

public class NoteRepositoryImpl implements NoteRepository {

    private final List<Note> notebook = new ArrayList<>();

    public NoteId getCurrentNoteId() {
        if (notebook.size() == 0) {
            return new NoteId(0);
        }
        return new NoteId(notebook.size());
    }

    @Override
    public void add(Note note) {
        note.setNoteId(getCurrentNoteId());
        notebook.add(note);
    }

    @Override
    public List<Note> loadAll() {
        return notebook;
    }

    @Override
    public void delete(NoteId id) {
        notebook.remove(getNoteById(id));
    }

    @Override
    public void update(Note note) {
        delete(getNoteById(note.getNoteId()).getNoteId());
        add(note);
    }

    private Note getNoteById(NoteId id) {
        return notebook.stream().filter(note -> note.getNoteId().equals(id)).findFirst().orElseThrow();
    }
}
