package de.jkrech.todo.ports.html;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import de.jkrech.todo.domain.Todo;

public class TodoJson {

    private Integer id;
    private String description;
    private LocalDateTime createdAt;
    private LocalDate completionDate;

    public static TodoJson from(Todo todo) {
        return new TodoJson(todo.id(), todo.description().value(), todo.createdAt().value(), todo.completionDate().value());
    }

    public TodoJson(Integer id, String description, LocalDateTime createdAt, LocalDate completionDate) {
        this.id = id;
        this.description = description;
        this.createdAt = createdAt;
        this.completionDate = completionDate;
    }

    @JsonProperty("id")
    public Integer id() {
        return id;
    }

    @JsonProperty("description")
    public String description() {
        return description;
    }

    @JsonProperty("createdAt")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    public LocalDateTime createdAt() {
        return createdAt;
    }

    @JsonProperty("completionDate")
    @JsonFormat(pattern = "yyyy-MM-dd")
    public LocalDate completionDate() {
        return completionDate;
    }

}
