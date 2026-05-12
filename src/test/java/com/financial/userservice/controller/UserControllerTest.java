package com.financial.userservice.controller;

import com.financial.userservice.entity.User;
import com.financial.userservice.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testHealthEndpoint() throws Exception {
        mockMvc.perform(get("/api/users/health"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("UP"));
    }

    @Test
    public void testRegisterUser_Success() throws Exception {
        User inputUser = new User(null, "john", "john@example.com", "pass", "John Doe");
        User savedUser = new User(1L, "john", "john@example.com", "pass", "John Doe");

        when(userRepository.existsByUsername("john")).thenReturn(false);
        when(userRepository.existsByEmail("john@example.com")).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        mockMvc.perform(post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputUser)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.username", is("john")));
    }

    @Test
    public void testRegisterUser_DuplicateUsername() throws Exception {
        User inputUser = new User(null, "john", "john@example.com", "pass", "John");
        when(userRepository.existsByUsername("john")).thenReturn(true);

        mockMvc.perform(post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputUser)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error", containsString("Username already taken")));
    }

    @Test
    public void testGetAllUsers() throws Exception {
        List<User> users = Arrays.asList(
                new User(1L, "john", "john@example.com", "pass", "John"),
                new User(2L, "jane", "jane@example.com", "pass", "Jane")
        );
        when(userRepository.findAll()).thenReturn(users);

        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].username", is("john")));
    }

    @Test
    public void testGetUserById_WhenExists() throws Exception {
        User user = new User(1L, "john", "john@example.com", "pass", "John");
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        mockMvc.perform(get("/api/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username", is("john")));
    }

    @Test
    public void testGetUserById_WhenNotFound() throws Exception {
        when(userRepository.findById(99L)).thenReturn(Optional.empty());
        mockMvc.perform(get("/api/users/99"))
                .andExpect(status().isNotFound());
    }
}