package ch.niculin.notebook.infrastructure.note;

import ch.niculin.notebook.domain.model.Note.Content;
import ch.niculin.notebook.domain.model.Note.NoteId;
import ch.niculin.notebook.domain.model.Note.NoteTO;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.time.LocalDate;

public class NoteDeserializer extends JsonDeserializer<NoteTO> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public NoteTO deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        objectMapper.setConfig(deserializationContext.getConfig());
        JsonNode node = objectMapper.readTree(jsonParser);
        NoteTO noteTO = new NoteTO();
        noteTO.setNoteId(new NoteId(node.get("id").asInt()));
        noteTO.setContent(new Content(node.get("content").asText()));
        noteTO.setCreated(LocalDate.parse(node.get("created").asText()));
        noteTO.setUpdated(LocalDate.parse(node.get("updated").asText()));
        return noteTO;
    }
}
