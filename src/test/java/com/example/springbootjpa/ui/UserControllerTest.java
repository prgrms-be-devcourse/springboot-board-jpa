package com.example.springbootjpa.ui;

import com.example.springbootjpa.domain.user.Hobby;
import com.example.springbootjpa.domain.user.User;
import com.example.springbootjpa.domain.user.UserRepository;
import com.example.springbootjpa.ui.dto.user.UserSaveRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import static org.springframework.http.HttpHeaders.*;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.modifyUris;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(RestDocumentationExtension.class)
@AutoConfigureMockMvc
@Transactional
@SpringBootTest
class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    UserRepository userRepository;

    @BeforeEach
    void setup(
            WebApplicationContext webApplicationContext,
            RestDocumentationContextProvider restDocumentationContextProvider
    ) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .addFilter(new CharacterEncodingFilter("UTF-8", true))
                .apply(documentationConfiguration(restDocumentationContextProvider)
                        .operationPreprocessors()
                        .withRequestDefaults(modifyUris().host("localhost").port(8080), prettyPrint())
                        .withResponseDefaults(modifyUris().host("localhost").port(8080), prettyPrint()))
                .build();
    }

    @Test
    @DisplayName("회원 가입")
    void createUser() throws Exception {
        // given
        UserSaveRequest userSaveRequest = new UserSaveRequest("kim", 20, Hobby.EXERCISE);

        // when && then
        this.mockMvc.perform(
                        post("/api/v1/users")
                                .content(objectMapper.writeValueAsString(userSaveRequest))
                                .contentType("application/json")
                ).andExpect(status().isCreated())
                .andDo(print())
                .andDo(document("create-user",
                        requestHeaders(
                                headerWithName(CONTENT_TYPE).description("content type"),
                                headerWithName(CONTENT_LENGTH).description("length of content")
                        ),
                        requestFields(
                                fieldWithPath("name").description("user name"),
                                fieldWithPath("age").description("user age"),
                                fieldWithPath("hobby").description("user hobby")
                        ),
                        responseHeaders(
                                headerWithName(LOCATION).description("created url"),
                                headerWithName(CONTENT_TYPE).description("content type")
                        ),
                        responseFields(
                                fieldWithPath("id").description("id of created user")
                        )
                ));
    }

    @Test
    @DisplayName("모든 회원 조회")
    void findAllUsers() throws Exception {
        // given
        User user1 = new User("kim", 20, Hobby.EXERCISE);
        User user2 = new User("park", 30, Hobby.EXERCISE);
        userRepository.save(user1);
        userRepository.save(user2);


        // when && then
        this.mockMvc.perform(
                        get("/api/v1/users")
                ).andExpect(status().isOk())
                .andDo(print())
                .andDo(document("find-all-users",
                        responseHeaders(
                                headerWithName(CONTENT_TYPE).description("content type")
                        ),
                        responseFields(
                                fieldWithPath("[].id").description("id"),
                                fieldWithPath("[].name").description("name"),
                                fieldWithPath("[].age").description("age"),
                                fieldWithPath("[].hobby").description("hobby")
                        )
                ));
    }
}
