package de.jkrech.todo.application;

import static java.util.Collections.emptySet;
import static java.util.Collections.unmodifiableSet;
import static java.util.Optional.ofNullable;
import static org.springframework.util.CollectionUtils.isEmpty;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.function.Predicate;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import de.jkrech.todo.domain.CompletionDate;
import de.jkrech.todo.domain.Description;
import de.jkrech.todo.domain.Todo;

/**
 * Service that adds, removes and lists {@link TODO}s
 */
@SuppressWarnings("java:S1135")
@Service
public class TodoService {

    private Set<Todo> todos;

    /**
     * Returns a list of {@link Todo}s. Sorted by {@link Todo#createdAt()}
     *
     * @return unmodifiable {@link Set} of {@link Todo}s or empty set
     */
    public Set<Todo> list() {
        return isEmpty(todos) ? emptySet() : unmodifiableSet(new HashSet<>(todos));
    }

    /**
     * Create a new {@link TODO} with {@link Description} and current time.
     *
     * @param description {@link Description}
     * @param completionDate {@link CompletionDate}
     * @return {@link TODO} the created element
     * @throws IllegalArgumentException if description is invalid
     */
    public Todo createWith(@NonNull Description description, @Nullable CompletionDate completionDate) {
        var validDescription = ofNullable(description).orElseThrow(() -> new IllegalArgumentException("Description can't be empty."));

        var todo = Todo.of(validDescription, completionDate);
        addToList(todo);
        return todo;
    }

    /**
     * Delete {@link TODO} with specified id
     *
     * @param id of element to remove
     * @return id of removed element
     * @throws UnsupportedOperationException if elements cannot be removed
     * @throws NoSuchElementException if element is not in list
     * @throws IllegalArgumentException if id is invalid
     */
    public Integer deleteWith(@NonNull Integer id) {
        if (isEmpty(todos)) {
            throw new UnsupportedOperationException("TODO List is empty");
        }
        var validId = ofNullable(id).orElseThrow(() -> new IllegalArgumentException("ID can't be empty."));

        boolean deleted = todos.removeIf(hasId(validId));
        if (deleted) {
            return id;
        }
        throw new NoSuchElementException("Todo with id " + id + " could not be found.");
    }

    private Predicate<? super Todo> hasId(Integer id) {
        return todo -> todo.id().equals(id);
    }

    private void addToList(Todo todo) {
        if (isEmpty(todos)) {
            todos = new HashSet<>();
        }
        todos.add(todo);
    }
}
