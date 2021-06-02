package de.jkrech.todo.application;

import java.util.Collections;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import de.jkrech.todo.domain.Description;
import de.jkrech.todo.domain.Todo;

@Service
public class TodoService {

    private Set<Todo> todos;

    /**
     * Returns a list of {@link Todo}s. Sorted by {@link Todo#createdAt()}
     *
     * @return {@link Set} of {@link Todo}s
     */
    public Set<Todo> list() {
        return CollectionUtils.isEmpty(todos) ? Collections.emptySet()
                : Collections.unmodifiableSet(new HashSet<>(todos));
    }

    /**
     * Create a new {@link TODO} with {@link Description} and current time.
     *
     * @param description
     * @return {@link TODO} the created element
     * @throws IllegalArgumentException if description is invalid
     */
    public Todo createWith(@NonNull Description description) {
        Optional.ofNullable(description).orElseThrow(() -> new IllegalArgumentException("Description can't be empty."));
        Todo todo = Todo.of(description);
        addToList(todo);
        return todo;
    }

    /**
     * Delete {@link TODO} with specified id
     * @param id of element to remove
     * @return id of removed element
     * @throws UnsupportedOperationException if elements cannot be removed
     * @throws NoSuchElementException if element is not in list
     * @throws IllegalArgumentException if id is invalid
     */
    public Integer deleteWith(Integer id) {
        if (CollectionUtils.isEmpty(todos)) {
            throw new UnsupportedOperationException("TODO List is empty");
        }
        Optional.ofNullable(id).orElseThrow(() -> new IllegalArgumentException("ID can't be empty."));
        boolean deleted = todos.removeIf(hasId(id));
        if (deleted) {
            return id;
        }
        throw new NoSuchElementException("Todo with id " + id + " could not be found.");
    }

    private Predicate<? super Todo> hasId(Integer id) {
        return todo -> todo.id().equals(id);
    }

    private void addToList(Todo todo) {
        if (CollectionUtils.isEmpty(todos)) {
            todos = new HashSet<>();
        }
        todos.add(todo);
    }
}
