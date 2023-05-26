package ch.niculin.notebook.domain.model;

import ch.niculin.notebook.domain.model.Note.NoteId;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NoteTOIdTest {

    @Test
    void testValidNoteId() {
        NoteId noteId = new NoteId(42);
        int result = noteId.id();
        assertEquals(42, result);
    }

    @Test
    void testInvalidNoteId() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new NoteId(-42));
        assertEquals("Id can't be lower than 0", exception.getMessage());
    }
}