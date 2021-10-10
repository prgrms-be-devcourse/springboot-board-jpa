package com.prgrms.board.domain.post;

import com.prgrms.board.domain.user.User;
import com.prgrms.board.domain.user.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class PostRepositoryTest {

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;

    private User user;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .name("이범진")
                .age(27)
                .hobby("축구")
                .build();
    }

    @AfterEach
    void tearDown() {
        postRepository.deleteAll();
    }

    @DisplayName("게시물 생성 테스트")
    @Test
    void createPost() {
        //given
        Post post = Post.builder()
                .title("첫번째 게시글")
                .content("게시글을 작성했습니다.")
                .user(user)
                .build();

        //when
        postRepository.save(post);

        //then
        Post findPost = postRepository.findById(post.getId()).get();

        assertThat(findPost.getTitle()).isEqualTo(post.getTitle());
        assertThat(findPost.getContent()).isEqualTo(post.getContent());
        assertThat(findPost.getCreatedAt()).isEqualTo(post.getCreatedAt());
        assertThat(findPost.getCreatedBy()).isEqualTo(post.getCreatedBy());
    }

    @DisplayName("게시물 조회 테스트")
    @Test
    void selectPost() {
        //given
        Post post = Post.builder()
                .title("첫번째 게시글")
                .content("게시글을 작성했습니다.")
                .user(user)
                .build();

        //when
        postRepository.save(post);

        //then
        List<Post> postList = postRepository.findAll();

        assertThat(postList).hasSize(1);
    }

    @DisplayName("게시물 수정 테스트")
    @Test
    @Transactional
    void updatePost() {
        //given
        Post post = Post.builder()
                .title("첫번째 게시글")
                .content("게시글을 작성했습니다.")
                .user(user)
                .build();
        postRepository.save(post);

        //when
        post.changeTitle("수정된 게시글");
        post.changeContent("게시글을 수정했습니다.");

        //then
        Post updated = postRepository.getById(post.getId());

        assertThat(updated.getTitle()).isEqualTo(post.getTitle());
        assertThat(updated.getContent()).isEqualTo(post.getContent());
    }
}