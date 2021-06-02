package de.jkrech.todo.domain;

import static java.time.LocalDateTime.now;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;

public class TodoTest {

    @ParameterizedTest(name = "invalid: \"{0}\"")
    @NullSource
    public void invalidValuesShouldThrowAnException(Description description) {
        assertThrows(IllegalArgumentException.class, () -> Todo.of(description));
    }

    @ParameterizedTest(name = "valid: description = \"{0}\" and completion date = \"{1}\" ")
    @MethodSource("validValues")
    public void createWithValidValues(Description description, LocalDateTime completionDate) {
        // when
        Todo todo = Todo.of(description);

        // then
        assertNotNull(todo.id());
        assertEquals(description.value(), todo.description());
        assertNotNull(todo.createdAt());
    }

    private static Stream<Arguments> validValues() {
        return Stream.of(
                Arguments.of(Description.of("Lorem ipsum"), now().plusMonths(1)),
                Arguments.of(Description.of("    Lorem ipsum     "), now().plusMonths(1))
        );
    }
}
