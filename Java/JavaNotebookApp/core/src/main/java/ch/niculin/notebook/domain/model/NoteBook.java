package ch.niculin.notebook.domain.model;

import java.util.LinkedList;

public class NoteBook {
    private LinkedList<NoteTO> notebook;

    public LinkedList<NoteTO> getNotebook() {
        return notebook;
    }

    public void setNotebook(LinkedList<NoteTO> notebook) {
        this.notebook = notebook;
    }
}
