package de.jkrech.todo.ports.html;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
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
        Mockito.when(todoService.createWith(Mockito.any(Description.class))).thenReturn(mockedTodo);

        // when
        ResponseEntity<Todo> created = todoController.create("todo #1");

        // then
        assertEquals(HttpStatus.OK, created.getStatusCode());
        assertEquals(mockedTodo, created.getBody());
    }

    @Test
    public void throwsException() {
        assertThrows(IllegalArgumentException.class, () -> todoController.create(""));
    }
}
