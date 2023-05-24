package ch.niculin.notebook.infrastructure;

import ch.niculin.notebook.domain.model.NoteTO;
import ch.niculin.notebook.domain.model.NoteId;

import java.util.List;

public interface NoteRepository {
    void add(NoteTO noteTO);

    List<NoteTO> loadAll();

    void delete(NoteId id);

    void update(NoteTO noteTO, String updatedContent);
}
