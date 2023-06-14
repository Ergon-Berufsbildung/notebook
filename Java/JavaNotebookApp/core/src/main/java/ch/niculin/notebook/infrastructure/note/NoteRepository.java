package ch.niculin.notebook.infrastructure.note;

import ch.niculin.notebook.domain.model.Note.NoteId;
import ch.niculin.notebook.domain.model.notebook.NotebookName;

import java.util.List;

public interface NoteRepository {
    void add(String content, NotebookName nameOfNotebook);

    List<NoteTO> loadAll();

    void delete(NoteId id);

    void update(NoteTO noteTO, String updatedContent);
}
