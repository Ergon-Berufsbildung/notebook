package ch.niculin.notebook.infrastructure;

import java.time.LocalDate;

public class DateProviderImpl implements DateProvider{
    @Override
    public LocalDate getDate() {
        return LocalDate.now();
    }
}
