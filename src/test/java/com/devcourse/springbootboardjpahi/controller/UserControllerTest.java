package com.devcourse.springbootboardjpahi.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.devcourse.springbootboardjpahi.dto.CreateUserRequest;
import com.devcourse.springbootboardjpahi.dto.UserResponse;
import com.devcourse.springbootboardjpahi.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@WebMvcTest(UserController.class)
class UserControllerTest {

    static final Faker faker = new Faker();

    @MockBean
    UserService userService;
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @DisplayName("[GET] 사용자 정보를 모두 반환한다.")
    @Test
    void testFindAll() throws Exception {
        // given
        List<UserResponse> mockResponses = List.of(generateUserResponse(), generateUserResponse());

        when(userService.findAll())
                .thenReturn(mockResponses);

        // when
        ResultActions actions = mockMvc.perform(get("/api/v1/users"));

        // then
        actions.andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(mockResponses.size())))
                .andDo(print());
    }

    @DisplayName("[GET] 등록된 사용자가 없으면 204 상태 코드를 반환한다.")
    @Test
    void testFindAllNoContent() throws Exception {
        // given
        when(userService.findAll())
                .thenReturn(Collections.emptyList());

        // when
        ResultActions actions = mockMvc.perform(get("/api/v1/users"));

        // then
        actions.andExpect(status().isNoContent())
                .andDo(print());
    }

    @DisplayName("[POST] 사용자를 추가한다.")
    @Test
    void testCreate() throws Exception {
        CreateUserRequest createUserRequest = generateCreateUserRequest();
        UserResponse userResponse = UserResponse.builder()
                .name(createUserRequest.name())
                .age(createUserRequest.age())
                .hobby(createUserRequest.hobby())
                .build();

        when(userService.create(createUserRequest))
                .thenReturn(userResponse);

        // when
        ResultActions actions = mockMvc.perform(post("/api/v1/users")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("name", createUserRequest.name())
                .param("age", createUserRequest.age().toString())
                .param("hobby", createUserRequest.hobby()));

        // then
        actions.andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is(createUserRequest.name())))
                .andExpect(jsonPath("$.age", is(createUserRequest.age())))
                .andExpect(jsonPath("$.hobby", is(createUserRequest.hobby())))
                .andDo(print());

    }

    CreateUserRequest generateCreateUserRequest() {
        String name = faker.name().firstName();
        int age = faker.number().randomDigitNotZero();
        String hobby = faker.esports().game();

        return new CreateUserRequest(name, age, hobby);
    }

    UserResponse generateUserResponse() {
        long id = faker.number().randomNumber();
        String name = faker.name().firstName();
        int age = faker.number().randomDigitNotZero();
        String hobby = faker.esports().game();
        LocalDateTime createdAt = LocalDateTime.now();

        return UserResponse.builder()
                .id(id)
                .name(name)
                .age(age)
                .hobby(hobby)
                .createdAt(createdAt)
                .build();
    }
}
