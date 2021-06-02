package de.jkrech.todo.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

public class CompletionDateTest {

    @Test
    public void createValidDates() {
        // given
        LocalDate dateTime = LocalDate.now();

        // when
        CompletionDate completionDate = CompletionDate.of(dateTime );

        // then
        assertEquals(dateTime, completionDate.value());
    }

    @Test
    public void emptyValueReturnsEmptyObject() {
        // when
        CompletionDate completionDate = CompletionDate.of(null);

        // then
        assertNull(completionDate);
    }
}
