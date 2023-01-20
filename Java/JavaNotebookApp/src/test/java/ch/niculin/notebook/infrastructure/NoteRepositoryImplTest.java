package ch.niculin.notebook.infrastructure;

import ch.niculin.notebook.domain.model.Content;
import ch.niculin.notebook.domain.model.Note;
import ch.niculin.notebook.domain.model.NoteId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NoteRepositoryImplTest {

    private NoteRepositoryImpl noteRepository;

    @BeforeEach
    void init(){
        List<Note> notebook = new ArrayList<>();
        this.noteRepository = new NoteRepositoryImpl(notebook);
    }

    @Test
    void testAddDataToEmptyNotebook() {
        List<Note> emptyRepo = loadAllNotes();
        assertEquals(0, emptyRepo.size());

        noteRepository.add(createNote("test1"));
        noteRepository.add(createNote("test2"));

        List<Note> repoWithContent = loadAllNotes();
        assertEquals(2, repoWithContent.size());
    }

    @Test
    void testAddDataToNotebookWithContent() {
        noteRepository.add(createNote("test1"));
        noteRepository.add(createNote("test2"));

        List<Note> emptyRepo = loadAllNotes();
        assertEquals(2, emptyRepo.size());

        noteRepository.add(createNote("test3"));
        noteRepository.add(createNote("test4"));

        List<Note> repoWithContent = loadAllNotes();
        assertEquals(4, repoWithContent.size());
    }

    @Test
    void testDelete() {
        initNoteBook();
        noteRepository.delete(new NoteId(0));
        assertEquals(noteRepository.loadAll().size(), 1);
    }

    @Test
    void testUpdate() {
        initNoteBook();
        List<Note> notebook = loadAllNotes();
        Note noteToUpdate = new Note(new Content("I am updated"), getNoteById(new NoteId(1), notebook).getCreated(), LocalDate.now());
        noteToUpdate.setNoteId(new NoteId(1));
        noteRepository.update(noteToUpdate);
        assertEquals("I am updated", getNoteById(new NoteId(1), noteRepository.loadAll()).getContent().content());
    }

    private List<Note> loadAllNotes() {
        return noteRepository.loadAll();
    }

    private void initNoteBook() {
        noteRepository.add(createNote("test1"));
        noteRepository.add(createNote("test2"));
    }

    private Note createNote(String content) {
        return new Note(new Content(content), LocalDate.now(), LocalDate.now());
    }

    private Note getNoteById(NoteId id, List<Note> notebook) {
        return notebook.stream().filter(note -> note.getNoteId().equals(id)).findFirst().orElseThrow();
    }
}