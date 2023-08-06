package com.example.springbootboardjpa.controller;

import com.example.springbootboardjpa.RestDocs.AbstractRestDocesTest;
import com.example.springbootboardjpa.dto.user.request.UserCreateRequest;
import com.example.springbootboardjpa.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static com.example.springbootboardjpa.enums.Hobby.GAME;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
class UserControllerTest extends AbstractRestDocesTest {

    private final ObjectMapper objectMapper;
    private final UserService userService;

    @Autowired
    public UserControllerTest(RestDocumentationResultHandler resultHandler, MockMvc mockMvc, ObjectMapper objectMapper,
                              UserService userService) {
        super(resultHandler, mockMvc);
        this.objectMapper = objectMapper;
        this.userService = userService;
    }

    @BeforeEach
    void saveUser() {
        UserCreateRequest request = new UserCreateRequest("Kim Jae won", 28, GAME);
        userService.createUser(request);
    }

    @Test
    @DisplayName("[REST DOCS] CREATE User")
    @Transactional
    void createUserTest() throws Exception {
        UserCreateRequest request = new UserCreateRequest("Kim Jae won", 28, GAME);

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("name").value("Kim Jae won"))
                .andExpect(jsonPath("age").value(28))
                .andExpect(jsonPath("hobby").value(GAME.toString()))
                .andDo(resultHandler);
    }

    @Test
    @DisplayName("[REST DOCS] GET All Users")
    void findByUserAllTest() throws Exception {
        mockMvc.perform(get("/api/users")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(resultHandler);
    }

    @Test
    @DisplayName("[REST DOCS] GET user by ID")
    void findByUserByIdTest() throws Exception {
        long userId = 1;

        mockMvc.perform(get("/api/users/{id}", userId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(resultHandler);
    }

    @Test
    @DisplayName("[REST DOCS] UPDATE user")
    @Transactional
    void updateUserTest() throws Exception {
        long userId = 1;
        UserCreateRequest request = new UserCreateRequest("Kim Jae won", 28, GAME);


        mockMvc.perform(patch("/api/users/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(resultHandler);
    }

    @Test
    @DisplayName("[REST DOCS] DELETE user by ID")
    @Transactional
    void deleteByUserByIdTest() throws Exception {
        long userId = 1;

        mockMvc.perform(delete("/api/users/{id}", userId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andDo(resultHandler);
    }
}
