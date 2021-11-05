package com.smalaca.gtd.projectmanagements.infrastruture.web.rest;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser("USER")
@Tag("SystemTest")
class GetThingsDoneControllerSystemTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldMotivateSpecificPerson() throws Exception {
        mockMvc.perform(get("/get-things-done").param("name", "Pater Parker"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Pater Parker! Get all the things done!"));
    }

    @Test
    void shouldMotivateNoName() throws Exception {
        mockMvc.perform(get("/get-things-done"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("No Name! Get all the things done!"));
    }
}