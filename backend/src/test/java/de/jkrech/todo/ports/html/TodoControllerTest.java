package de.jkrech.todo.ports.html;

import static java.time.LocalDate.now;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;

import java.util.Collections;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import de.jkrech.todo.application.TodoService;
import de.jkrech.todo.domain.CompletionDate;
import de.jkrech.todo.domain.Description;
import de.jkrech.todo.domain.Todo;

@ExtendWith(MockitoExtension.class)
public class TodoControllerTest {

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private Todo mockedTodo;

    @Mock
    private TodoService todoService;

    @InjectMocks
    private TodoController todoController;

    @Test
    public void canCreateNewTodo() {
        // given
        when(todoService.createWith(any(Description.class), any(CompletionDate.class))).thenReturn(mockedTodo);

        // when
        ResponseEntity<TodoJson> response = todoController.create("todo #1", now());

        // then
        assertEquals(OK, response.getStatusCode());
    }

    @Test
    public void canListAllTodos() {
        // given
        when(todoService.list()).thenReturn(Collections.singleton(mockedTodo));

        // when
        ResponseEntity<Set<TodoJson>> response = todoController.list();

        // then
        assertEquals(OK, response.getStatusCode());
    }

    @Test
    public void canDeleteATodoById() {
        // given
        Integer id = 1000;
        when(todoService.deleteWith(id)).thenReturn(id);

        // when
        ResponseEntity<Integer> response = todoController.delete(id);

        // then
        assertEquals(OK, response.getStatusCode());
        assertEquals(id, response.getBody());
    }

    @Test
    public void shouldForwardExceptions() {
        // given
        Integer id = 1000;
        when(todoService.deleteWith(id)).thenThrow(UnsupportedOperationException.class);

        // when
        ResponseEntity<Integer> response = todoController.delete(id);

        // then
        assertEquals(BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void invalidParamtersShouldThrowAnException() {
        assertThrows(IllegalArgumentException.class, () -> todoController.create("", null));
    }
}
