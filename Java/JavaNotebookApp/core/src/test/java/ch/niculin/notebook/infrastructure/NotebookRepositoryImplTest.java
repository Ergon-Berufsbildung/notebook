package ch.niculin.notebook.infrastructure;

import ch.niculin.notebook.domain.model.notebook.NotebookTO;
import ch.niculin.notebook.infrastructure.notebook.NotebookRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.nio.file.Path;

class NotebookRepositoryImplTest {

    private NotebookRepositoryImpl notebookRepository;
    private static final String DEFAULT_NOTEBOOK = "notebooks.json";



    Path path;
    File file;

    @TempDir
    Path tempdir;

    @BeforeEach
    void init(){
        path = Path.of("src/test/resources/testdata/notebooks.json");
        file = path.toFile();
        notebookRepository = new NotebookRepositoryImpl(file);
    }

    @Test
    void loadAllNotes(){
        notebookRepository.getAllNotebooks();
    }

    @Test
    void addEmptyNotebook(){
        notebookRepository.addNotebook("test");
    }

}