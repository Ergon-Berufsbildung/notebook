package ch.niculin.notebook.infrastructure;

import ch.niculin.notebook.domain.model.Note.Content;
import ch.niculin.notebook.domain.model.Note.NoteId;
import ch.niculin.notebook.domain.model.notebook.NotebookId;
import ch.niculin.notebook.domain.model.notebook.NotebookName;
import ch.niculin.notebook.infrastructure.note.NoteTO;
import ch.niculin.notebook.infrastructure.notebook.NotebookRepositoryImpl;
import ch.niculin.notebook.infrastructure.notebook.NotebookTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NotebookRepositoryImplTest {

    private NotebookRepositoryImpl notebookRepository;
    private static final String DEFAULT_NOTEBOOK = "notebooks.json";
    private static final String NOTEBOOK1 = "notebook.json";


    Path path;
    File file;

    @TempDir
    Path tempdir;

    @BeforeEach
    void init() {
        try {
            path = Files.createFile(tempdir.resolve("mainNotebook"));
            Files.writeString(path, "[]");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        file = path.toFile();
        System.out.println("File created in: " + path);
        notebookRepository = new NotebookRepositoryImpl(file);
    }

    @Test
    void addEmptyNotebook() {
        notebookRepository.addNotebook("test1");
        notebookRepository.addNotebook("test2");
        assertEquals(getExpectedFile(), getActual());
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

    @Test
    void deleteNotebook() {
        notebookRepository.addNotebook("test1");
        notebookRepository.addNotebook("test2");
        notebookRepository.addNotebook("test3");
        notebookRepository.deleteNotebookByName(new NotebookName("test3"));
        assertEquals(getExpectedFile(), getActual());
    }

    @Test
    void getNotebookByName() {
        NotebookTO expectedNotebook = getExpectedNotebook();
        notebookRepository.addNotebook("test1");
        notebookRepository.addNotebook("test2");
        NotebookTO notebookTO = notebookRepository.getNotebookByName(new NotebookName("test1"));
        assertEquals(expectedNotebook, notebookTO);
    }

    @Test
    void addNote() {
        String expectedFile = getExpectedFileNote();
        initNotebook();
        assertEquals(expectedFile, getActual());
    }

    private void initNotebook() {
        notebookRepository.addNotebook("test1");
        notebookRepository.addNote(new Content("content1"), new NotebookName("test1"));
        notebookRepository.addNote(new Content("content2"), new NotebookName("test1"));
    }

    @Test
    void getAllNotes() {
        initNotebook();
        List<NoteTO> expected = new ArrayList<>();
        expected.add(new NoteTO(new NoteId(0), new Content("content1"), LocalDate.now(), LocalDate.now()));
        expected.add(new NoteTO(new NoteId(1), new Content("content2"), LocalDate.now(), LocalDate.now()));

        List<NoteTO> actual = notebookRepository.getAllNotes(new NotebookName("test1"));

        assertEquals(expected, actual);
    }

    @Test
    void deleteNote() {
        initNotebook();
        notebookRepository.addNote(new Content("content3"), new NotebookName("test1"));

        notebookRepository.deleteNote(new NotebookName("test1"), new NoteId(2));

        assertEquals(getExpectedFileNote(), getActual());
    }

    @Test
    void getNoteById() {
        initNotebook();
        NoteTO noteTO = new NoteTO(new NoteId(0), new Content("content1"), LocalDate.now(), LocalDate.now());

        NoteTO actual = notebookRepository.getNoteById(new NotebookName("test1"), new NoteId(0));

        assertEquals(noteTO, actual);
    }

    @Test
    void updateNoteContent() {
        notebookRepository.addNotebook("test1");
        notebookRepository.addNote(new Content("content1"), new NotebookName("test1"));
        notebookRepository.addNote(new Content("nnieiasdf"), new NotebookName("test1"));

        notebookRepository.updateNoteContent(new NotebookName("test1"), new NoteId(1), new Content("content2"));

        assertEquals(getExpectedFileNote(), getActual());
    }

    private static NotebookTO getExpectedNotebook() {
        NotebookTO expectedNotebook = new NotebookTO();
        expectedNotebook.setId(new NotebookId(0));
        expectedNotebook.setName(new NotebookName("test1"));
        expectedNotebook.setListOfNotes(new LinkedList<>());
        return expectedNotebook;
    }

    private static String getExpectedFile() {
        String expected;
        try {
            expected = new String(Files.readAllBytes(Path.of("src/test/resources/testdata/" + NotebookRepositoryImplTest.DEFAULT_NOTEBOOK))).replace("\n", "").replace("\r", "");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return expected.replaceAll("\\s", "");
    }

    private static String getExpectedFileNote() {
        String expected;
        try {
            expected = new String(Files.readAllBytes(Path.of("src/test/resources/testdata/" + NotebookRepositoryImplTest.NOTEBOOK1))).replace("\n", "").replace("\r", "");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return expected.replaceAll("\\s", "");
    }
}