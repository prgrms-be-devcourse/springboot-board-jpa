package org.prgrms.springbootboard.service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.prgrms.springbootboard.domain.Post;
import org.prgrms.springbootboard.domain.PostRepository;
import org.prgrms.springbootboard.domain.User;
import org.prgrms.springbootboard.domain.UserRepository;
import org.prgrms.springbootboard.dto.PostCreateRequest;
import org.prgrms.springbootboard.dto.PostResponse;
import org.prgrms.springbootboard.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
class PostServiceTest {

    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    private User writer;
    private Long wrongUserId;

    @BeforeEach
    void setUp() {
        postRepository.deleteAll();
        userRepository.deleteAll();

        writer = new User("jungmi", 24, "유튜브");
        userRepository.save(writer);

        wrongUserId = -1L;
    }

    @Test
    void 게시글을_생성한다() {
        // given
        String title = "title";
        String content = "content";
        PostCreateRequest request = PostCreateRequest.builder()
            .title(title)
            .content(content)
            .writerId(writer.getId())
            .build();

        // when
        PostResponse response = postService.createPost(request);

        // then
        log.info("post id is {}", response.getId());
        assertAll(
            () -> assertThat(response.getTitle()).isEqualTo(title),
            () -> assertThat(response.getContent()).isEqualTo(content),
            () -> assertThat(response.getCreatedAt()).isNotNull(),
            () -> assertThat(response.getLastModifiedAt()).isNotNull()
        );
    }

    @Test
    void 게시글_생성에_실패한다() {
        // given
        PostCreateRequest request = PostCreateRequest.builder()
            .title("title")
            .content("content")
            .writerId(wrongUserId)
            .build();

        // when, then
        assertThatThrownBy(() -> postService.createPost(request))
            .isInstanceOf(NotFoundException.class)
            .hasMessage("존재하지 않는 user입니다, id: " + wrongUserId);
    }

    @Test
    void 아이디로_게시글을_조회한다() {
        // given
        String title = "title";
        String content = "content";
        Post saved = postRepository.save(new Post(title, content, writer));
        Long postId = saved.getId();

        // when
        PostResponse response = postService.findById(postId);

        // then
        assertAll(
            () -> assertThat(response.getTitle()).isEqualTo(title),
            () -> assertThat(response.getContent()).isEqualTo(content)
        );
    }

    @Test
    void 존재하지_않는_아이디로_게시글을_조회한다() {
        // given
        Long wrongPostId = -1L;

        // when, then
        assertThatThrownBy(() -> postService.findById(wrongPostId))
            .isInstanceOf(NotFoundException.class)
            .hasMessage("존재하지 않는 post입니다, id: " + wrongPostId);
    }

    @Test
    void 게시글_전체를_조회한다() {
        // given
        Post saved1 = postRepository.save(new Post("title", "content", writer));
        Post saved2 = postRepository.save(new Post("title2", "content2", writer));

        PageRequest page = PageRequest.of(0, 10);

        // when
        Page<PostResponse> responses = postService.findAll(page);
        List<PostResponse> posts = responses.getContent();

        // then
        assertAll(
            () -> assertThat(responses.getTotalPages()).isEqualTo(1),
            () -> assertThat(responses.getTotalElements()).isEqualTo(2),
            () -> assertThat(posts.get(0).getId()).isEqualTo(saved1.getId()),
            () -> assertThat(posts.get(1).getId()).isEqualTo(saved2.getId())
        );
    }

    @Test
    void 한_유저의_게시글_전체를_조회한다() {
        // given
        User writer2 = new User("jummi", 30, "산책");
        userRepository.save(writer2);
        Post saved1 = postRepository.save(new Post("title", "content", writer));
        Post saved2 = postRepository.save(new Post("title2", "content2", writer2));
        Post saved3 = postRepository.save(new Post("title3", "content3", writer2));
        PageRequest page = PageRequest.of(0, 10);

        // when
        Page<PostResponse> responses1 = postService.findAllByWriter(writer.getId(), page);
        List<PostResponse> posts1 = responses1.getContent();
        Page<PostResponse> responses2 = postService.findAllByWriter(writer2.getId(), page);
        List<PostResponse> posts2 = responses2.getContent();

        // then
        assertAll(
            () -> assertThat(responses1.getTotalPages()).isEqualTo(1),
            () -> assertThat(responses1.getTotalElements()).isEqualTo(1),
            () -> assertThat(posts1.get(0).getId()).isEqualTo(saved1.getId()),

            () -> assertThat(responses2.getTotalPages()).isEqualTo(1),
            () -> assertThat(responses2.getTotalElements()).isEqualTo(2),
            () -> assertThat(posts2.get(0).getId()).isEqualTo(saved2.getId()),
            () -> assertThat(posts2.get(1).getId()).isEqualTo(saved3.getId())
        );
    }
}
