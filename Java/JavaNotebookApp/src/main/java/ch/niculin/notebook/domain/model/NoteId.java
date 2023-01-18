package ch.niculin.notebook.domain.model;

public class NoteId {
    private int Id;

    public NoteId(int id) {
        if (id >= 0){
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NoteId noteId = (NoteId) o;

        return Id == noteId.Id;
    }

    @Override
    public int hashCode() {
        return Id;
    }
}
