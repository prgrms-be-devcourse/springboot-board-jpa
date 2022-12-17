package com.prgrms.jpa.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prgrms.jpa.MysqlTestContainer;
import com.prgrms.jpa.controller.dto.post.CreatePostRequest;
import com.prgrms.jpa.controller.dto.post.UpdatePostRequest;
import com.prgrms.jpa.service.PostService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.patch;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@AutoConfigureRestDocs
@SpringBootTest
class PostControllerTest extends MysqlTestContainer {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PostService postService;

    @Test
    @DisplayName("게시글을 생성한다.")
    @Sql("classpath:schema.sql")
    void create() throws Exception {
        String title = "제목2";
        String content = "내용2";
        long userId = 1L;
        CreatePostRequest postRequest = new CreatePostRequest(title, content, userId);

        mockMvc.perform(post("/api/v1/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postRequest)))
                .andExpect(status().isCreated())
                .andDo(print())
                .andDo(document("post-create",
                        requestFields(
                                fieldWithPath("title").type(JsonFieldType.STRING).description("게시글 제목"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("게시글 내용"),
                                fieldWithPath("userId").type(JsonFieldType.NUMBER).description("작성자 아이디")
                        ), responseFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("생성된 게시글 아이디")
                        ))
                );
    }

    @Test
    @DisplayName("게시글 목록을 조회한다.")
    @Sql("classpath:schema.sql")
    void findAll() throws Exception {
        mockMvc.perform(get("/api/v1/posts")
                        .param("page", String.valueOf(0))
                        .param("size", String.valueOf(15))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("post-findAll",
                        responseFields(
                                fieldWithPath("totalPages").type(JsonFieldType.NUMBER).description("총 페이지"),
                                fieldWithPath("totalCount").type(JsonFieldType.NUMBER).description("총 개수"),
                                fieldWithPath("posts[]").type(JsonFieldType.ARRAY).description("게시글들"),
                                fieldWithPath("posts[].id").type(JsonFieldType.NUMBER).description("게시글 아이디"),
                                fieldWithPath("posts[].title").type(JsonFieldType.STRING).description("게시글 제목"),
                                fieldWithPath("posts[].content").type(JsonFieldType.STRING).description("게시글 내용"),
                                fieldWithPath("posts[].userId").type(JsonFieldType.NUMBER).description("작성자 아이디")
                        ))
                );
    }

    @Test
    @DisplayName("게시글을 아이디로 조회한다.")
    @Sql("classpath:schema.sql")
    void findById() throws Exception {
        long id = 1L;

        mockMvc.perform(get("/api/v1/posts/{id}", id))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("post-findById",
                        pathParameters(
                                parameterWithName("id").description("수정할 게시글 아이디")
                        ),
                        responseFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("게시글 아이디"),
                                fieldWithPath("title").type(JsonFieldType.STRING).description("게시글 제목"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("게시글 내용"),
                                fieldWithPath("userId").type(JsonFieldType.NUMBER).description("작성자 아이디")
                        ))
                );
    }

    @Test
    @DisplayName("게시글의 제목과 내용을 수정한다.")
    @Sql("classpath:schema.sql")
    void update() throws Exception {
        long id = 1L;
        String title = "제목수정";
        String content = "내용수정";
        UpdatePostRequest postRequest = new UpdatePostRequest(title, content);

        mockMvc.perform(patch("/api/v1/posts/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postRequest)))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("post-update",
                        pathParameters(
                                parameterWithName("id").description("수정할 게시글 아이디")
                        ),
                        requestFields(
                                fieldWithPath("title").type(JsonFieldType.STRING).description("수정하는 제목"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("수정하는 내용")
                        ))
                );
    }
}