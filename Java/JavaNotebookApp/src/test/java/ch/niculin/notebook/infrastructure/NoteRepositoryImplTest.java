package ch.niculin.notebook.infrastructure;

import ch.niculin.notebook.domain.model.Content;
import ch.niculin.notebook.domain.model.Note;
import ch.niculin.notebook.domain.model.NoteId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NoteRepositoryImplTest {

    NoteRepositoryImpl noteRepository;

    @BeforeEach
    void init() {
        noteRepository = new NoteRepositoryImpl();
    }

    @Test
    void add() {
        int sizeBefore = noteRepository.loadAll().size() + 2;
        initNoteBook();
        int sizeAfter = noteRepository.loadAll().size();
        assertEquals(sizeBefore, sizeAfter);
    }

    @Test
    void delete() {
        initNoteBook();
        noteRepository.delete(new NoteId(0));
        assertEquals(noteRepository.loadAll().size(), 1);
    }

    @Test
    void update() {
        initNoteBook();
        Content contentToUpdate = new Content("I am updated!");
        noteRepository.update(new NoteId(1), contentToUpdate);
        assertEquals(noteRepository.getNoteById(new NoteId(1)).getContent().getContent(), contentToUpdate.getContent());
    }

    private void initNoteBook() {
        noteRepository.add(new Note(new NoteId(noteRepository.getCurrentNoteId().getId()), new Content("test1"), LocalDate.now(), LocalDate.now()));
        noteRepository.add(new Note(new NoteId(noteRepository.getCurrentNoteId().getId()), new Content("test2"), LocalDate.now(), LocalDate.now()));
    }
}