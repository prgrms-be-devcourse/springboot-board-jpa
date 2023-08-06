package com.prgrms.board.post.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prgrms.board.post.controller.dto.PostDetailedRequest;
import com.prgrms.board.user.service.UserService;
import com.prgrms.board.user.service.dto.UserDetailedParam;
import com.prgrms.board.user.service.dto.UserShortResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@AutoConfigureRestDocs
@SpringBootTest
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        UserDetailedParam userDetailedParam = new UserDetailedParam(
                "James",
                25,
                "Baseball");
        UserShortResult savedUser = userService.save(userDetailedParam);
    }

    @Test
    @Order(1)
    @DisplayName("[create] 저장")
    void create_유효한값일때_엔티티로저장() throws Exception {
        // Given
        PostDetailedRequest request = new PostDetailedRequest(
                "이것은 제목입니다.",
                "이것은 내용입니다.",
                1L
        );

        // When // Then
        mockMvc.perform(post("/api/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andDo(print())
                .andDo(document("post-save",
                        requestFields(
                                fieldWithPath("title").type(JsonFieldType.STRING).description("제목"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("내용"),
                                fieldWithPath("userId").type(JsonFieldType.NUMBER).description("유저 아이디")
                        )
                ));
    }

    @Test
    @Order(2)
    @DisplayName("[findById] 단건 조회")
    void findById_유효한값일때_엔티티반환() throws Exception {
        // Given // When // Then
        mockMvc.perform(get("/api/posts/{id}", 1L))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("post-get",
                        pathParameters(
                                parameterWithName("id").description("게시글 아이디")
                        ),
                        responseFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("게시글 아이디"),
                                fieldWithPath("title").type(JsonFieldType.STRING).description("제목"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("내용"),
                                fieldWithPath("createdBy").type(JsonFieldType.STRING).description("생성자"),
                                fieldWithPath("createdDate").type(JsonFieldType.STRING).description("생성일")
                        )
                ));
    }

    @Test
    @Order(3)
    @DisplayName("[findAllByPagination] 페이징 조회")
    void findAllWithPagination_유효한값일때_페이지반환() throws Exception {
        // Given // When // Then
        mockMvc.perform(get("/api/posts")
                        .param("page", "0")
                        .param("size", "10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("post-getPage",
                        responseFields(
                                fieldWithPath("list[].id").type(JsonFieldType.NUMBER).description("게시글 아이디"),
                                fieldWithPath("list[].title").type(JsonFieldType.STRING).description("제목"),
                                fieldWithPath("list[].content").type(JsonFieldType.STRING).description("내용"),
                                fieldWithPath("list[].createdBy").type(JsonFieldType.STRING).description("생성자"),
                                fieldWithPath("list[].createdDate").type(JsonFieldType.STRING).description("생성일")
                        )
                ));
    }

    @Test
    @Order(4)
    @DisplayName("[update] 업데이트")
    void update_유효한값일때_엔티티업데이트() throws Exception {
        // Given
        PostDetailedRequest request = new PostDetailedRequest(
                "이것은 새로운 제목입니다..",
                "이것은 새로운 내용입니다.",
                1L
        );

        // When // Then
        mockMvc.perform(post("/api/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andDo(print())
                .andDo(document("post-update",
                        requestFields(
                                fieldWithPath("title").type(JsonFieldType.STRING).description("제목"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("내용"),
                                fieldWithPath("userId").type(JsonFieldType.NUMBER).description("유저 아이디")
                        ),
                        responseFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("게시글 아이디")
                        )
                ));
    }
}