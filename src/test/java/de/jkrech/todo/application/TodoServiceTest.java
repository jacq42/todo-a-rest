package de.jkrech.todo.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.jkrech.todo.domain.Description;
import de.jkrech.todo.domain.Todo;

public class TodoServiceTest {

    private TodoService todoService;

    @BeforeEach
    public void setup() {
        todoService = new TodoService();
    }

    @Test
    public void addTodoToList() {
        // given
        Todo todo = Todo.of(Description.of("Something needs to be done"));

        // when
        todoService.add(todo);

        // then
        assertTodoIsInList(todoService.list(), todo);
    }

    @Test
    public void invalidValues() {
        assertThrows(IllegalArgumentException.class, () -> todoService.add(null), "empty values are invalid");
    }

    @Test
    public void listContainsAllTodos() {
        // given
        Todo todo1 = Todo.of(Description.of("todo #1"));
        Todo todo2 = Todo.of(Description.of("todo #2"));

        // and
        Set<Todo> todosToAdd = new HashSet<>(Arrays.asList(todo1, todo2));

        // when
        todosToAdd.forEach(todo -> todoService.add(todo));

        // then
        Set<Todo> todoList = todoService.list();
        assertEquals(2, todoList.size());
        assertTodoIsInList(todoList, todo1);
        assertTodoIsInList(todoList, todo2);

    }

    private void assertTodoIsInList(Set<Todo> todoList, Todo todoToFind) {
        Optional<Todo> createdTodo = todoList.stream().filter(t -> t.createdAt().equals(todoToFind.createdAt())).findFirst();
        assertTrue(createdTodo.isPresent());
        assertSame(createdTodo.get(), todoToFind);
    }
}
