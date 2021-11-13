package com.poogle.board.controller.post;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.poogle.board.model.post.Post;
import com.poogle.board.service.post.PostService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@AutoConfigureRestDocs
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PostControllerTest {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private PostService postService;

    @BeforeAll
    void setup() {
        PostRequest postRequest = PostRequest.builder()
                .title("post title")
                .content("Describe below. It is the content of the post.")
                .writer("poogle")
                .build();

        Post post = postService.write(postRequest.newPost());
    }

    @Test
    @DisplayName("포스트 추가 테스트")
    void save_success_test() throws Exception {
        PostRequest postRequest = PostRequest.builder()
                .title("title")
                .content("content")
                .writer("writer")
                .build();
        mockMvc.perform(post("/api/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(postRequest)))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("post-save",
                        requestFields(
                                fieldWithPath("title").type(JsonFieldType.STRING).description("request post title"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("request post content"),
                                fieldWithPath("writer").type(JsonFieldType.STRING).description("request post writer")
                        ),
                        responseFields(
                                fieldWithPath("success").type(JsonFieldType.BOOLEAN).description("success value: boolean"),
                                fieldWithPath("response").type(JsonFieldType.OBJECT).description("response"),
                                fieldWithPath("response.id").type(JsonFieldType.NUMBER).description("response post id"),
                                fieldWithPath("response.title").type(JsonFieldType.STRING).description("response post title"),
                                fieldWithPath("response.content").type(JsonFieldType.STRING).description("response post content"),
                                fieldWithPath("response.createdAt").type(JsonFieldType.STRING).description("response post createdAt"),
                                fieldWithPath("response.modifiedAt").type(JsonFieldType.STRING).description("response post modifiedAt"),
                                fieldWithPath("response.createdBy").type(JsonFieldType.STRING).description("response post createdBy"),
                                fieldWithPath("response.modifiedBy").type(JsonFieldType.STRING).description("response post modifiedBy"),
                                fieldWithPath("response.userResponse").type(JsonFieldType.NULL).description("response user"),
                                fieldWithPath("error").type(JsonFieldType.NULL).description("error")
                        )
                ));
    }

    @Test
    @DisplayName("포스트 수정 테스트")
    void edit_success_test() throws Exception {
        PostRequest postRequest = PostRequest.builder()
                .title("new post title")
                .content("It will be the new content of this post")
                .writer("new poogle")
                .build();
        mockMvc.perform(put("/api/posts/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(postRequest)))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("post-edit",
                        requestFields(
                                fieldWithPath("title").type(JsonFieldType.STRING).description("request post title"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("request post content"),
                                fieldWithPath("writer").type(JsonFieldType.STRING).description("request post writer")
                        ),
                        responseFields(
                                fieldWithPath("success").type(JsonFieldType.BOOLEAN).description("success value: boolean"),
                                fieldWithPath("response").type(JsonFieldType.OBJECT).description("response"),
                                fieldWithPath("response.id").type(JsonFieldType.NUMBER).description("response post id"),
                                fieldWithPath("response.title").type(JsonFieldType.STRING).description("response post title"),
                                fieldWithPath("response.content").type(JsonFieldType.STRING).description("response post content"),
                                fieldWithPath("response.createdAt").type(JsonFieldType.STRING).description("response post createdAt"),
                                fieldWithPath("response.modifiedAt").type(JsonFieldType.STRING).description("response post modifiedAt"),
                                fieldWithPath("response.createdBy").type(JsonFieldType.STRING).description("response post createdBy"),
                                fieldWithPath("response.modifiedBy").type(JsonFieldType.STRING).description("response post modifiedBy"),
                                fieldWithPath("response.userResponse").type(JsonFieldType.NULL).description("response user"),
                                fieldWithPath("error").type(JsonFieldType.NULL).description("error")
                        )
                ));
    }

    @Test
    @DisplayName("포스트 단건 조회 테스트")
    void get_one_post() throws Exception {
        mockMvc.perform(get("/api/posts/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("post-get",
                        responseFields(
                                fieldWithPath("success").type(JsonFieldType.BOOLEAN).description("success value: boolean"),
                                fieldWithPath("response").type(JsonFieldType.OBJECT).description("response"),
                                fieldWithPath("response.id").type(JsonFieldType.NUMBER).description("response post id"),
                                fieldWithPath("response.title").type(JsonFieldType.STRING).description("response post title"),
                                fieldWithPath("response.content").type(JsonFieldType.STRING).description("response post content"),
                                fieldWithPath("response.createdAt").type(JsonFieldType.STRING).description("response post createdAt"),
                                fieldWithPath("response.modifiedAt").type(JsonFieldType.STRING).description("response post modifiedAt"),
                                fieldWithPath("response.createdBy").type(JsonFieldType.STRING).description("response post createdBy"),
                                fieldWithPath("response.modifiedBy").type(JsonFieldType.STRING).description("response post modifiedBy"),
                                fieldWithPath("response.userResponse").type(JsonFieldType.NULL).description("response user"),
                                fieldWithPath("error").type(JsonFieldType.NULL).description("error")
                        )
                ));
    }

    @Test
    @DisplayName("포스트 전체 조회 테스트")
    void get_all_post() throws Exception {
        mockMvc.perform(get("/api/posts")
                .param("page", String.valueOf(0))
                .param("size", String.valueOf(10))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

}
