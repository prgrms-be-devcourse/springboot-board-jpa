package com.maenguin.kdtbbs.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.maenguin.kdtbbs.config.JpaAuditingConfiguration;
import com.maenguin.kdtbbs.converter.BBSConverter;
import com.maenguin.kdtbbs.domain.Post;
import com.maenguin.kdtbbs.domain.User;
import com.maenguin.kdtbbs.dto.PostAddDto;
import com.maenguin.kdtbbs.dto.PostAddResDto;
import com.maenguin.kdtbbs.dto.PostDto;
import com.maenguin.kdtbbs.exception.PostNotFoundException;
import com.maenguin.kdtbbs.exception.UserNotFoundException;
import com.maenguin.kdtbbs.repository.PostRepository;
import com.maenguin.kdtbbs.repository.UserRepository;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@DataJpaTest
@Import({PostService.class, BBSConverter.class, JpaAuditingConfiguration.class})
class PostServiceTest {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostService sut;

    @Test
    @DisplayName("게시글 단건 조회 - 성공 테스트")
    void getPostByIdSuccessTest() {
        //given
        Post originPost = postRepository.save(new Post("title", "content"));
        //when
        assertThat(originPost.getView()).isZero();
        PostDto postDto = sut.getPostById(originPost.getPostId());
        //then
        assertThat(postDto.getId()).isEqualTo(originPost.getPostId());
        assertThat(postDto.getTitle()).isEqualTo(originPost.getTitle());
        assertThat(postDto.getContent()).isEqualTo(originPost.getContent());
        Post findPost = postRepository.getById(originPost.getPostId());
        assertThat(findPost.getView()).isEqualTo(1);
    }

    @Test
    @DisplayName("게시글 단건 조회 - 실패 테스트 (잘못된 게시글 Id)")
    void getPostByIdFailureTest() {
        //given
        Long invalidPostId = 1L;
        //when then
        assertThatThrownBy(() -> sut.getPostById(invalidPostId))
            .isInstanceOf(PostNotFoundException.class);
    }

    @Test
    @DisplayName("게시글 등록 - 성공 테스트")
    void savePostSuccessTest() {
        //given
        User user = new User("user", "piano");
        User savedUser = userRepository.save(user);
        PostAddDto postAddDto = new PostAddDto(savedUser.getUserId(), "title", "content");
        //when
        PostAddResDto postAddResDto = sut.savePost(postAddDto);
        //then
        Optional<Post> findPost = postRepository.findById(postAddResDto.getPostId());
        assertThat(findPost).isPresent();
        assertThat(findPost.get().getUser().getUserId()).isEqualTo(savedUser.getUserId());
        assertThat(findPost.get().getTitle()).isEqualTo(postAddDto.getTitle());
        assertThat(findPost.get().getContent()).isEqualTo(postAddDto.getContent());
    }

    @Test
    @DisplayName("게시글 등록 - 실패 테스트 (잘못된 유저 Id)")
    void savePostFailureTest() {
        //given
        Long invalidUserId = 1L;
        PostAddDto postAddDto = new PostAddDto(invalidUserId, "title", "content");
        //when then
        assertThatThrownBy(() -> sut.savePost(postAddDto))
            .isInstanceOf(UserNotFoundException.class);
    }

    @Test
    @DisplayName("게시글 수정 - 성공 테스트")
    void editPostSuccessTest() {
        //given
        Post originPost = postRepository.save(new Post("title", "content"));
        PostAddDto postAddDto = new PostAddDto(1L, "title2", "content2");
        //when
        PostAddResDto postAddResDto = sut.editPost(originPost.getPostId(), postAddDto);
        //then
        assertThat(postAddResDto.getPostId()).isEqualTo(originPost.getPostId());
        Optional<Post> findPost = postRepository.findById(originPost.getPostId());
        assertThat(findPost).isPresent();
        assertThat(findPost.get().getTitle()).isEqualTo(postAddDto.getTitle());
        assertThat(findPost.get().getContent()).isEqualTo(postAddDto.getContent());
    }

    @Test
    @DisplayName("게시글 수정 - 실패 테스트(잘못된 게시글 Id)")
    void editPostFailureTest() {
        //given
        Long invalidPostId = 1L;
        PostAddDto postAddDto = new PostAddDto(1L, "title2", "content2");
        //when then
        assertThatThrownBy(() -> sut.editPost(invalidPostId, postAddDto))
            .isInstanceOf(PostNotFoundException.class);
    }
}
