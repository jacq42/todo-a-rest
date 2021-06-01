package de.jkrech.todo.application;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import de.jkrech.todo.domain.Description;
import de.jkrech.todo.domain.Todo;

@Service
public class TodoService {

    private Set<Todo> todos;

    /**
     * Returns a list of sorted {@link Todo}s.
     * Sorted by {@link Todo#createdAt()}
     *
     * @return {@link Set} of {@link Todo}s
     */
    public Set<Todo> list() {
        return CollectionUtils.isEmpty(todos) ? Collections.emptySet() : Collections.unmodifiableSet(new HashSet<>(todos));
    }

    /**
     * Adds a new {@link Todo}
     *
     * @param todo
     */
    public void add(@NonNull Todo todo) {
        Optional.ofNullable(todo).orElseThrow(() -> new IllegalArgumentException("Todo can't be empty."));
        addToList(todo);
    }

    /**
     * Create a new {@link TODO} with {@link Description} and current time.
     *
     * @param description
     */
    public Todo createWith(@NonNull Description description) {
        Optional.ofNullable(description).orElseThrow(() -> new IllegalArgumentException("Description can't be empty."));
        Todo todo = Todo.of(description);
        addToList(todo);
        return todo;
    }

    private void addToList(Todo todo) {
        if (CollectionUtils.isEmpty(todos)) {
            todos = new HashSet<>();
        }
        todos.add(todo);
    }
}
