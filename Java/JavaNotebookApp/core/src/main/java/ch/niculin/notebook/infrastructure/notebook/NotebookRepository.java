package ch.niculin.notebook.infrastructure.notebook;

import ch.niculin.notebook.domain.model.notebook.NotebookName;

import java.util.List;

public interface NotebookRepository {

    void addNotebook(String name);

    void deleteNotebookByName(NotebookName notebookName);

    List<NotebookTO> getAllNotebooks();
}
