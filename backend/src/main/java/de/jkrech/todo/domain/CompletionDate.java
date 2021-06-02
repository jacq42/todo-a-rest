package de.jkrech.todo.domain;

import static java.util.Optional.ofNullable;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.lang.NonNull;

public class CompletionDate {

    private static final DateTimeFormatter DT_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    private static final CompletionDate EMPTY = null;

    @NonNull
    private LocalDate value;

    public static CompletionDate of(LocalDate value) {
        return (ofNullable(value).isPresent()) ? new CompletionDate(value) : EMPTY;
    }

    private CompletionDate(LocalDate value) {
        this.value = value;
    }

    public LocalDate value() {
        return value;
    }

    @Override
    public String toString() {
        return value().format(DT_FORMATTER);
    }
}
