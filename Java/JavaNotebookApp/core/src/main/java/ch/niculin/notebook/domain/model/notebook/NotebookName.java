package ch.niculin.notebook.domain.model.notebook;

public record NotebookName(String name) {

    public NotebookName {
        if (name.isEmpty()) {
            throw new IllegalArgumentException("Your Notebook needs a name");
        }
    }

    public String name() {
        return name;
    }
}
