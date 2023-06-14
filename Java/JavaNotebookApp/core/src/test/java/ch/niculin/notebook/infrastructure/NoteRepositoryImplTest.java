package ch.niculin.notebook.infrastructure;

import ch.niculin.notebook.domain.model.Note.Content;
import ch.niculin.notebook.infrastructure.note.NoteTO;
import ch.niculin.notebook.domain.model.Note.NoteId;
import ch.niculin.notebook.infrastructure.note.NoteRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class NoteRepositoryImplTest {

    private NoteRepositoryImpl noteRepository;
    private static final String DEFAULT_NOTEBOOK = "notebook.json";

    Path path;
    File file;

    @TempDir
    Path tempdir;

    @BeforeEach
    void init() {
        try {
            path = tempdir.resolve("notebook.json");
        } catch (InvalidPathException ipe) {
            System.err.println("error creating temporary test file in " +
                               this.getClass().getSimpleName());
        }
        System.out.println(path.toString());
        file = path.toFile();
        this.noteRepository = new NoteRepositoryImpl(file);
    }

    @Test
    void testAddDataToEmptyNotebook() {
        String expected = getExpectedFile();
        String actual;


        noteRepository.add("test3", "test");
        noteRepository.add("test4", "test2");

        actual = getActual();

        assertEquals(expected, actual);
    }

    @Test
    void testAddDataToNotebookWithContent() {
        noteRepository.add("test3", "name");
        List<NoteTO> repoBeforeAdd = noteRepository.loadAll();

        noteRepository.add("test4", "name2");

        List<NoteTO> repoAfterAdd = noteRepository.loadAll();

        assertEquals(getExpectedFile(), getActual());
        assertNotEquals(repoAfterAdd.size(), repoBeforeAdd.size());
    }

    @Test
    void testDelete() {
        noteRepository.add("test3", "test");
        noteRepository.add("test3", "test");
        noteRepository.add("test3", "test");



        noteRepository.delete(new NoteId(2));
        assertEquals(getExpectedFile().getBytes(StandardCharsets.UTF_8).length, getActual().getBytes(StandardCharsets.UTF_8).length);
    }

    @Test
    void testUpdate() {
        List<NoteTO> notebook = loadAllNotes();
        NoteTO noteTOToUpdate = new NoteTO(new Content("I am updated"), getNoteById(new NoteId(1), notebook).getCreated(), LocalDate.now());
        noteTOToUpdate.setNoteId(new NoteId(1));
        noteRepository.update(noteTOToUpdate, noteTOToUpdate.getContent().content());
        assertEquals("I am updated", getNoteById(new NoteId(1), noteRepository.loadAll()).getContent().content());
    }

    private List<NoteTO> loadAllNotes() {
        return noteRepository.loadAll();
    }

    private NoteTO getNoteById(NoteId id, List<NoteTO> notebook) {
        return notebook.stream().filter(note -> note.getNoteId().equals(id)).findFirst().orElseThrow();
    }

    private static String getExpectedFile() {
        String expected;
        try {
            expected = new String(Files.readAllBytes(Path.of("src/test/resources/testdata/" + NoteRepositoryImplTest.DEFAULT_NOTEBOOK)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return expected;
    }

    private String getActual() {
        String actual;
        try {
            actual = new String(Files.readAllBytes(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return actual;
    }
}