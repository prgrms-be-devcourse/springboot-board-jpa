package com.kdt.board.user.controller;

import static org.springframework.restdocs.payload.PayloadDocumentation.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kdt.board.user.domain.User;
import com.kdt.board.user.dto.request.UserCreateRequestDto;
import com.kdt.board.user.repository.UserRepository;
import com.kdt.board.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

@AutoConfigureRestDocs
@AutoConfigureMockMvc
@SpringBootTest
@Transactional
class UserRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @DisplayName("User 회원가입")
    void saveTest() throws Exception {
        UserCreateRequestDto userCreateRequestDto = new UserCreateRequestDto("CHOI", 27, "RUNNING");

        mockMvc.perform(MockMvcRequestBuilders.post("/users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(userCreateRequestDto)))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andDo(MockMvcResultHandlers.print())
            .andDo(MockMvcRestDocumentation.document("save-user",
                requestFields(
                    fieldWithPath("name").type(JsonFieldType.STRING).description("name"),
                    fieldWithPath("age").type(JsonFieldType.NUMBER).description("age"),
                    fieldWithPath("hobby").type(JsonFieldType.STRING).description("hobby")
                ),
                responseFields(
                    fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("statusCode"),
                    fieldWithPath("data").type(JsonFieldType.NUMBER).description("data"),
                    fieldWithPath("serverDateTime").type(JsonFieldType.STRING).description("serverDateTime")
                )
            ));
    }

    @Test
    @DisplayName("User 상세 정보 조회")
    void getOne() throws Exception {
        User user = new User("CHOI", 27, "Running");
        userRepository.save(user);

        mockMvc.perform(MockMvcRequestBuilders.get("/users/{id}", user.getId())
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andDo(MockMvcResultHandlers.print())
            .andDo(MockMvcRestDocumentation.document("get-user",
                responseFields(
                    fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("statusCode"),
                    fieldWithPath("data").type(JsonFieldType.OBJECT).description("data"),
                    fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("data.id"),
                    fieldWithPath("data.name").type(JsonFieldType.STRING).description("data.name"),
                    fieldWithPath("data.age").type(JsonFieldType.NUMBER).description("data.age"),
                    fieldWithPath("data.hobby").type(JsonFieldType.STRING).description("data.hobby"),
                    fieldWithPath("serverDateTime").type(JsonFieldType.STRING).description("serverDateTime")
                )
            ));
    }
}