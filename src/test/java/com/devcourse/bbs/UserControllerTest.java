package com.devcourse.bbs;

import com.devcourse.bbs.controller.bind.UserCreateRequest;
import com.devcourse.bbs.controller.bind.UserUpdateRequest;
import com.devcourse.bbs.domain.user.User;
import com.devcourse.bbs.repository.user.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureRestDocs
@AutoConfigureMockMvc
@Transactional
class UserControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ObjectMapper objectMapper;

    private static final FieldDescriptor apiResponseSuccess = fieldWithPath("success").description("Status of result");
    private static final FieldDescriptor apiResponseResult = fieldWithPath("result").description("Value of result");
    private static final FieldDescriptor apiResponseError = fieldWithPath("error").description("Message of result");

    @Test
    void createUserTest() throws Exception {
        UserCreateRequest request = new UserCreateRequest();
        request.setName("NAME");
        request.setHobby("HOBBY");
        request.setAge(25);
        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(header().exists(HttpHeaders.LOCATION))
                .andExpect(jsonPath("$['result'].name").value("NAME"))
                .andExpect(jsonPath("$['result'].id").isNotEmpty())
                .andDo(document("UserController/createUser",
                        requestFields(
                                fieldWithPath("name").description("Creating user's unique name."),
                                fieldWithPath("age").description("Creating user's age."),
                                fieldWithPath("hobby").description("Creating user's hobby.")),
                        responseFields(
                                apiResponseSuccess,
                                apiResponseResult,
                                apiResponseError,
                                fieldWithPath("result.id").description("Created user's id."),
                                fieldWithPath("result.name").description("Created user's name."),
                                fieldWithPath("result.age").description("Created user's age."),
                                fieldWithPath("result.hobby").description("Created user's hobby."))));
    }

    @Test
    void updateUserTest() throws Exception {
        User user = userRepository.save(User.builder()
                .name("NAME")
                .age(25)
                .hobby("HOBBY")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now()).build());
        UserUpdateRequest request = new UserUpdateRequest();
        request.setAge(55);
        request.setName("UPDATED_NAME");
        request.setHobby("NO_HOBBY");
        mockMvc.perform(put("/users/{name}", user.getName())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$['result'].age").value(55))
                .andExpect(jsonPath("$['result'].name").value("UPDATED_NAME"))
                .andExpect(jsonPath("$['result'].hobby").value("NO_HOBBY"))
                .andDo(document("UserController/updateUser",
                        pathParameters(
                                parameterWithName("name").description("Updating user's name")),
                        requestFields(
                                fieldWithPath("name").description("User's name to update."),
                                fieldWithPath("age").description("User's age to update."),
                                fieldWithPath("hobby").description("User's hobby to update.")),
                        responseFields(
                                apiResponseSuccess,
                                apiResponseResult,
                                apiResponseError,
                                fieldWithPath("result.id").description("User's id."),
                                fieldWithPath("result.name").description("Updated user's name."),
                                fieldWithPath("result.age").description("Updated user's age."),
                                fieldWithPath("result.hobby").description("Updated user's hobby."))));
    }

    @Test
    void readUserTest() throws Exception {
        User user = userRepository.save(User.builder()
                .name("NAME")
                .age(25)
                .hobby("HOBBY")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now()).build());
        mockMvc.perform(get("/users/{name}", user.getName()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$['result'].id").isNotEmpty())
                .andExpect(jsonPath("$['result'].name").value("NAME"))
                .andExpect(jsonPath("$['result'].age").value(25))
                .andExpect(jsonPath("$['result'].hobby").value("HOBBY"))
                .andDo(document("UserController/readUser",
                        pathParameters(
                                parameterWithName("name").description("User's name to read.")),
                        responseFields(
                                apiResponseSuccess,
                                apiResponseResult,
                                apiResponseError,
                                fieldWithPath("result.id").description("User's id."),
                                fieldWithPath("result.name").description("User's name."),
                                fieldWithPath("result.age").description("User's age."),
                                fieldWithPath("result.hobby").description("User's hobby."))));
    }

    @Test
    void deleteUserTest() throws Exception {
        User user = userRepository.save(User.builder()
                .name("NAME")
                .age(25)
                .hobby("HOBBY")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now()).build());
        mockMvc.perform(get("/users/{name}", user.getName()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$['result'].id").isNotEmpty())
                .andExpect(jsonPath("$['result'].name").value("NAME"))
                .andExpect(jsonPath("$['result'].age").value(25))
                .andExpect(jsonPath("$['result'].hobby").value("HOBBY"))
                .andDo(document("UserController/readUser",
                        pathParameters(
                                parameterWithName("name").description("User's name to read.")),
                        responseFields(
                                apiResponseSuccess,
                                apiResponseResult,
                                apiResponseError,
                                fieldWithPath("result.id").description("User's id."),
                                fieldWithPath("result.name").description("User's name."),
                                fieldWithPath("result.age").description("User's age."),
                                fieldWithPath("result.hobby").description("User's hobby."))));
    }
}
