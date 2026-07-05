package com.backend.project.integration;

import com.backend.project.config.AbstractIntegrationTest;
import com.backend.project.interfaces.dto.batch.BatchRequestDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.is;

@AutoConfigureMockMvc
public class BatchIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser(username = "admin", roles = {"USER"})
    void shouldCreateAndRetrieveBatch() throws Exception {
        BatchRequestDTO request = new BatchRequestDTO("transactions.csv", "PENDING");
        
        // 1. Create a Batch
        mockMvc.perform(post("/batch")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.batchId", notNullValue()))
                .andExpect(jsonPath("$.fileName", is("transactions.csv")))
                .andExpect(jsonPath("$.status", is("PENDING")));
                
        // 2. Retrieve Batches
        mockMvc.perform(get("/batch")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].fileName", is("transactions.csv")));
    }
}
