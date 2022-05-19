package com.prgrms.springboard.post.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import com.prgrms.springboard.user.domain.User;
import com.prgrms.springboard.user.domain.UserRepository;

@DataJpaTest
@Sql("classpath:db/data_user.sql")
class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    void setUp() {
        user = userRepository.findById(1L).get();
    }

    @DisplayName("게시글 객체를 생성한다.")
    @Test
    void post_createPost() {
        // given
        Post post = createPost();

        // when
        // then
        assertThat(post).isNotNull();
        assertThat(post.getCreatedBy()).isEqualTo(user.getName().getName());
    }

    @DisplayName("게시글을 저장한다.")
    @Test
    void save() {
        // given
        Post post = createPost();

        // when
        Post savedPost = postRepository.save(post);

        // then
        assertThat(savedPost.getId()).isNotNull();
    }

    @DisplayName("게시글을 수정한다.")
    @Test
    void update() {
        // given
        Post post = postRepository.save(createPost());

        // when
        post.changePostInformation("수정 제목입니다.", "수정 내용입니다.");

        // then
        assertThat(postRepository.findById(post.getId())).isNotEmpty()
            .get()
            .isEqualTo(post);
    }

    @DisplayName("게시글로부터 회원을 조회할 수 있다.")
    @Test
    void findUserWithPost() {
        // given
        Post savedPost = postRepository.save(createPost());

        // when
        User findUser = savedPost.getUser();

        // then
        assertThat(findUser).isEqualTo(user);
    }

    private Post createPost() {
        return Post.of("제목입니다.", "내용입니다.", user);
    }
}
