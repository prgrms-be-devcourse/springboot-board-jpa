package com.example.springbootboardjpa.controller;


import com.example.springbootboardjpa.dto.PostDTO;
import com.example.springbootboardjpa.model.Post;
import com.example.springbootboardjpa.model.User;
import com.example.springbootboardjpa.repoistory.PostJpaRepository;
import com.example.springbootboardjpa.repoistory.UserJpaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@AutoConfigureRestDocs
@AutoConfigureMockMvc
@SpringBootTest
@Transactional
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserJpaRepository userJpaRepository;

    @Autowired
    private PostJpaRepository postJpaRepository;

    private User user;

    @BeforeEach
    public void setUp() {
        user = new User("이름", 99);
        user = userJpaRepository.save(user);
    }

    @Test
    @DisplayName("post를 정상 생성한다.")
    public void createPost() throws Exception {
        // Given
        PostDTO.Save postDto = new PostDTO.Save("제목", "내용", user.getId());

        // When // Then
        mockMvc.perform(post("/api/v1/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postDto)))
                .andExpect(status().isCreated())
                .andExpect(redirectedUrlPattern("/api/v1/posts/*"))
                .andDo(print())
                .andDo(document("post-save",
                        requestFields(fieldWithPath("title").type(JsonFieldType.STRING).description("post title"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("post content"),
                                fieldWithPath("userId").type(JsonFieldType.NUMBER).description("user id")),
                        responseFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("post id")
                        )
                ));
    }

    @Test
    @DisplayName("title이 null이면 post를 생성할 수 없다.")
    public void nullTitleCreateFailTest() throws Exception {
        // Given
        PostDTO.Save postDto = new PostDTO.Save(null, "내용", user.getId());

        // When // Then
        mockMvc.perform(post("/api/v1/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postDto)))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    @DisplayName("context가 null이면 post를 생성할 수 없다.")
    public void nullContextCreateFailTest() throws Exception {
        // Given
        PostDTO.Save postDto = new PostDTO.Save("제목", null, user.getId());

        // When // Then
        mockMvc.perform(post("/api/v1/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postDto)))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    @DisplayName("user id가 null이면 post를 생성할 수 없다.")
    public void nullUserIdCreateFailTest() throws Exception {
        // Given
        PostDTO.Save postDto = new PostDTO.Save("제목", "내용", null);

        // When // Then
        mockMvc.perform(post("/api/v1/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postDto)))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    @DisplayName("존재하지 않는 userId 일 경우, post를 생성할 수 없다.")
    public void nullUserCreateFailTest() throws Exception {
        // Given
        assertThat(user.getId()).isNotEqualTo(1);
        PostDTO.Save postDto = new PostDTO.Save("제목", "내용", 1L);

        // When // Then
        mockMvc.perform(post("/api/v1/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postDto)))
                .andExpect(status().isNotFound())
                .andDo(print());
    }


    @Test
    @DisplayName("post를 정상 업데이트한다.")
    public void updatePost() throws Exception {
        // Given
        var post = postJpaRepository.save(new Post("제목", "내용", user));
        PostDTO.Request postDto = new PostDTO.Request("update_title", "update_context", user.getId());

        // When // Then
        mockMvc.perform(patch("/api/v1/posts/{id}", post.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postDto)))
                .andExpect(status().isOk())
                .andExpect(redirectedUrl("/api/v1/posts/" + post.getId()))
                .andDo(print())
                .andDo(document("post-update",
                        pathParameters(
                                parameterWithName("id").description("post Id")
                        ),
                        requestFields(fieldWithPath("title").type(JsonFieldType.STRING).description("update title"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("update content"),
                                fieldWithPath("userId").type(JsonFieldType.NUMBER).description("request user id"))
                ));
    }

    @Test
    @DisplayName("title이 null이면 post를 update할 수 없다.")
    public void nullTitleUpdateFailTest() throws Exception {
        // Given
        var post = postJpaRepository.save(new Post("제목", "내용", user));
        PostDTO.Request postDto = new PostDTO.Request(null, "update_context", user.getId());

        // When // Then
        mockMvc.perform(patch("/api/v1/posts/" + post.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postDto)))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    @DisplayName("context이 null이면 post를 update할 수 없다.")
    public void nullContextUpdateFailTest() throws Exception {
        // Given
        var post = postJpaRepository.save(new Post("제목", "내용", user));
        PostDTO.Request postDto = new PostDTO.Request("update_title", null, user.getId());

        // When // Then
        var id = post.getId();
        mockMvc.perform(patch("/api/v1/posts/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postDto)))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    @DisplayName("post를 정상 조회한다.")
    public void getPostById() throws Exception {
        // Given
        var post = new Post("제목", "내용", user);
        post.setCreatedBy(user.getName());
        post = postJpaRepository.save(post);

        // When // Then
        var id = post.getId();
        mockMvc.perform(get("/api/v1/posts/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(post.getTitle()))
                .andExpect(jsonPath("$.content").value(post.getContent()))
                .andDo(print())
                .andDo(document("find-post-one",
                        pathParameters(
                                parameterWithName("id").description("post Id")
                        ),
                        responseFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("post id"),
                                fieldWithPath("title").type(JsonFieldType.STRING).description("post title"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("post content"),
                                fieldWithPath("createdBy").type(JsonFieldType.STRING).description("post createdBy"),
                                fieldWithPath("createdAt").type(JsonFieldType.STRING).description("post createdAt")
                        )
                ));
    }

    @Test
    @DisplayName("post id가 존재하지않을 경우 조회 실패한다.")
    public void getFailById() throws Exception {
        // Given
        var nonPresentedId = 0L;
        assertThat(postJpaRepository.findById(nonPresentedId).isEmpty()).isTrue();

        // When // Then
        mockMvc.perform(get("/api/v1/posts/" + nonPresentedId))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    @DisplayName("posts page 정상 조회한다.")
    public void getAllByPage() throws Exception {
        // Given
        List<Post> posts = Arrays.asList(new Post("test_title0", "content0", user),
                new Post("test_title1", "content1", user),
                new Post("test_title2", "content2", user),
                new Post("test_title3", "content3", user),
                new Post("test_title4", "content4", user),
                new Post("test_title5", "content5", user),
                new Post("test_title6", "content6", user));
        postJpaRepository.saveAll(posts);


        // When // Then
        mockMvc.perform(get("/api/v1/posts")
                        .param("page", String.valueOf(0))
                        .param("size", String.valueOf(5)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(5))
                .andExpect(jsonPath("$.first").value(true))
                .andExpect(jsonPath("$.size").value(5))
                .andExpect(jsonPath("$.number").value(0))
                .andDo(print());
    }
}