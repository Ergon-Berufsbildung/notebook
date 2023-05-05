package ch.niculin.notebook.infrastructure;

import ch.niculin.notebook.domain.model.Content;
import ch.niculin.notebook.domain.model.Note;
import ch.niculin.notebook.domain.model.NoteId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NoteRepositoryImplTest {

    private NoteRepositoryImpl noteRepository;




    @BeforeEach
    void init() throws IOException {
        Path path = Paths.get("./JsonFiles");
        Path tempFile = Files.createTempFile(path, null, ".json");
        this.noteRepository = new NoteRepositoryImpl(new File(tempFile.toUri()));
    }

    @Test
    void testAddDataToEmptyNotebook() {
        noteRepository.add(createNote("test3"));
        noteRepository.add(createNote("test4"));
        System.out.println("FilePath: " + noteRepository.getFile().getAbsolutePath());
    }

    @Test
    void testAddDataToNotebookWithContent() {
        noteRepository.add(createNote("Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet."));
        noteRepository.add(createNote("test2"));

        List<Note> repoBeforeAdd = noteRepository.loadAll();
        assertEquals(2, repoBeforeAdd.size());

        noteRepository.add(createNote("test3"));
        noteRepository.add(createNote("test4"));

        List<Note> repoWithContent = noteRepository.loadAll();
        assertEquals(4, repoWithContent.size());
    }

    @Test
    void testDelete() {
        //error here
        initNoteBook();
        noteRepository.delete(new NoteId(0));
        assertEquals(1, noteRepository.loadAll().size());
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