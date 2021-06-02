package de.jkrech.todo.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.NoSuchElementException;
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
        // when
        Todo todo = todoService.createWith(Description.of("Something needs to be done"));

        // then
        assertTodoIsInList(todoService.list(), todo.id());
    }

    @Test
    public void addingWithInvalidValuesShouldThrowAnException() {
        assertThrows(IllegalArgumentException.class, () -> todoService.createWith(null), "empty values are invalid");
    }

    @Test
    public void listContainsAllTodos() {
        // given
        Description description1 = Description.of("todo #1");
        Description description2 = Description.of("todo #2");

        // and
        Set<Description> todosToAdd = new HashSet<>(Arrays.asList(description1, description2));

        // when
        Set<Todo> createdTodos = new HashSet<>();
        todosToAdd.forEach(description -> createdTodos.add(todoService.createWith(description)));

        // then
        Set<Todo> todoList = todoService.list();
        assertEquals(2, todoList.size());
        Iterator<Todo> todoIterator = createdTodos.iterator();
        assertTodoIsInList(todoList, todoIterator.next().id());
        assertTodoIsInList(todoList, todoIterator.next().id());
    }

    @Test
    public void todoCanBeDeleted() {
        // given
        Todo createdTodo = todoService.createWith(Description.of("todo #1"));

        // when
        Integer deletedId = todoService.deleteWith(createdTodo.id());

        // then: same id
        assertEquals(createdTodo.id(), deletedId);
    }

    @Test
    public void canNotDeleteFromEmptyList() {
        assertThrows(UnsupportedOperationException.class, () -> todoService.deleteWith(Integer.MAX_VALUE));
    }

    @Test
    public void cannotDeleteUnknownTodos() {
        // given
        todoService.createWith(Description.of("todo #1"));

        // expect
        assertThrows(NoSuchElementException.class, () -> todoService.deleteWith(Integer.MAX_VALUE));
    }

    @Test
    public void deleteWithInvalidValuesShouldThrowAnException() {
        // given
        todoService.createWith(Description.of("todo #1"));

        assertThrows(IllegalArgumentException.class, () -> todoService.deleteWith(null));
    }

    private void assertTodoIsInList(Set<Todo> todoList, Integer idToFind) {
        Optional<Todo> createdTodo = todoList.stream().filter(t -> t.id().equals(idToFind)).findFirst();
        assertTrue(createdTodo.isPresent());
    }
}
