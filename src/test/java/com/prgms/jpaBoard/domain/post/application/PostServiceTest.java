package com.prgms.jpaBoard.domain.post.application;

import com.prgms.jpaBoard.domain.post.application.dto.PostResponse;
import com.prgms.jpaBoard.domain.post.presentation.dto.PostSaveRequest;
import com.prgms.jpaBoard.domain.post.presentation.dto.PostUpdateRequest;
import com.prgms.jpaBoard.domain.user.HobbyType;
import com.prgms.jpaBoard.domain.user.User;
import com.prgms.jpaBoard.domain.user.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class PostServiceTest {

    @Autowired
    PostService postService;

    @Autowired
    UserRepository userRepository;

    @Test
    @DisplayName("게시글을 작성할 수 있다.")
    void post() {
        // Given
        User user = new User("재웅", 28, HobbyType.CLIMBING);
        User savedUser = userRepository.save(user);

        PostSaveRequest postSaveRequest = new PostSaveRequest("aaa", "금요일 밤에 미션하는중.", savedUser.getId());

        // When
        Long savedPostId = postService.post(postSaveRequest);

        // Then
        assertThat(savedPostId).isNotNull();
    }

    @Test
    @DisplayName("postId로 게시글을 조회할 수 있다.")
    void readById() {
        // Given
        User user = new User("재웅", 28, HobbyType.CLIMBING);
        User savedUser = userRepository.save(user);

        PostSaveRequest postSaveRequest = new PostSaveRequest("aaa", "금요일 밤에 미션하는중.", savedUser.getId());
        Long savedPostId = postService.post(postSaveRequest);

        // When
        PostResponse postResponse = postService.readPost(savedPostId);

        // Then
        assertThat(postResponse.id()).isEqualTo(savedPostId);
        assertThat(postResponse.title()).isEqualTo(postSaveRequest.title());
        assertThat(postResponse.content()).isEqualTo(postSaveRequest.content());
    }

    @Test
    @DisplayName("게시글을 수정할 수 있다.")
    void updatePost() {
        // Given
        User user = new User("재웅", 28, HobbyType.CLIMBING);
        User savedUser = userRepository.save(user);

        PostSaveRequest postSaveRequest = new PostSaveRequest("aaa", "금요일 밤에 미션하는중.", savedUser.getId());
        Long savedPostId = postService.post(postSaveRequest);

        PostUpdateRequest postUpdateRequest = new PostUpdateRequest("bbb", "곧 토요일");

        // When
        PostResponse postResponse = postService.updatePost(savedPostId, postUpdateRequest);

        // Then
        assertThat(postResponse.id()).isEqualTo(savedPostId);
        assertThat(postResponse.title()).isEqualTo(postUpdateRequest.title());
        assertThat(postResponse.content()).isEqualTo(postUpdateRequest.content());
    }
}