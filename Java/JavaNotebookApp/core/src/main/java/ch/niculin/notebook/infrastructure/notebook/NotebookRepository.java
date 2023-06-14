package ch.niculin.notebook.infrastructure.notebook;

import ch.niculin.notebook.domain.model.notebook.NotebookName;

import java.util.Set;

public interface NotebookRepository {

    void addNotebook(String name);

    void deleteNotebookByName(NotebookName notebookName);

    Set<NotebookTO> getAllNotebooks();

    NotebookTO getNotebookByName(NotebookName notebookName);
}
