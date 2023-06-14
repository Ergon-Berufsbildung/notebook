package ch.niculin.notebook.infrastructure.notebook;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class NotebookSerializer extends JsonSerializer<NotebookTO> {
    @Override
    public void serialize(NotebookTO notebookTO, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        gen.writeNumberField("id", notebookTO.getId().id());
        gen.writeStringField("name", notebookTO.getName().name());
        gen.writeObjectField("listOfNotes", notebookTO.getListOfNotes());
        gen.writeEndObject();
    }
}
