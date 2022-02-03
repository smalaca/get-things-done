package com.smalaca.gtd.projectmanagements.infrastructure.api.web.rest.auth;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Tag("SystemTest")
class NotAuthenticatedUserSystemTest {
    @Autowired private MockMvc mockMvc;

    @Test
    void shouldBeForbiddenToCreateIdeaForNotAuthenticatedUser() throws Exception {
        String idea = "{\"title\":\"Idea\",\"description\":\"With description\"}";

        mockMvc.perform(post("/idea").contentType(APPLICATION_JSON).content(idea))
                .andExpect(status().isForbidden());
    }
}
