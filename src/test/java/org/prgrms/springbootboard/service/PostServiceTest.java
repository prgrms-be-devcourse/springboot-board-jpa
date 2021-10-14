package org.prgrms.springbootboard.service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.prgrms.springbootboard.domain.PostRepository;
import org.prgrms.springbootboard.domain.User;
import org.prgrms.springbootboard.domain.UserRepository;
import org.prgrms.springbootboard.dto.PostCreateRequest;
import org.prgrms.springbootboard.dto.PostCreateResponse;
import org.prgrms.springbootboard.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
        PostCreateResponse response = postService.createPost(request);

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
}
