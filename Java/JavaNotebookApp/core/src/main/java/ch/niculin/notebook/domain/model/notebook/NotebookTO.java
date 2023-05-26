package ch.niculin.notebook.domain.model.notebook;

import ch.niculin.notebook.domain.model.Note.NoteTO;
import ch.niculin.notebook.infrastructure.notebook.NotebookDeserializer;
import ch.niculin.notebook.infrastructure.notebook.NotebookSerializer;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.LinkedList;

@JsonSerialize(using = NotebookSerializer.class)
@JsonDeserialize(using = NotebookDeserializer.class)
public class NotebookTO {
    @JsonProperty("id")
    private NotebookId id;

    @JsonProperty("name")
    private NotebookName name;

    @JsonProperty("listOfNotes")
    private LinkedList<NoteTO> listOfNotes;

    public NotebookId getId() {
        return id;
    }

    public void setId(NotebookId id) {
        this.id = id;
    }

    public NotebookName getName() {
        return name;
    }

    public void setName(NotebookName name) {
        this.name = name;
    }

    public LinkedList<NoteTO> getListOfNotes() {
        return listOfNotes;
    }

    public void setListOfNotes(LinkedList<NoteTO> listOfNotes) {
        this.listOfNotes = listOfNotes;
    }

    @Override
    public String toString() {
        return "NotebookTO{" +
               "id=" + id +
               ", name=" + name +
               ", listOfNotes=" + listOfNotes +
               '}';
    }
}
