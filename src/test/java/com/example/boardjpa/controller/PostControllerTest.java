package com.example.boardjpa.controller;

import com.example.boardjpa.domain.User;
import com.example.boardjpa.dto.CreatePostRequestDto;
import com.example.boardjpa.dto.UpdatePostRequestDto;
import com.example.boardjpa.exception.custom.FieldBlankException;
import com.example.boardjpa.repository.UserRepository;
import com.example.boardjpa.service.PostService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@AutoConfigureRestDocs
@AutoConfigureMockMvc
@SpringBootTest
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PostService postService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @DisplayName("잘못된 url 테스트")
    void testInvalidApi() throws Exception {
        mockMvc.perform(get("/api/v1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    @DisplayName("Post 생성 테스트")
    void testCreatePost() throws Exception {
        //Given
        User user = userRepository
                .save(new User("박상혁", 25, "음주"));
        CreatePostRequestDto createPostRequestDto
                = new CreatePostRequestDto("제목", "내용", user.getId());

        //When //Then
        mockMvc.perform(post("/api/v1/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createPostRequestDto)))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("post-create"
                                , requestFields(
                                        fieldWithPath("title")
                                                .type(JsonFieldType.STRING)
                                                .description("title")
                                        , fieldWithPath("content")
                                                .type(JsonFieldType.STRING)
                                                .description("content")
                                        , fieldWithPath("userId")
                                                .type(JsonFieldType.NUMBER)
                                                .description("userId")
                                ), responseFields(
                                        fieldWithPath("postId")
                                                .type(JsonFieldType.NUMBER)
                                                .description("postId")
                                )
                        )
                );
    }

    @Test
    @DisplayName("제목 없이 Post 생성 테스트")
    void testCreateWithNoTitle() {
        //Given
        User user = userRepository
                .save(new User("박상혁", 25, "음주"));
        assertThatThrownBy(() -> new CreatePostRequestDto(
                null, "내용", user.getId()))
                .isInstanceOf(FieldBlankException.class);
    }

    @Test
    @DisplayName("User 없이 Post 생성 테스트")
    void testCreateWithNoUser() {
        assertThatThrownBy(() -> new CreatePostRequestDto(
                "제목", "내용", null))
                .isInstanceOf(FieldBlankException.class);
    }

    @Test
    @DisplayName("Post 단건 조회 테스트")
    void testFindPost() throws Exception {
        //Given
        User user = userRepository
                .save(new User("박상혁", 25, "음주"));
        Long postId = postService
                .createPost(new CreatePostRequestDto(
                        "제목", "내용", user.getId()))
                .getPostId();

        //When //Then
        mockMvc.perform(get("/api/v1/posts/{postId}", postId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print()).andDo(document("find-post",
                        responseFields(
                                fieldWithPath("id")
                                        .type(JsonFieldType.NUMBER)
                                        .description("postId")
                                , fieldWithPath("title")
                                        .type(JsonFieldType.STRING)
                                        .description("title")
                                , fieldWithPath("content")
                                        .type(JsonFieldType.STRING)
                                        .description("content")
                                , fieldWithPath("user")
                                        .type(JsonFieldType.OBJECT)
                                        .description("user")
                                , fieldWithPath("createdAt")
                                        .type(JsonFieldType.STRING)
                                        .description("createdAt")
                                , fieldWithPath("createdBy")
                                        .type(JsonFieldType.STRING)
                                        .description("createdBy")
                                , fieldWithPath("user.id")
                                        .type(JsonFieldType.NUMBER)
                                        .description("user.id")
                                , fieldWithPath("user.name")
                                        .type(JsonFieldType.STRING)
                                        .description("user.name")
                                , fieldWithPath("user.age")
                                        .type(JsonFieldType.NUMBER)
                                        .description("user.age")
                                , fieldWithPath("user.hobby")
                                        .type(JsonFieldType.STRING)
                                        .description("user.hobby")
                                , fieldWithPath("user.createdAt")
                                        .type(JsonFieldType.STRING)
                                        .description("user.createdAt")
                                , fieldWithPath("user.createdBy")
                                        .type(JsonFieldType.STRING)
                                        .description("user.createdBy")
                        )
                ));
    }

    @Test
    @DisplayName("잘못된 ID로 Post 단건 조회 테스트")
    void testFindInvalidPost() throws Exception {
        mockMvc.perform(get("/api/v1/posts/{postId}", 11)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    @DisplayName("Pagination을 이용한 Post 다건 조회 테스트")
    void testFindPosts() throws Exception {
        //Given
        User user = userRepository
                .save(new User("박상혁", 25, "음주"));
        Long postId = postService
                .createPost(new CreatePostRequestDto(
                        "제목", "내용", user.getId()))
                .getPostId();

        //When //Then
        mockMvc.perform(get("/api/v1/posts")
                        .param("page", String.valueOf(0))
                        .param("size", String.valueOf(10))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(print()).andDo(document("find-posts"
                        , responseFields(
                                fieldWithPath("page")
                                        .type(JsonFieldType.NUMBER)
                                        .description("page")
                                , fieldWithPath("size")
                                        .type(JsonFieldType.NUMBER)
                                        .description("size")
                                , fieldWithPath("posts[]")
                                        .type(JsonFieldType.ARRAY)
                                        .description("posts")
                                , fieldWithPath("posts[].id")
                                        .type(JsonFieldType.NUMBER)
                                        .description("posts.postId")
                                , fieldWithPath("posts[].title")
                                        .type(JsonFieldType.STRING)
                                        .description("posts.title")
                                , fieldWithPath("posts[].content")
                                        .type(JsonFieldType.STRING)
                                        .description("posts.content")
                                , fieldWithPath("posts[].user")
                                        .type(JsonFieldType.OBJECT)
                                        .description("posts.user")
                                , fieldWithPath("posts[].createdAt")
                                        .type(JsonFieldType.STRING)
                                        .description("posts.createdAt")
                                , fieldWithPath("posts[].createdBy")
                                        .type(JsonFieldType.STRING)
                                        .description("posts.createdBy")
                                , fieldWithPath("posts[].user.id")
                                        .type(JsonFieldType.NUMBER)
                                        .description("posts.user.id")
                                , fieldWithPath("posts[].user.name")
                                        .type(JsonFieldType.STRING)
                                        .description("posts.user.name")
                                , fieldWithPath("posts[].user.age")
                                        .type(JsonFieldType.NUMBER)
                                        .description("posts.user.age")
                                , fieldWithPath("posts[].user.hobby")
                                        .type(JsonFieldType.STRING)
                                        .description("posts.user.hobby")
                                , fieldWithPath("posts[].user.createdAt")
                                        .type(JsonFieldType.STRING)
                                        .description("posts.user.createdAt")
                                , fieldWithPath("posts[].user.createdBy")
                                        .type(JsonFieldType.STRING)
                                        .description("posts.user.createdBy")
                        )
                ));
    }

    @Test
    @DisplayName("Post 갱신 테스트")
    void testUpdatePost() throws Exception {
        //Given
        User user = userRepository
                .save(new User("박상혁", 25, "음주"));
        Long postId = postService
                .createPost(new CreatePostRequestDto(
                        "제목", "내용", user.getId()))
                .getPostId();

        //When //Then
        UpdatePostRequestDto updatePostRequestDto
                = new UpdatePostRequestDto("바뀐 내용");
        mockMvc.perform(post("/api/v1/posts/{postId}", postId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatePostRequestDto)))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("update-post"
                        , requestFields(
                                fieldWithPath("content")
                                        .type(JsonFieldType.STRING)
                                        .description("content")
                        )));
    }

    @Test
    @DisplayName("내용 없이 Post 갱신 테스트")
    void testUpdateWithNoContent() {
        //Given
        User user = userRepository
                .save(new User("박상혁", 25, "음주"));
        Long postId = postService
                .createPost(new CreatePostRequestDto(
                        "제목", "내용", user.getId()))
                .getPostId();

        //When //Then
        assertThatThrownBy(() -> new UpdatePostRequestDto(null))
                .isInstanceOf(FieldBlankException.class);
    }

}