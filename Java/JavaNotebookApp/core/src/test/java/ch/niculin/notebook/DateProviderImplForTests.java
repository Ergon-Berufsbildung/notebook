package ch.niculin.notebook;

import ch.niculin.notebook.infrastructure.DateProvider;

import java.time.LocalDate;

public class DateProviderImplForTests implements DateProvider {
    @Override
    public LocalDate getDate() {
        return LocalDate.of(2023, 5, 11);
    }
}
