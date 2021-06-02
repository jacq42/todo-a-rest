package de.jkrech.todo.domain;

import static java.time.LocalDateTime.now;
import static java.util.Optional.ofNullable;

import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.core.style.ToStringCreator;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

public class Todo {

    private static final AtomicInteger ID_GENERATOR = new AtomicInteger(1000);

    @NonNull
    private Integer id;

    @NonNull
    private Description description;

    @NonNull
    private CreatedAt createdAt;

    @Nullable
    private CompletionDate completionDate;

    /**
     * Creates a new {@link Todo} with specified parameters.
     *
     * @param description
     * @param completionDate
     * @return Valid {@link Todo}
     */
    public static Todo of(@NonNull Description description, @Nullable CompletionDate completionDate) {
        return Todo.of(description, CreatedAt.of(now()), completionDate);
    }

    private static Todo of(Description description, CreatedAt createdAt, CompletionDate completionDate) {
        ofNullable(description).orElseThrow(() -> new IllegalArgumentException("Description can't be empty."));

        Todo todo = new Todo();
        todo.id = ID_GENERATOR.getAndIncrement();
        todo.description = description;
        todo.createdAt = createdAt;
        todo.completionDate = completionDate;
        return todo;
    }

    private Todo() {

    }

    public Integer id() {
        return id;
    }

    public Description description() {
        return description;
    }

    public CreatedAt createdAt() {
        return createdAt;
    }

    public CompletionDate completionDate() {
        return completionDate;
    }

    @Override
    public String toString() {
        return new ToStringCreator(this).append(createdAt).append(description).toString();
    }
}
