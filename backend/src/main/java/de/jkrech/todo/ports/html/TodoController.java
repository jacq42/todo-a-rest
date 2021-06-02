package de.jkrech.todo.ports.html;

import java.util.Set;

import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import de.jkrech.todo.application.TodoService;
import de.jkrech.todo.domain.Description;
import de.jkrech.todo.domain.Todo;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1/")
public class TodoController {

    @NonNull
    private TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping(path = "/todos", produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public ResponseEntity<Set<Todo>> list() {
        return ResponseEntity.ok(todoService.list());
    }

    @PostMapping(path = "/todos", produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public ResponseEntity<Todo> create(@RequestParam String description) {
        Description validDescription = Description.of(description);
        Todo todo = todoService.createWith(validDescription);
        return ResponseEntity.ok(todo);
    }

    @DeleteMapping(path = "/todos/{id}")
    public ResponseEntity<Integer> delete(@PathVariable Integer id) {
        try {
            todoService.deleteWith(id);
        } catch(UnsupportedOperationException | IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(id);
    }
}
