package ch.niculin.notebook.infrastructure.notebook;

import ch.niculin.notebook.domain.model.Note.NoteTO;
import ch.niculin.notebook.domain.model.notebook.NotebookId;
import ch.niculin.notebook.domain.model.notebook.NotebookName;
import ch.niculin.notebook.domain.model.notebook.NotebookTO;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.LinkedList;

public class NotebookDeserializer extends JsonDeserializer<NotebookTO> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public NotebookTO deserialize(JsonParser jsonParser, DeserializationContext ctxt) throws IOException, JsonProcessingException {

        objectMapper.setConfig(ctxt.getConfig());
        JsonNode node = objectMapper.readTree(jsonParser);
        NotebookTO notebookTO = new NotebookTO();
        notebookTO.setId(new NotebookId(node.get("id").asInt()));
        notebookTO.setName(new NotebookName(node.get("name").asText()));

        JsonNode listOfNotesNode = node.get("listOfNotes");
        if (listOfNotesNode != null && listOfNotesNode.isArray()) {
            LinkedList<NoteTO> listOfNotes = new LinkedList<>();
            for (JsonNode noteNode : listOfNotesNode) {
                NoteTO noteTO = objectMapper.readValue(noteNode.traverse(), NoteTO.class);
                listOfNotes.add(noteTO);
            }
            notebookTO.setListOfNotes(listOfNotes);
        }

        return notebookTO;
    }
}
