package ch.niculin.notebook.infrastructure.notebook;

import ch.niculin.notebook.domain.model.Note.Content;
import ch.niculin.notebook.domain.model.Note.Note;
import ch.niculin.notebook.domain.model.Note.NoteId;
import ch.niculin.notebook.domain.model.notebook.Notebook;
import ch.niculin.notebook.domain.model.notebook.NotebookId;
import ch.niculin.notebook.domain.model.notebook.NotebookName;
import ch.niculin.notebook.infrastructure.DateProviderImpl;
import ch.niculin.notebook.infrastructure.note.NoteTO;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class NotebookRepositoryImpl implements NotebookRepository {
    private final File file;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private int noteId = 0;
    private int notebookId = 0;

    public NotebookRepositoryImpl(File file) {
        this.file = file;
    }

    @Override
    public void addNotebook(String name) {
        NotebookTO notebookTO = new NotebookTO();
        notebookTO.setId(new NotebookId(notebookId));
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
        notebookId++;
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

    @Override
    public void addNote(Content content, NotebookName notebookName) {
        Set<NotebookTO> notebookTOS = getAllNotebooks();
        NoteTO noteTO = createNoteTO(content);
        noteId++;
        getListOfNotes(notebookTOS, notebookName)
                .add(noteTO);
        try {
            objectMapper.writeValue(file, notebookTOS.stream().toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<NoteTO> getAllNotes(NotebookName notebookName) {
        return getListOfNotes(getAllNotebooks(), notebookName);
    }

    @Override
    public void deleteNote(NotebookName notebookName, NoteId noteId) {
        Set<NotebookTO> notebookTOS = getAllNotebooks();
        getListOfNotes(notebookTOS, notebookName)
                .removeIf(noteTO -> noteTO.getNoteId().equals(noteId));
        try {
            objectMapper.writeValue(file, notebookTOS.stream().toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public NoteTO getNoteById(NotebookName notebookName, NoteId noteId) {
        return getListOfNotes(getAllNotebooks(), notebookName).stream()
                .filter(noteTO -> noteTO.getNoteId().equals(noteId))
                .findFirst()
                .orElseThrow();
    }

    private LinkedList<NoteTO> getListOfNotes(Set<NotebookTO> allNotebooks, NotebookName notebookName) {
        return allNotebooks.stream()
                .filter(notebookTO -> notebookTO.getName().equals(notebookName))
                .findFirst()
                .orElseThrow()
                .getListOfNotes();
    }


    @Override
    public void updateNoteContent(NotebookName notebookName, NoteId noteId, Content newContent) {
        Set<NotebookTO> notebookTOS = getAllNotebooks();

        getNoteTO(notebookName, noteId, notebookTOS)
                .setContent(newContent);
        getNoteTO(notebookName, noteId, notebookTOS)
                .setUpdated(new DateProviderImpl().getDate());

        try {
            objectMapper.writeValue(file, notebookTOS);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private static NoteTO getNoteTO(NotebookName notebookName, NoteId noteId, Set<NotebookTO> notebookTOS) {
        return notebookTOS.stream()
                .filter(notebookTO -> notebookTO.getName().equals(notebookName))
                .findFirst()
                .orElseThrow()
                .getListOfNotes()
                .stream()
                .filter(noteTO -> noteTO.getNoteId().equals(noteId))
                .findFirst()
                .orElseThrow();
    }

    private NoteTO createNoteTO(Content content) {
        return new NoteTO(new NoteId(noteId), content, new DateProviderImpl().getDate(), new DateProviderImpl().getDate());
    }

    private Note convertToNote(NoteTO noteTO) {
        return new Note(noteTO.getNoteId(), noteTO.getContent(), noteTO.getCreated(), noteTO.getUpdated());
    }
}