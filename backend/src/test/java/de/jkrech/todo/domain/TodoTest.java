package de.jkrech.todo.domain;

import static java.time.LocalDate.now;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.time.Month;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;

class TodoTest {

    @ParameterizedTest(name = "invalid: \"{0}\"")
    @NullSource
    void invalidValuesShouldThrowAnException(Description description) {
        CompletionDate completionDate = CompletionDate.of(now());
        assertThrows(IllegalArgumentException.class, () -> Todo.of(description, completionDate));
    }

    @ParameterizedTest(name = "valid: description = \"{0}\" and completion date = \"{1}\" ")
    @MethodSource("validValues")
    void createWithValidValues(Description description, CompletionDate completionDate) {
        // when
        Todo todo = Todo.of(description, completionDate);

        // then
        assertNotNull(todo.id());
        assertEquals(description, todo.description());
        assertNotNull(todo.createdAt());
        assertEquals(completionDate, todo.completionDate());
    }

    private static Stream<Arguments> validValues() {
        return Stream.of(
                Arguments.of(Description.of("Lorem ipsum"), CompletionDate.of(LocalDate.of(2013, Month.FEBRUARY, 16)), "16.02.2013"),
                Arguments.of(Description.of("    Lorem ipsum     "), null, null)
        );
    }
}
