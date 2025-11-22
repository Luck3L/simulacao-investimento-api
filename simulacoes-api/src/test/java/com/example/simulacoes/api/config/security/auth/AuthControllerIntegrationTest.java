package com.example.simulacoes.api.config.security.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.security.test.context.support.WithMockUser;

@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
    
    @Test
    void testAuthenticate_Success() throws Exception {
        AuthRequest request = new AuthRequest("admin@test.com", "password123");

        mockMvc.perform(post("/auth/token")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk()) 
                .andExpect(jsonPath("$.token").isNotEmpty()); 
    }

    @Test
    void testAuthenticate_InvalidCredentials() throws Exception {
        AuthRequest request = new AuthRequest("admin@test.com", "wrong_password");

        mockMvc.perform(post("/auth/token")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isUnauthorized()); 
    }
    
   /* @Test
    @WithMockUser(username = "admin@test.com", roles = {"USER"})
    void testSecureEndpoint_AccessGranted() throws Exception {
        mockMvc.perform(get("/auth/token"))
               .andExpect(status().isOk());
    }*/
}