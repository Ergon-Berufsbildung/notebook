package ch.niculin.notebook.infrastructure;

import ch.niculin.notebook.domain.model.Note;
import ch.niculin.notebook.domain.model.NoteBook;
import ch.niculin.notebook.domain.model.NoteId;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.*;
import java.util.*;

public class NoteRepositoryImpl implements NoteRepository {

    private final File file;
    private final Map<String, ArrayNode> map = new HashMap<>();

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final ArrayNode parentArray = objectMapper.createArrayNode();


    public NoteRepositoryImpl(File file) {
        this.file = file;
        try {
            objectMapper.writeValue(
                    file, parentArray
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public NoteId getCurrentNoteId() {
        if (parentArray.size() == 0) {
            return new NoteId(0);
        }
        return new NoteId(parentArray.size());
    }


    @Override
    public void add(Note note) {
        ObjectNode jsonNote = objectMapper.createObjectNode();
        note.setNoteId(getCurrentNoteId());
        jsonNote.put("noteId", note.getNoteId().id());
        jsonNote.put("content", note.getContent().content());
        jsonNote.put("created", note.getCreated().toString());
        jsonNote.put("updated", note.getUpdated().toString());
        parentArray.add(jsonNote);
        map.put("notebook", parentArray);

        writeNoteBook();
    }

    private void writeNoteBook() {
        try {
            objectMapper.writeValue(
                    file, map
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Note> loadAll() {
        NoteBook notebook = new NoteBook();
        File file = this.file;

        try {
            notebook = objectMapper.readValue(file, NoteBook.class);
            System.out.println(notebook);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return List.of(notebook.getNotebook());
    }

    @Override
    public void delete(NoteId id) {
        NoteBook notebook;
        try {
            notebook = objectMapper.readValue(file, NoteBook.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        List<Note> notes = new ArrayList<>(Arrays.asList(notebook.getNotebook()));
        notes.removeIf(note -> note.getNoteId().id() == id.id());
        try {
            objectMapper.writeValue(file, notebook);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


    @Override
    public void update(Note note) {
/*        delete(getNoteById(note.getNoteId()).getNoteId());
        add(note);*/
    }

/*
    private Note getNoteById(NoteId id) {
        return notebook.stream().filter(note -> note.getNoteId().equals(id)).findFirst().orElseThrow();
    }
*/

    public File getFile() {
        return file;
    }
}
