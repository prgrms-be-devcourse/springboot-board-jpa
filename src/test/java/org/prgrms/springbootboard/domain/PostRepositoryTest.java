package org.prgrms.springbootboard.domain;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
class PostRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    private User writer;
    private String writerName;

    private Post post;
    private String title;
    private String content;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
        postRepository.deleteAll();

        writerName = "jungmi";
        writer = new User(writerName, 24, "쇼핑");
        userRepository.save(writer);

        title = "제목";
        content = "내용";
        post = new Post(title, content, writer);
    }

    @Test
    void 게시글을_저장한다() {
        // when
        Post saved = postRepository.save(post);

        // then
        assertAll(
            () -> assertThat(saved.getId()).isNotNull(),
            () -> assertThat(saved.getTitle()).isEqualTo(title),
            () -> assertThat(saved.getContent()).isEqualTo(content),
            () -> assertThat(saved.getWriter()).isNotNull(),
            () -> assertThat(saved.getWriter().getName()).isEqualTo(writerName),
            () -> assertThat(saved.getCreatedAt()).isNotNull(),
            () -> assertThat(saved.getLastModifiedAt()).isAfterOrEqualTo(saved.getCreatedAt())
        );
    }

    @Transactional
    @Test
    void 아이디로_게시글을_조회한다() {
        // given
        Post saved = postRepository.save(post);
        Long id = saved.getId();

        // when
        Optional<Post> found = postRepository.findById(id);

        // then
        assertAll(
            () -> assertThat(found.isPresent()).isTrue(),
            () -> assertThat(found.get().getTitle()).isEqualTo(title),
            () -> assertThat(found.get().getContent()).isEqualTo(content),
            () -> assertThat(found.get().getWriter().getPosts().size()).isEqualTo(1),   // 연관관계 테스트
            () -> assertThat(found.get().getWriter().getPosts().get(0)).isEqualTo(found.get())
        );
    }

    @Test
    void 모든_게시글을_조회한다() {
        // given
        Post post2 = new Post("title2", "content2", writer);
        Post saved1 = postRepository.save(post);
        Post saved2 = postRepository.save(post2);

        // when
        List<Post> all = postRepository.findAll();

        // then
        assertAll(
            () -> assertThat(all.size()).isEqualTo(2),
            () -> assertThat(all.get(0).getId()).isEqualTo(saved1.getId()),
            () -> assertThat(all.get(1).getId()).isEqualTo(saved2.getId())
        );
    }

    @Test
    void 유저의_모든_게시글을_조회한다() {
        // given
        Post post2 = new Post("title2", "content2", writer);
        Post saved1 = postRepository.save(post);
        Post saved2 = postRepository.save(post2);

        // when
        List<Post> all = postRepository.findAllByWriter(writer);

        // then
        assertAll(
            () -> assertThat(all.size()).isEqualTo(2),
            () -> assertThat(all.get(0).getId()).isEqualTo(saved1.getId()),
            () -> assertThat(all.get(1).getId()).isEqualTo(saved2.getId())
        );
    }

    @Test
    void 게시글을_수정한다() {
        // given
        Post saved = postRepository.save(post);
        Long id = saved.getId();

        String newTitle = "new title";
        String newContent = "new content";
        Post found = postRepository.findById(id).get();

        // when
        found.changeTitle(newTitle);
        found.changeContent(newContent);
        postRepository.save(found);
        Post updated = postRepository.findById(id).get();

        // then
        assertAll(
            () -> assertThat(updated.getTitle()).isEqualTo(newTitle),
            () -> assertThat(updated.getContent()).isEqualTo(newContent),
            () -> assertThat(updated.getLastModifiedAt()).isAfter(updated.getCreatedAt())
        );
    }

    @Test
    void 게시글을_삭제한다() {
        // given
        Post saved = postRepository.save(post);
        Long id = saved.getId();

        // when
        postRepository.deleteById(id);
        Optional<Post> deleted = postRepository.findById(id);

        // then
        assertThat(deleted.isEmpty()).isTrue();
    }
}
