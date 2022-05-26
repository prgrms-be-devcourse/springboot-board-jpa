package com.example.springbootboardjpa.domain;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PostRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    private Post post;

    private final Pageable pageable = PageRequest.of(0, 10);

    @BeforeAll
    void setup() {
        User user = User.builder()
                .id(UUID.randomUUID())
                .age(29)
                .name("nnagman")
                .hobby("thinking")
                .build();
        post = Post.builder()
                .title("hallo")
                .content("yallu")
                .build();
        user.addPost(post);
        userRepository.save(user);
        post = postRepository.save(post);
    }

    @AfterAll
    void tearDown() {
        postRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("게시물을 아이디로 검색 할 수 있다.")
    void FIND_POST_BY_ID_TEST() {
        // given
        Long id = post.getId();

        // when
        Optional<Post> byId = postRepository.findById(id);

        // then
        assertThat(byId).contains(post);
    }

    @Test
    @DisplayName("게시물을 제목으로 검색 할 수 있다.")
    void FIND_POST_BY_TITLE_TEST() {
        // given
        String title = post.getTitle();

        // when
        Page<Post> posts = postRepository.findAllByTitle(title, pageable);

        // then
        assertThat(posts).hasSize(1);
    }

    @Test
    @DisplayName("게시물을 내용으로 검색 할 수 있다.")
    void FIND_POST_BY_CONTENT_TEST() {
        // given
        String content = post.getContent();

        // when
        Page<Post> posts = postRepository.findAllByContent(content, pageable);

        // then
        assertThat(posts).hasSize(1);
    }

    @Test
    @DisplayName("게시물을 수정 할 수 있습니다.")
    void UPDATE_POST_TEST() {
        // given
        post.toBuilder()
                .title("updated")
                .build();

        // when
        Post updated = postRepository.save(post);

        // then
        assertThat(updated.getTitle()).isEqualTo(post.getTitle());
    }

    @Test
    @DisplayName("게시물을 삭제 할 수 있습니다.")
    void DELETE_POST_TEST() {
        // given
        Long id = post.getId();

        // when
        postRepository.deleteById(id);

        // then
        assertThat(postRepository.findById(id)).isEmpty();
    }
}
