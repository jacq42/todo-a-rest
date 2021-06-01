package de.jkrech.todo.ports.html;

import java.util.Set;

import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import de.jkrech.todo.application.TodoService;
import de.jkrech.todo.domain.Description;
import de.jkrech.todo.domain.Todo;

@RestController
public class TodoController {

    @NonNull
    private TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping(path = "/list", produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public ResponseEntity<Set<Todo>> list() {
        return ResponseEntity.ok(todoService.list());
    }

    @PostMapping(path = "/create", produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public ResponseEntity<Todo> create(@RequestParam String description) {
        Description validDescription = Description.of(description);
        Todo todo = todoService.createWith(validDescription);
        return ResponseEntity.ok(todo);
    }
}
