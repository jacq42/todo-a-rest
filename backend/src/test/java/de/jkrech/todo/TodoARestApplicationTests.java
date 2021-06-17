package de.jkrech.todo;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import de.jkrech.todo.application.TodoService;
import de.jkrech.todo.ports.html.TodoController;

@SpringBootTest
class TodoARestApplicationTests {

    @Autowired
    private TodoController controller;

    @Autowired
    private TodoService service;

    @Test
    void contextLoads() {
        assertNotNull(controller);
        assertNotNull(service);
    }

}
