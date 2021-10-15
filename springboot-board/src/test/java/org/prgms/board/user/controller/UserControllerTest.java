package org.prgms.board.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgms.board.domain.entity.User;
import org.prgms.board.user.dto.UserRequest;
import org.prgms.board.user.dto.UserResponse;
import org.prgms.board.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    private User user;

    @BeforeEach
    void setUp() {
        user = User.builder()
            .id(1L)
            .name("buhee")
            .age(26)
            .hobby("making")
            .build();
    }

    @DisplayName("특정 사용자 정보 조회하기")
    @Test
    void getOneUser() throws Exception {
        given(userService.getUser(anyLong())).willReturn(new UserResponse(user));

        RequestBuilder request = MockMvcRequestBuilders
            .get("/users/{id}", user.getId())
            .accept(MediaType.APPLICATION_JSON);

        this.mockMvc.perform(request)
            .andExpect(status().isOk())
            .andDo(
                document("user-getOne",
                    responseFields(
                        fieldWithPath("data").type(JsonFieldType.OBJECT).description("데이터"),
                        fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("아이디"),
                        fieldWithPath("data.name").type(JsonFieldType.STRING).description("이름"),
                        fieldWithPath("data.age").type(JsonFieldType.NUMBER).description("나이"),
                        fieldWithPath("data.hobby").type(JsonFieldType.STRING).description("취미"),
                        fieldWithPath("data.createdDate").type(JsonFieldType.NULL).description("생성날짜"),
                        fieldWithPath("data.updatedDate").type(JsonFieldType.NULL).description("변경날짜"),
                        fieldWithPath("status").type(JsonFieldType.NUMBER).description("상태코드")
                    )
                )
            );
    }

    @DisplayName("사용자 저장하기")
    @Test
    void addUser() throws Exception {
        given(userService.addUser(any())).willReturn(user.getId());

        String body = objectMapper.writeValueAsString(
            new UserRequest("buhee", 26, "making"));

        RequestBuilder request = MockMvcRequestBuilders
            .post("/users")
            .content(body)
            .contentType(MediaType.APPLICATION_JSON);

        this.mockMvc.perform(request)
            .andExpect(status().isOk())
            .andDo(
                document("user-add",
                    requestFields(
                        fieldWithPath("name").type(JsonFieldType.STRING).description("이름"),
                        fieldWithPath("age").type(JsonFieldType.NUMBER).description("나이"),
                        fieldWithPath("hobby").type(JsonFieldType.STRING).description("취미")
                    ),
                    responseFields(
                        fieldWithPath("data").type(JsonFieldType.NUMBER).description("데이터"),
                        fieldWithPath("status").type(JsonFieldType.NUMBER).description("상태코드")
                    )
                )
            );
    }

    @DisplayName("사용자 정보 수정하기")
    @Test
    void modifyUser() throws Exception {
        given(userService.modifyUser(anyLong(), any())).willReturn(user.getId());

        String body = objectMapper.writeValueAsString(
            new UserRequest("buri", 26, "tufting"));

        RequestBuilder request = MockMvcRequestBuilders
            .put("/users/{id}", user.getId())
            .content(body)
            .contentType(MediaType.APPLICATION_JSON);

        this.mockMvc.perform(request)
            .andExpect(status().isOk())
            .andDo(
                document("user-modify",
                    requestFields(
                        fieldWithPath("name").type(JsonFieldType.STRING).description("이름"),
                        fieldWithPath("age").type(JsonFieldType.NUMBER).description("나이"),
                        fieldWithPath("hobby").type(JsonFieldType.STRING).description("취미")
                    ),
                    responseFields(
                        fieldWithPath("data").type(JsonFieldType.NUMBER).description("데이터"),
                        fieldWithPath("status").type(JsonFieldType.NUMBER).description("상태코드")
                    )
                )
            );
    }

    @DisplayName("사용자 정보 삭제하기")
    @Test
    void removeUser() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders
            .delete("/users/{id}", user.getId());

        this.mockMvc.perform(request)
            .andExpect(status().isOk())
            .andDo(
                document("user-remove",
                    responseFields(
                        fieldWithPath("data").type(JsonFieldType.NULL).description("데이터"),
                        fieldWithPath("status").type(JsonFieldType.NUMBER).description("상태코드")
                    )
                )
            );
    }
}