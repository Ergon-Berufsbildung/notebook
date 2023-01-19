package ch.niculin.notebook.domain.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class NoteTest {

    @Test
    void testValidNote() {
        Note note = new Note(new Content("asdfasfcf"), LocalDate.now(), LocalDate.now());
        note.setNoteId(new NoteId(1));
        int result = note.getNoteId().id();
        assertEquals(1, result );
    }

    @Test
    void testInvalidNote() {
        Note note = new Note(new Content("asdfasfcf"), LocalDate.now(), LocalDate.now());
        Exception exception = assertThrows(NullPointerException.class, () -> note.getNoteId().id() );
        assertEquals("Cannot invoke \"ch.niculin.notebook.domain.model.NoteId.id()\" because the return value of \"ch.niculin.notebook.domain.model.Note.getNoteId()\" is null", exception.getMessage());
    }
}