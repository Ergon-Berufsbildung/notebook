package ch.niculin.notebook.domain.model;

public record NoteId(int id) {

    public NoteId {
        if (id < 0) {
            throw new IllegalArgumentException("Id can't be lower than 0");
        }
    }
}
