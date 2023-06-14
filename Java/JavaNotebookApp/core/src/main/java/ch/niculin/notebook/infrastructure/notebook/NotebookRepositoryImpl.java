package ch.niculin.notebook.infrastructure.notebook;

import ch.niculin.notebook.domain.model.Note.Note;
import ch.niculin.notebook.domain.model.notebook.Notebook;
import ch.niculin.notebook.domain.model.notebook.NotebookId;
import ch.niculin.notebook.domain.model.notebook.NotebookName;
import ch.niculin.notebook.infrastructure.note.NoteTO;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.io.File;
import java.io.IOException;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Set;
import java.util.TreeSet;

public class NotebookRepositoryImpl implements NotebookRepository {
    private final File file;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public NotebookRepositoryImpl(File file) {
        this.file = file;
    }

    @Override
    public void addNotebook(String name) {
        NotebookTO notebookTO = new NotebookTO();
        notebookTO.setId(new NotebookId(getCurrentId()));
        notebookTO.setName(new NotebookName(name));
        notebookTO.setListOfNotes(new LinkedList<>());

        SimpleModule simpleModule = new SimpleModule("NotebookSerializer", new Version(1, 0, 0, null, null, null));
        simpleModule.addSerializer(NotebookTO.class, new NotebookSerializer());
        objectMapper.registerModule(simpleModule);
        Set<NotebookTO> notebookTOS = getAllNotebooks();
        notebookTOS.add(notebookTO);
        try {
            objectMapper.writeValue(file, notebookTOS.stream().toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteNotebookByName(NotebookName notebookName) {
        Set<NotebookTO> notebookTOS = getAllNotebooks();
        if (!notebookTOS.removeIf(notebookTO -> notebookTO.getName().equals(notebookName))) {
            System.err.println("Notebook not found");
        }
        try {
            objectMapper.writeValue(file, notebookTOS.stream().toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public Set<NotebookTO> getAllNotebooks() {
        Set<NotebookTO> notebooks;
        try {
            notebooks = objectMapper.readValue(file, new TypeReference<Set<NotebookTO>>() {
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Set<NotebookTO> sortedNotebooks = new TreeSet<>(Comparator.comparing(notebookTO -> notebookTO.getId().id()));
        sortedNotebooks.addAll(notebooks);
        return sortedNotebooks;
    }

    @Override
    public NotebookTO getNotebookByName(NotebookName name) {
        return getAllNotebooks().stream().filter(notebookTO -> notebookTO.getName().equals(name)).findFirst().orElseThrow();
    }

    private Notebook convertToNotebook(NotebookTO notebookTO) {
        Notebook notebook = new Notebook();

        notebook.setNotebookId(notebookTO.getId());
        notebook.setNotebookName(notebookTO.getName());
        LinkedList<Note> notes = new LinkedList<>();
        notebookTO.getListOfNotes().forEach(noteTO -> notes.add(convertToNote(noteTO)));
        notebook.setNotes(notes);
        return notebook;

    }

    private Note convertToNote(NoteTO noteTO) {
        return new Note(noteTO.getNoteId(), noteTO.getContent(), noteTO.getCreated(), noteTO.getUpdated());
    }

    private int getCurrentId() {
        return getAllNotebooks().size();
    }
}
