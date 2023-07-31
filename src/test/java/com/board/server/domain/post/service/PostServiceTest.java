package com.board.server.domain.post.service;

import com.board.server.domain.post.dto.request.CreatePostRequest;
import com.board.server.domain.post.dto.request.UpdatePostRequest;
import com.board.server.domain.post.dto.response.PostResponse;
import com.board.server.domain.post.entity.Post;
import com.board.server.domain.post.entity.PostRepository;
import com.board.server.domain.user.entity.User;
import com.board.server.domain.user.entity.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class PostServiceTest {
    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    User user1;
    Post post1;

    @BeforeEach
    void setUp() {
        user1 = User.builder()
                .name("밍키")
                .age(19).build();
        user1.setHobby("공놀이");

        userRepository.save(user1);

        CreatePostRequest createPostRequest = new CreatePostRequest("제목1", "내용1");
        Long postId = postService.createPost(createPostRequest, user1.getUserId()).postId();
        post1 = postRepository.findById(postId).get();
    }

    @AfterEach
    void tearDown() {
        postRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void createPostTest() {
        CreatePostRequest createPostRequest = new CreatePostRequest("제목test", "내용test");
        PostResponse postResponse = postService.createPost(createPostRequest, user1.getUserId());

        Post saved = postRepository.findById(postResponse.postId())
                .orElse(null);

        assertThat(saved).isNotNull();
        assertThat(saved.getTitle()).isEqualTo("제목test");
        assertThat(saved.getContent()).isEqualTo("내용test");
    }

    @Test
    void getOnePostTest() {
        PostResponse postResponse = postService.getPost(post1.getPostId());

        assertThat(postResponse.postId()).isEqualTo(post1.getPostId());
        assertThat(postResponse.title()).isEqualTo("제목1");
        assertThat(postResponse.content()).isEqualTo("내용1");
        assertThat(postResponse.createdBy()).isEqualTo(user1.getName());
    }

    @Test
    void updatePostTest() {
        UpdatePostRequest request = new UpdatePostRequest("바뀐 제목", "바뀐 내용");
        postService.updatePost(request, post1.getPostId(), user1.getUserId());
        Post updated = postRepository.findById(post1.getPostId()).get();

        assertThat(updated.getTitle()).isEqualTo("바뀐 제목");
        assertThat(updated.getContent()).isEqualTo("바뀐 내용");
    }
}