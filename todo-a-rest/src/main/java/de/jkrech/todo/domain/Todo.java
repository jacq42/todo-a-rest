package de.jkrech.todo.domain;

import static java.time.LocalDateTime.now;

import java.time.LocalDateTime;

import org.springframework.core.style.ToStringCreator;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Todo {

    private Description description;
    private LocalDateTime createdAt;

    public static Todo of(Description description) {
        return Todo.of(description, now());
    }

    public static Todo of(Description description, LocalDateTime createdAt) {
        Todo todo = new Todo();
        todo.description = description;
        todo.createdAt = createdAt;
        return todo;
    }

    private Todo() {

    }

    @JsonProperty
    public String description() {
        return description.value();
    }

    @JsonProperty
    public LocalDateTime createdAt() {
        return createdAt;
    }

    @Override
    public String toString() {
        return new ToStringCreator(this).append(createdAt).append(description.value()).toString();
    }
}
