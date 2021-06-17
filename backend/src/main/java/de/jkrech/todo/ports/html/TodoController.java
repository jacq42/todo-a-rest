package de.jkrech.todo.ports.html;

import static java.util.stream.Collectors.toSet;

import java.time.LocalDate;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
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
import de.jkrech.todo.domain.CompletionDate;
import de.jkrech.todo.domain.Description;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1/")
public class TodoController {

    @Autowired
    private TodoService todoService;

    @GetMapping(path = "/todos", produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public ResponseEntity<Set<TodoJson>> list() {
        Set<TodoJson> allTodos = todoService.list().stream().map(TodoJson::from).collect(toSet());
        return ResponseEntity.ok(allTodos);
    }

    @PostMapping(path = "/todos", produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public ResponseEntity<TodoJson> create(
            @RequestParam String description,
            @RequestParam @Nullable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate completionDate) {

        var validDescription = Description.of(description);
        var validCompletionDate = CompletionDate.of(completionDate);

        var todo = todoService.createWith(validDescription, validCompletionDate);
        return ResponseEntity.ok(TodoJson.from(todo));
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
