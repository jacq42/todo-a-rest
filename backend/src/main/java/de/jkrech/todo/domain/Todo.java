package de.jkrech.todo.domain;

import static java.time.LocalDateTime.now;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.core.style.ToStringCreator;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Todo {

    private static final AtomicInteger ID_GENERATOR = new AtomicInteger(1000);

    private Integer id;
    private Description description;
    private LocalDateTime createdAt;

    public static Todo of(Description description) {
        return Todo.of(description, now());
    }

    public static Todo of(Description description, LocalDateTime createdAt) {
        Todo todo = new Todo();
        todo.id = ID_GENERATOR.getAndIncrement();
        todo.description = description;
        todo.createdAt = createdAt;
        return todo;
    }

    private Todo() {

    }

    @JsonProperty("id")
    public Integer id() {
        return id;
    }

    @JsonProperty("description")
    public String description() {
        return description.value();
    }

    @JsonProperty("createdAt")
    public LocalDateTime createdAt() {
        return createdAt;
    }

    @Override
    public String toString() {
        return new ToStringCreator(this).append(createdAt).append(description.value()).toString();
    }
}
