package ch.niculin.notebook.domain.model.notebook;

public record NotebookId (int id){
    public NotebookId {
        if (id < 0) {
            throw new IllegalArgumentException("Id can't be lower than 0");
        }
    }

    public int id(){
        return id;
    }
}
