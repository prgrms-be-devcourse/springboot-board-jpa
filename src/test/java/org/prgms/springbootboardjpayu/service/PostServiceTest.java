package org.prgms.springbootboardjpayu.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.prgms.springbootboardjpayu.domain.User;
import org.prgms.springbootboardjpayu.dto.request.CreatePostRequest;
import org.prgms.springbootboardjpayu.dto.request.UpdatePostRequest;
import org.prgms.springbootboardjpayu.dto.response.PostResponse;
import org.prgms.springbootboardjpayu.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
class PostServiceTest {
    @Autowired
    private PostService postService;
    @Autowired
    private UserRepository userRepository;

    @DisplayName("게시글 생성에 성공한다.")
    @Test
    void createPost() {
        // given
        User user = createUser("예림");
        String title = "제목";
        String content = "내용";
        CreatePostRequest request = new CreatePostRequest(title, content, user.getId());

        // when
        PostResponse savedPost = postService.createPost(request);

        // then
        assertThat(savedPost.id()).isNotNull();
        assertThat(savedPost.createdAt()).isNotNull();
        assertThat(savedPost).extracting("title", "content")
                .containsExactly(title, content);
        assertThat(savedPost.user()).extracting("id", "name")
                .containsExactly(user.getId(), user.getName());
    }

    @DisplayName("존재하지 않는 사용자로 게시글 생성에 실패한다.")
    @Test
    void createPostWithNonExistUser() {
        // given
        Long invalidId = 0L;
        CreatePostRequest request = new CreatePostRequest("제목", "내용", invalidId);

        // when then
        assertThatThrownBy(() -> postService.createPost(request))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("존재하지 않는 유저입니다.");
    }

    @DisplayName("작성자가 없으면 게시글 생성에 실패한다.")
    @Test
    void createPostWithoutUser() {
        // given
        CreatePostRequest request = new CreatePostRequest("제목", "내용", null);

        // when then
        assertThatThrownBy(() -> postService.createPost(request))
                .isInstanceOf(ConstraintViolationException.class);
    }

    @DisplayName("제목이 1 ~ 30자 범위를 초과로 게시글 생성에 실패한다.")
    @ParameterizedTest
    @ValueSource(strings = {"", "  ", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"})
    void createPostWithOutOfRangeTitle(String title) {
        // given
        User user = createUser("예림");
        CreatePostRequest request = new CreatePostRequest(title, "내용", user.getId());

        // when then
        assertThatThrownBy(() -> postService.createPost(request))
                .isInstanceOf(ConstraintViolationException.class);
    }

    @DisplayName("게시글 수정에 성공한다.")
    @Test
    void updatePost() {
        // given
        User user = createUser("예림");
        CreatePostRequest request = new CreatePostRequest("제목", "내용", user.getId());
        PostResponse savedPost = postService.createPost(request);

        String newTitle = "제목 수정";
        String newContent = "내용 수정";
        UpdatePostRequest updateRequest = new UpdatePostRequest(newTitle, newContent);

        // when
        PostResponse updatedPost = postService.updatePost(savedPost.id(), updateRequest);

        // then
        assertThat(updatedPost).extracting("title", "content")
                .containsExactly(newTitle, newContent);
    }

    @DisplayName("게시글이 없으면 게시글 수정에 실패한다.")
    @Test
    void updatePostWithNonExistPost() {
        // given
        Long invalidPostId = 999L;
        UpdatePostRequest updateRequest = new UpdatePostRequest("제목 수정", "내용 수정");

        // when then
        assertThatThrownBy(() -> postService.updatePost(invalidPostId, updateRequest))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("존재하지 않는 게시글입니다.");
    }

    @DisplayName("수정된 제목이 1 ~ 30자 범위를 초과로 게시글 수정에 실패한다.")
    @ParameterizedTest
    @ValueSource(strings = {"", "  ", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"})
    void updatePostWithOutOfRangeTitle(String title) {
        // given
        User user = createUser("예림");
        CreatePostRequest request = new CreatePostRequest("제목", "내용", user.getId());
        PostResponse savedPost = postService.createPost(request);

        UpdatePostRequest updateRequest = new UpdatePostRequest(title, "내용 수정");

        // when then
        assertThatThrownBy(() -> postService.updatePost(savedPost.id(), updateRequest))
                .isInstanceOf(ConstraintViolationException.class);
    }

    private User createUser(String name) {
        User user = User.builder().name(name).build();
        return userRepository.save(user);
    }

}