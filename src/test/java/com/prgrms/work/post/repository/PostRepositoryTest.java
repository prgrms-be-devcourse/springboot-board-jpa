package com.prgrms.work.post.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import com.prgrms.work.post.domain.Post;
import com.prgrms.work.user.domain.User;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class PostRepositoryTest {

    private Long VALID_ID;
    private static final Long INVALID_ID = Long.MAX_VALUE;
    private static final String TITLE = "제목";
    private static final String CONTENT = "내용";
    private static final int VALID_SIZE = 10;

    @Autowired
    private PostRepository postRepository;

    @AfterEach
    void cleanUp() {
        postRepository.deleteAll();
    }

    @Test
    @DisplayName("게시글을 저장합니다.")
    void save_test() {
        // GIVEN
        Post post = createPost();

        // WHEN
        Post savedPost = postRepository.save(post);
        VALID_ID = savedPost.getId();

        // THEN
        assertThat(savedPost.getId()).isEqualTo(VALID_ID);
        assertThat(savedPost.getTitle()).isEqualTo(TITLE);
        assertThat(savedPost.getContent()).isEqualTo(CONTENT);
    }

    @Test
    @DisplayName("게시글을 전체조회 합니다.")
    void findAll_test() {
        // GIVEN
        List<Post> posts = createPosts();

        // WHEN
        List<Post> savedPosts = postRepository.saveAll(posts);

        // THEN
        assertThat(savedPosts).hasSize(VALID_SIZE);
    }

    @Test
    @DisplayName("아이디에 해당하는 게시글이 있다면 옵셔널로 래핑하여 반환합니다.")
    void findById_pass() {
        // GIVEN
        Post savedPost = postRepository.save(createPost());
        VALID_ID = savedPost.getId();

        // WHEN
        Optional<Post> post = postRepository.findById(VALID_ID);

        // THEN
        assertThat(post.isPresent()).isTrue();
        assertThat(post.get().getTitle()).isEqualTo(TITLE);
    }

    @Test
    @DisplayName("게시글이 없다면 비어있는 옵셔널을 반환합니다.")
    void findById_fail() {
        // GIVEN
        postRepository.deleteAll();

        // WHEN
        Optional<Post> post = postRepository.findById(INVALID_ID);

        // THEN
        assertThat(post.isPresent()).isFalse();
    }

    @Test
    @DisplayName("넘어온 아이디에 해당하는 게시글을 삭제합니다.")
    void deleteById_test() {
        // GIVEN
        Post savedPost = postRepository.save(createPost());
        VALID_ID = savedPost.getId();

        // WHEN
        postRepository.deleteById(VALID_ID);
        Optional<Post> post = postRepository.findById(VALID_ID);

        // THEN
        assertThat(post.isPresent()).isFalse();
    }

    private static Post createPost() {
        User user = createUser();
        return Post.create(user.getName(), TITLE, CONTENT, user);
    }

    private static User createUser() {
        return User.create("김형욱", 27, "산책");
    }

    private static List<Post> createPosts() {
        List<Post> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            User user = createUser();
            list.add(Post.create(user.getName(), TITLE + i, CONTENT + i, user));
        }

        return list;
    }

}