package de.jkrech.todo.integration;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import de.jkrech.todo.application.TodoService;
import de.jkrech.todo.domain.CompletionDate;
import de.jkrech.todo.domain.Description;
import de.jkrech.todo.domain.Todo;

@SpringBootTest
@AutoConfigureMockMvc
class TodoCotrollerIntegrationTest {

    private static final String BASE_URI = "/api/v1/todos";

    @Autowired
    private TodoService todoService;

    @Autowired
    private MockMvc mvc;

    @Test
    void list() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get(BASE_URI)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }

    @Test
    void create() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post(BASE_URI)
                .param("description", "super tolles todo")
                .param("completionDate", "2023-02-16")
                .contentType(MediaType.TEXT_PLAIN)
                .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").exists())
            .andExpect(jsonPath("$.description").exists())
            .andExpect(jsonPath("$.createdAt").exists())
            .andExpect(jsonPath("$.completionDate").exists());
    }

    @Test
    void delete() throws Exception {
        // given: a todo to delete
        Todo todo = todoService.createWith(Description.of("Lorem ipsum"), CompletionDate.of(LocalDate.now()));

        // expect
        mvc.perform(MockMvcRequestBuilders.delete(BASE_URI + "/{id}", todo.id())
                .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk());
    }

}
