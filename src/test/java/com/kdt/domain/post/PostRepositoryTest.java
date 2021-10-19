package com.kdt.domain.post;

import static org.assertj.core.api.Assertions.assertThat;

import com.kdt.domain.user.User;
import com.kdt.domain.user.UserRepository;
import java.util.List;
import java.util.stream.IntStream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
class PostRepositoryTest {

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;

    User user;

    @BeforeEach
    void setUp() {
        user = userRepository.save(User.builder()
                .name("user")
                .age(1995)
                .build());
    }

    @AfterEach
    void tearDown() {
        postRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("게시물 등록")
    void savePost() {
        //given
        Post post = givenPost(1L);

        //when
        postRepository.save(post);

        //then
        Post savePost = postRepository.findById(1L).get();
        assertThat(savePost).isNotNull();
    }

    @Test
    @DisplayName("게시물 수정")
    void updatePost() {
        //given
        Post post = postRepository.save(givenPost(1L));

        //when
        String title = "자바 스터디 모집";
        String content = "자바 스터디원을 모집힙니다.";
        post.changeTitle(title);
        post.changeContent(content);

        //then
        Post updatedPost = postRepository.findById(post.getId()).get();

        assertThat(updatedPost.getTitle()).isEqualTo(title);
        assertThat(updatedPost.getContent()).isEqualTo(content);
        assertThat(updatedPost.getCreatedAt()).isNotEqualTo(updatedPost.getUpdatedAt());
    }

    @Test
    @DisplayName("게시물 단건 조회")
    void findPostById() {
        //given
        Post savedPost = postRepository.save(givenPost(1L));

        //when
        Post findPost = postRepository.findById(savedPost.getId()).get();

        //then
        assertThat(findPost)
                .usingRecursiveComparison()
                .isEqualTo(savedPost);
    }

    @Test
    @DisplayName("게시물 페이징 조회")
    void findAllByPage() {
        //given
        PageRequest pageRequest = PageRequest.of(0, 10);

        IntStream.range(0, 30)
                .forEach(i -> postRepository.save(givenPost(1L)));

        //when
        Page<Post> all = postRepository.findAll(pageRequest);

        assertThat(all.getTotalElements()).isEqualTo(30L);
        assertThat(all.getTotalPages()).isEqualTo(3);

    }

    private Post givenPost(Long id) {
        String title = "스프링 스터디 모집";
        String content = "스프링을 주제로 주 1회 발표하며 공부하실 스터디원을 모집합니다.";
        return Post.builder()
                .id(id)
                .title(title)
                .content(content)
                .user(user)
                .build();
    }

}