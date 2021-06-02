package de.jkrech.todo.ports.html;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import de.jkrech.todo.application.TodoService;
import de.jkrech.todo.domain.Description;
import de.jkrech.todo.domain.Todo;

@ExtendWith(MockitoExtension.class)
public class TodoControllerTest {

    @Mock
    private Todo mockedTodo;

    @Mock
    private TodoService todoService;

    @InjectMocks
    private TodoController todoController;

    @Test
    public void canCreateNewTodo() {
        // given
        when(todoService.createWith(Mockito.any(Description.class))).thenReturn(mockedTodo);

        // when
        ResponseEntity<Todo> response = todoController.create("todo #1");

        // then
        assertEquals(OK, response.getStatusCode());
        assertEquals(mockedTodo, response.getBody());
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
    public void throwsException() {
        assertThrows(IllegalArgumentException.class, () -> todoController.create(""));
    }
}
