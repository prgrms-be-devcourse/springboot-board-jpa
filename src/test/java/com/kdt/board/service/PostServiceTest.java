package com.kdt.board.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kdt.board.domain.Post;
import com.kdt.board.dto.post.PostRequest;
import com.kdt.board.dto.user.UserRequest;
import com.kdt.board.repository.PostRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
class PostServiceTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    ObjectMapper objectMapper;

    private static UserRequest userRequest;
    private static PostRequest postRequest;
    private static Long setPostId;

    @BeforeEach
    void createPost() {
        userRequest = new UserRequest("set user");

        postRequest = new PostRequest("set title", "set content", userRequest.getName());

        setPostId = postService.createPost(postRequest);

    }

    @AfterEach
    void tearDown() {
        postRepository.deleteAll();
    }

    @Test
    void findOnePost() {
        // Given
        Long postId = setPostId;
        // When
        Post onePost = postService.findOnePost(postId).get();
        // Then
        assertThat(onePost.getId()).isEqualTo(setPostId);
    }

    @Test
    void findAllPosts() {
        // Given // When
        List<Post> posts = postService.findAllPosts();
        // Then
        assertThat(posts.size()).isEqualTo(1);
    }
}