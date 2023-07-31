package com.jpaboard.domain.user.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jpaboard.domain.user.application.UserService;
import com.jpaboard.domain.user.dto.request.UserCreationRequest;
import com.jpaboard.domain.user.dto.request.UserUpdateRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureRestDocs
@AutoConfigureMockMvc
@SpringBootTest
class UserControllerTest {
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    private Long userId;

    @BeforeEach
    void setup() {
        userId = userService.createUser(new UserCreationRequest("이름", 10, "취미"));
    }

    @Test
    @DisplayName("유저를 생성할 수 있다.")
    void testUserSave() throws Exception {
        UserCreationRequest request = new UserCreationRequest("홍길동", 100, "도둑질");

        mockMvc.perform(
                        post("/api/users")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                ).andExpect(status().isCreated())
                .andDo(print())
                .andDo(document(
                        "user-create",
                        requestFields(
                                fieldWithPath("name").type(JsonFieldType.STRING).description("이름"),
                                fieldWithPath("age").type(JsonFieldType.NUMBER).description("나이"),
                                fieldWithPath("hobby").type(JsonFieldType.STRING).description("취미")
                        ),
                        responseFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("아이디")
                        )
                ));
    }

    @Test
    @DisplayName("유저를 조회할 수 있다.")
    void testUserFind() throws Exception {
        mockMvc.perform(get("/api/users/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andDo(print())
                .andDo(document(
                        "user-find",
                        responseFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("아이디"),
                                fieldWithPath("name").type(JsonFieldType.STRING).description("이름"),
                                fieldWithPath("age").type(JsonFieldType.NUMBER).description("나이"),
                                fieldWithPath("hobby").type(JsonFieldType.STRING).description("취미")
                        )
                ));
    }

    @Test
    @DisplayName("유저를 수정할 수 있다.")
    void testUserUpdate() throws Exception {
        UserUpdateRequest request = new UserUpdateRequest("수정된 이름", 100, "수정된 취미");

        mockMvc.perform(put("/api/users/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                ).andExpect(status().isOk())
                .andDo(print())
                .andDo(
                        document(
                                "user-update",
                                requestFields(
                                        fieldWithPath("name").type(JsonFieldType.STRING).description("수정할 이름"),
                                        fieldWithPath("age").type(JsonFieldType.NUMBER).description("수정할 나이"),
                                        fieldWithPath("hobby").type(JsonFieldType.STRING).description("수정할 취미")
                                )
                        )
                );
    }

    @Test
    @DisplayName("유저를 삭제할 수 있다.")
    void testUserDelete() throws Exception {
        mockMvc.perform(delete("/api/users/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andDo(print())
                .andDo(document("user-delete"));
    }
}
