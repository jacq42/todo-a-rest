package de.jkrech.todo.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class CreatedAt {

    private static final DateTimeFormatter DT_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

    private LocalDateTime value;

    public static CreatedAt of(LocalDateTime value) {
        return new CreatedAt(value);
    }

    private CreatedAt(LocalDateTime value) {
        LocalDateTime validDate = Optional.ofNullable(value)
                .orElseThrow(() -> new IllegalArgumentException("Value can't be empty"));

        this.value = validDate;
    }

    public LocalDateTime value() {
        return value;
    }

    @Override
    public String toString() {
        return value().format(DT_FORMATTER);
    }
}
