package ch.niculin.notebook.domain.model;

public class NoteBook {
    private String name;
    private Note[] notebook;

    public Note[] getNotebook() {
        return notebook;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNotebook(Note[] notebook) {
        this.notebook = notebook;
    }
}
