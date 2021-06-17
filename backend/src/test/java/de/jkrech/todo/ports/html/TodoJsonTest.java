package de.jkrech.todo.ports.html;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;

import de.jkrech.todo.domain.CompletionDate;
import de.jkrech.todo.domain.Description;
import de.jkrech.todo.domain.Todo;

class TodoJsonTest {

    private static final DateTimeFormatter DTF_CREATED_AT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    @Test
    void convertsFromTodo() throws Exception {
        // given
        Todo todo = Todo.of(Description.of("Lorem ipsum"), CompletionDate.of(LocalDate.now()));

        // when
        TodoJson json = TodoJson.from(todo);

        // then
        assertEquals(todo.id(), json.id());
        assertEquals(todo.description().value(), json.description());
        assertEquals(todo.createdAt().value(), json.createdAt());
        assertEquals(todo.completionDate().value(), json.completionDate());
    }
}
