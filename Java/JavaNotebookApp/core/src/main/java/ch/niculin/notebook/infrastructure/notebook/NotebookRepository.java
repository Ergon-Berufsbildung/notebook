package ch.niculin.notebook.infrastructure.notebook;

import ch.niculin.notebook.domain.model.notebook.Notebook;
import ch.niculin.notebook.domain.model.notebook.NotebookName;
import ch.niculin.notebook.domain.model.notebook.NotebookTO;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public interface NotebookRepository {

    void addNotebook(String name);

    void deleteNotebookByName(NotebookName notebookName);

    List<NotebookTO> getAllNotebooks();
}
