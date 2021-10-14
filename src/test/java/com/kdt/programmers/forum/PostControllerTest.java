package com.kdt.programmers.forum;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kdt.programmers.forum.transfer.PostDto;
import com.kdt.programmers.forum.transfer.request.PostRequest;
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

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
class PostControllerTest {

    @Autowired
    private PostService postService;

    @Autowired
    private PostJpaRepository postJpaRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void clean() {
        postJpaRepository.deleteAll();
    }

    @Test
    @DisplayName("게시글을 저장할 수 있다")
    void savePost() throws Exception {
        // Given
        PostRequest postRequest = new PostRequest("test title", "");

        // When Then
        mockMvc
            .perform(
                post("/api/v1/posts")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(postRequest))
            )
            .andExpect(status().isCreated())
            .andDo(print())
            .andDo(
                document("save-post",
                    preprocessRequest(prettyPrint()),
                    preprocessResponse(prettyPrint()),
                    requestFields(
                        fieldWithPath("title").type(JsonFieldType.STRING).description("post title"),
                        fieldWithPath("content").type(JsonFieldType.STRING).description("post content")
                    ),
                    responseFields(
                        fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("id"),
                        fieldWithPath("data.title").type(JsonFieldType.STRING).description("title"),
                        fieldWithPath("data.content").type(JsonFieldType.STRING).description("content"),
                        fieldWithPath("data.createdAt").type(JsonFieldType.STRING).description("created at"),
                        fieldWithPath("data.createdBy").type(JsonFieldType.NULL).description("created by"),
                        fieldWithPath("serverDateTime").type(JsonFieldType.STRING).description("server time")
                    )
                )
            );
    }


    @Test
    @DisplayName("게시글을 ID로 조회할 수 있다")
    void testGetPost() throws Exception {
        // Given
        PostDto dto = new PostDto("test title", "");
        PostDto post = postService.savePost(dto);

        // When Then
        mockMvc
            .perform(
                get("/api/v1/posts/{postId}", post.getId())
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk())
            .andDo(print())
            .andDo(
                document("get-post-by-id",
                    preprocessResponse(prettyPrint()),
                    responseFields(
                        fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("id"),
                        fieldWithPath("data.title").type(JsonFieldType.STRING).description("title"),
                        fieldWithPath("data.content").type(JsonFieldType.STRING).description("content"),
                        fieldWithPath("data.createdAt").type(JsonFieldType.STRING).description("created at"),
                        fieldWithPath("data.createdBy").type(JsonFieldType.NULL).description("created by"),
                        fieldWithPath("serverDateTime").type(JsonFieldType.STRING).description("server time")
                    )
                )
            );
    }

    @Test
    @DisplayName("게시글을 페이지로 조회할 수 있다")
    void testGetPosts() throws Exception {
        // Given
        postService.savePost(new PostDto("test title", ""));
        postService.savePost(new PostDto("test title", ""));
        postService.savePost(new PostDto("test title", ""));
        postService.savePost(new PostDto("test title", ""));

        // When Then
        final String SIZE = "3";
        final String PAGE = "0";
        mockMvc
            .perform(
                get("/api/v1/posts")
                    .param("size", SIZE)
                    .param("page", PAGE)
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.content", hasSize(3)))
            .andDo(print())
            .andDo(
                document("get-posts-by-page",
                    preprocessResponse(prettyPrint()),
                    responseFields(
                        fieldWithPath("data.content[].id").type(JsonFieldType.NUMBER).description("id"),
                        fieldWithPath("data.content[].title").type(JsonFieldType.STRING).description("title"),
                        fieldWithPath("data.content[].content").type(JsonFieldType.STRING).description("content"),
                        fieldWithPath("data.content[].createdAt").type(JsonFieldType.STRING).description("created at"),
                        fieldWithPath("data.content[].createdBy").type(JsonFieldType.NULL).description("created by"),
                        fieldWithPath("data.totalPages").type(JsonFieldType.NUMBER).description("total pages"),
                        fieldWithPath("data.totalElements").type(JsonFieldType.NUMBER).description("total elements"),
                        fieldWithPath("serverDateTime").type(JsonFieldType.STRING).description("server time")
                    )
                )
            );

        // When Then
        final String NEXT_PAGE = "1";
        mockMvc
            .perform(
                get("/api/v1/posts")
                    .param("size", SIZE)
                    .param("page", NEXT_PAGE)
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.content", hasSize(1)))
            .andDo(print());
    }

    @Test
    @DisplayName("게시글을 수정할 수 있다")
    void testUpdatePost() throws Exception {
        // Given
        PostDto post = postService.savePost(new PostDto("test post", ""));
        PostRequest updateRequest = new PostRequest("updated post", "");

        // When Then
        mockMvc
            .perform(
                patch("/api/v1/posts/{postId}", post.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(updateRequest))
            )
            .andExpect(status().isOk())
            .andDo(print())
            .andDo(
                document("update-post",
                    preprocessRequest(prettyPrint()),
                    preprocessResponse(prettyPrint()),
                    requestFields(
                        fieldWithPath("title").type(JsonFieldType.STRING).description("post title"),
                        fieldWithPath("content").type(JsonFieldType.STRING).description("post content")
                    ),
                    responseFields(
                        fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("id"),
                        fieldWithPath("data.title").type(JsonFieldType.STRING).description("title"),
                        fieldWithPath("data.content").type(JsonFieldType.STRING).description("content"),
                        fieldWithPath("data.createdAt").type(JsonFieldType.STRING).description("created at"),
                        fieldWithPath("data.createdBy").type(JsonFieldType.NULL).description("created by"),
                        fieldWithPath("serverDateTime").type(JsonFieldType.STRING).description("server time")
                    )
                )
            );
    }
}
