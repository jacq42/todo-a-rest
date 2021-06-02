package de.jkrech.todo.integration;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
@AutoConfigureMockMvc
public class TodoControllerIntegrationTest {

    private static final String BASE_URI = "/api/v1/todos";
    @Autowired
    private MockMvc mvc;

    @Test
    public void list() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get(BASE_URI)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }

    @Test
    public void create() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post(BASE_URI)
                .accept(MediaType.APPLICATION_JSON)
                .param("description", "super tolles todo"))
            .andDo(print())
            .andExpect(status().isOk());
    }

}
