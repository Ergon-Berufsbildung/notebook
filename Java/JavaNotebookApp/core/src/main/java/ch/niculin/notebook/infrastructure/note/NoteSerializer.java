package ch.niculin.notebook.infrastructure.note;

import ch.niculin.notebook.domain.model.Note.NoteTO;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

public class NoteSerializer extends JsonSerializer<NoteTO> {
    @Override
    public void serialize(NoteTO noteTO, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        gen.writeNumberField("id", noteTO.getNoteId().id());
        gen.writeStringField("content", noteTO.getContent().content());
        gen.writeStringField("created", noteTO.getCreated().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        gen.writeStringField("updated", noteTO.getUpdated().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        gen.writeEndObject();
    }
}
