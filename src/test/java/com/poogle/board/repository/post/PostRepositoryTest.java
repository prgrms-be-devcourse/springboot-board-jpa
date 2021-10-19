package com.poogle.board.repository.post;

import com.poogle.board.model.post.Post;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@Slf4j
@DataJpaTest
class PostRepositoryTest {

    @Autowired
    PostRepository postRepository;

    @Test
    @DisplayName("포스트 저장 후 불러오기")
    public void save_post_test() {
        //given
        String title = "title";
        String content = "content";
        String writer = "tester";

        postRepository.save(Post.of(title, content, writer));

        //when
        List<Post> postList = postRepository.findAll();

        //then
        Post post = postList.get(0);
        assertAll(
                () -> assertThat(post.getTitle()).isEqualTo(title),
                () -> assertThat(post.getContent()).isEqualTo(content)
        );
    }

    @Test
    @DisplayName("BaseTimeEntity 테스트")
    public void base_time_entity_test() {
        //given
        LocalDateTime now = LocalDateTime.of(2021, 10, 19, 0, 0, 0);
        postRepository.save(Post.of("title", "content", "tester"));

        //when
        List<Post> postList = postRepository.findAll();

        //then
        Post post = postList.get(0);
        log.info("[*] createdAt: {}", post.getCreatedAt());
        log.info("[*] modifiedAt: {}", post.getModifiedAt());
        assertAll(
                () -> assertThat(post.getCreatedAt()).isAfter(now),
                () -> assertThat(post.getModifiedAt()).isAfter(now)
        );
    }

    @Test
    @DisplayName("BaseEntity 테스트")
    public void base_entity_test() {
        //given
        LocalDateTime now = LocalDateTime.of(2021, 10, 19, 0, 0, 0);
        postRepository.save(Post.of("title", "content", "tester"));

        //when
        List<Post> postList = postRepository.findAll();

        //then
        Post post = postList.get(0);
        log.info("[*] createdAt: {}", post.getCreatedAt());
        log.info("[*] modifiedAt: {}", post.getModifiedAt());
        log.info("[*] createdBy: {}", post.getCreatedBy());
        log.info("[*] modifiedBy: {}", post.getModifiedBy());
        assertAll(
                () -> assertThat(post.getCreatedAt()).isAfter(now),
                () -> assertThat(post.getModifiedAt()).isAfter(now),
                () -> assertThat(post.getCreatedBy()).isNotEmpty(),
                () -> assertThat(post.getModifiedBy()).isNotEmpty()
        );
    }
}
