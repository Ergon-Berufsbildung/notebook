package ch.niculin.notebook.domain.model;

public class NoteId {
    private int Id;

    public NoteId(int id) {
        if (id > 0){
            Id = id;
        } else {
            throw new IllegalArgumentException("Id cant be lower than 0");
        }
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }
}
