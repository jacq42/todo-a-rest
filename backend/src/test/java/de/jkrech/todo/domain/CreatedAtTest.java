package de.jkrech.todo.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;

public class CreatedAtTest {

    @ParameterizedTest(name = "invalid: \"{0}\"")
    @NullSource
    public void invalidValuesShouldThrowAnException(LocalDateTime value) {
        assertThrows(IllegalArgumentException.class, () -> CreatedAt.of(value));
    }

    @Test
    public void createValidDates() {
        // given
        LocalDateTime dateTime = LocalDateTime.now();

        // when
        CreatedAt createdAt = CreatedAt.of(dateTime );

        // then
        assertEquals(dateTime, createdAt.value());
    }
}
