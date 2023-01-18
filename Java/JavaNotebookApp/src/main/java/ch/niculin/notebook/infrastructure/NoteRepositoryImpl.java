package ch.niculin.notebook.infrastructure;

import ch.niculin.notebook.domain.model.Content;
import ch.niculin.notebook.domain.model.Note;
import ch.niculin.notebook.domain.model.NoteId;

import java.util.ArrayList;
import java.util.List;

public class NoteRepositoryImpl implements NoteRepository {



    private final List<Note> notebook = new ArrayList<>();

    public NoteId getCurrentNoteId(){
        if (notebook.size() != 0){
            return new NoteId(notebook.get(notebook.size() - 1).getNoteId().getId() + 1);
        } else {
            return new NoteId(0);
        }

    }

    @Override
    public void add(Note note) {
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
    public void update(NoteId noteId, Content content) {
        notebook.get(getNoteById(noteId).getNoteId().getId()).setContent(content);
    }

    public Note getNoteById(NoteId id) {
        return notebook.stream().filter(note -> note.getNoteId().equals(id) ).findFirst().orElseThrow();
    }
}
