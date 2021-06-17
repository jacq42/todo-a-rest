package de.jkrech.todo.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiPredicate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.jkrech.todo.domain.CompletionDate;
import de.jkrech.todo.domain.Description;
import de.jkrech.todo.domain.Todo;

class TodoServiceTest {

    private static final Description DEFAULT_DESCRIPTION = Description.of("todo #1");

    private static final CompletionDate EMPTY_COMPLETION_DATE = null;

    private TodoService todoService;

    @BeforeEach
    public void setup() {
        todoService = new TodoService();
    }

    @Test
    void addTodoToList() {
        // when
        Todo todo = todoService.createWith(Description.of("Something needs to be done"), EMPTY_COMPLETION_DATE);

        // then
        assertTodoIsInList(todoService.list(), todo.id());
    }

    @Test
    void addingWithInvalidValuesShouldThrowAnException() {
        assertThrows(IllegalArgumentException.class, () -> todoService.createWith(null, EMPTY_COMPLETION_DATE), "empty values are invalid");
    }

    @Test
    void listContainsAllTodos() {
        // given: a set with descriptions
        Description description2 = Description.of("todo #2");
        Set<Description> todosToAdd = new HashSet<>(Arrays.asList(DEFAULT_DESCRIPTION, description2));

        // when: creating todos
        Set<Todo> createdTodos = new HashSet<>();
        todosToAdd.forEach(description -> createdTodos.add(todoService.createWith(description, EMPTY_COMPLETION_DATE)));

        // then: they can be found in the list
        Set<Todo> todoList = todoService.list();
        assertEquals(2, todoList.size());
        Iterator<Todo> todoIterator = createdTodos.iterator();
        assertTodoIsInList(todoList, todoIterator.next().id());
        assertTodoIsInList(todoList, todoIterator.next().id());
    }

    @Test
    void todoCanBeDeleted() {
        // given: a list with 1 todo
        Todo createdTodo = todoService.createWith(DEFAULT_DESCRIPTION, EMPTY_COMPLETION_DATE);

        // when: deleting this todo by id
        Integer deletedId = todoService.deleteWith(createdTodo.id());

        // then: the todo has been delete
        assertEquals(createdTodo.id(), deletedId);

        // and: can not be found in the list
        assertTodoIsNotInList(todoService.list(), createdTodo.id());
    }

    @Test
    void canNotDeleteFromEmptyList() {
        assertThrows(UnsupportedOperationException.class, () -> todoService.deleteWith(Integer.MAX_VALUE));
    }

    @Test
    void cannotDeleteUnknownTodos() {
        // given
        todoService.createWith(DEFAULT_DESCRIPTION, EMPTY_COMPLETION_DATE);

        // expect
        assertThrows(NoSuchElementException.class, () -> todoService.deleteWith(Integer.MAX_VALUE));
    }

    @Test
    void deleteWithInvalidValuesShouldThrowAnException() {
        // given
        todoService.createWith(DEFAULT_DESCRIPTION, EMPTY_COMPLETION_DATE);

        assertThrows(IllegalArgumentException.class, () -> todoService.deleteWith(null));
    }

    // -- private asserts

    private void assertTodoIsInList(Set<Todo> todoList, Integer idToFind) {
        assertTrue(todoIsInList().test(todoList, idToFind));
    }

    private void assertTodoIsNotInList(Set<Todo> todoList, Integer idToFind) {
        assertFalse(todoIsInList().test(todoList, idToFind));
    }

    private BiPredicate<Set<Todo>, Integer> todoIsInList() {
        return (todoList, idToFind) -> {
            Optional<Todo> createdTodo = todoList.stream().filter(t -> t.id().equals(idToFind)).findFirst();
            return createdTodo.isPresent();
        };
    }

}
