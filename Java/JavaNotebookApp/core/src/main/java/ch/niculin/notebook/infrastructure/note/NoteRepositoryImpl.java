package ch.niculin.notebook.infrastructure.note;

import ch.niculin.notebook.domain.model.notebook.Notebook;
import ch.niculin.notebook.domain.model.Note.NoteId;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.List;

public class NoteRepositoryImpl implements NoteRepository {

    private final File file;
    private final LinkedHashMap<String, ArrayNode> map = new LinkedHashMap<>();

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
    public void add(String content, String nameOfNotebook) {
        ObjectNode jsonNote = objectMapper.createObjectNode();
        NoteTO noteTO = new NoteTO();
        noteTO.setNoteId(getCurrentNoteId());
        jsonNote.put("noteId", noteTO.getNoteId().id());
        jsonNote.put("content", noteTO.getContent().content());
        jsonNote.put("created", noteTO.getCreated().toString());
        jsonNote.put("updated", noteTO.getUpdated().toString());
        map.put("notebook", parentArray);
        System.out.println(noteTO);
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
    public List<NoteTO> loadAll() {
        return null;
/*        Notebook notebook = new Notebook();
        File file = this.file;

        try {
            notebook = objectMapper.readValue(file, Notebook.class);
            System.out.println(notebook);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return notebook.getNotes();*/
    }

    @Override
    public void delete(NoteId id) {
        Notebook notebook;
        try {
            notebook = objectMapper.readValue(file, Notebook.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        notebook.getNotes().removeIf(note -> note.getNoteId().id() == id.id());
        try {
            objectMapper.writeValue(file, notebook);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void update(NoteTO noteTO, String updatedContent) {
        try {
            String jsonString = new String(Files.readAllBytes(Paths.get("data.json")));
            JsonObject json = new JsonObject(jsonString);
            JsonArray notebook = json.getJsonArray("notebook");

            JsonObject firstNote = notebook.getJsonObject(noteTO.getNoteId().id());
            firstNote.put("content", "new content");

            FileWriter fileWriter = new FileWriter("data.json");
            fileWriter.write(json.toString());
            fileWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

/*
    private Note getNoteById(NoteId id) {
        return notebook.stream().filter(note -> note.getNoteId().equals(id)).findFirst().orElseThrow();
    }
*/
}
