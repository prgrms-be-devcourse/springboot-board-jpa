package com.kdt.board.post.service;

import static org.assertj.core.api.Assertions.*;

import com.kdt.board.common.exception.NotFoundException;
import com.kdt.board.post.dto.request.PostCreateRequestDto;
import com.kdt.board.post.dto.request.PostEditRequestDto;
import com.kdt.board.post.dto.response.PostResponseDto;
import com.kdt.board.user.domain.User;
import com.kdt.board.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class PostServiceTest {

    @Autowired
    PostService postService;

    @Autowired
    UserRepository userRepository;

    @Test
    @DisplayName("Post 저장 가능하다")
    void saveTest() {
        // Given
        User user = new User("name", 27);
        userRepository.save(user);
        PostCreateRequestDto postCreateRequestDto = new PostCreateRequestDto(user.getId(), "title", "content");

        // When
        Long saveId = postService.save(postCreateRequestDto);

        // Then
        PostResponseDto post = postService.getPost(saveId);
        assertThat(saveId).isEqualTo(post.getId());
    }

    @Test
    @DisplayName("모든 게시물을 조회할 수 있다.")
    void getAllPostsTest() {
        // Given
        User user1 = new User("name1", 27);
        User user2 = new User("name2", 27);
        userRepository.save(user1);
        userRepository.save(user2);
        for (int i = 0; i < 20; i++) {
            PostCreateRequestDto postCreateRequestDto1 = new PostCreateRequestDto(user1.getId(), "title"+i, "content"+i);
            PostCreateRequestDto postCreateRequestDto2 = new PostCreateRequestDto(user2.getId(), "title"+i, "content"+i);
            postService.save(postCreateRequestDto1);
            postService.save(postCreateRequestDto2);
        }

        // When
        Page<PostResponseDto> posts = postService.getAllPosts(0);

        // Then
        assertThat(posts.getTotalElements()).isEqualTo(40);
        assertThat(posts.getTotalPages()).isEqualTo(8);
    }

    @Test
    @DisplayName("특정 사용자의 모든 게시물을 조회할 수 있다.")
    void getAllPostsByUserIdTest() {
        // Given
        User user = new User("name", 27);
        userRepository.save(user);
        for (int i = 0; i < 20; i++) {
            PostCreateRequestDto postCreateRequestDto = new PostCreateRequestDto(user.getId(), "title"+i, "content"+i);
            postService.save(postCreateRequestDto);
        }

        // When
        Page<PostResponseDto> posts = postService.getAllPostsByUserId(user.getId(), 0);

        // Then
        assertThat(posts.getTotalElements()).isEqualTo(20);
        assertThat(posts.getTotalPages()).isEqualTo(4);
    }

    @Test
    @DisplayName("게시물을 수정할 수 있다.")
    void editTest() {
        // Given
        User user = new User("name", 27);
        userRepository.save(user);
        PostCreateRequestDto postCreateRequestDto = new PostCreateRequestDto(user.getId(), "title", "content");
        Long saveId = postService.save(postCreateRequestDto);

        // When
        PostEditRequestDto postEditRequestDto = new PostEditRequestDto(saveId, "updateTitle", "updateContent");
        postService.edit(postEditRequestDto);

        // Then
        PostResponseDto post = postService.getPost(saveId);
        assertThat(post.getTitle()).isEqualTo("updateTitle");
        assertThat(post.getContent()).isEqualTo("updateContent");
    }

    @Test
    @DisplayName("게시물을 삭제할 수 있다.")
    void deletePostTest() {
        // Given
        User user = new User("name", 27);
        userRepository.save(user);
        PostCreateRequestDto postCreateRequestDto = new PostCreateRequestDto(user.getId(), "title", "content");
        Long saveId = postService.save(postCreateRequestDto);

        // When
        postService.deletePost(saveId);

        // Then
        assertThatThrownBy(() -> postService.getPost(saveId))
            .isInstanceOf(NotFoundException.class);
    }
}