package com.example.springbootboardjpa.repoistory;

import com.example.springbootboardjpa.model.Post;
import com.example.springbootboardjpa.model.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;


@Slf4j
@DataJpaTest
class PostJpaRepositoryTest {

    @Autowired
    PostJpaRepository postRepository;

    @Autowired
    UserJpaRepository userRepository;


    User user;
    Post post;

    @BeforeEach
    public void setUp() {
        userRepository.deleteAll();
        postRepository.deleteAll();

        user = new User("영지", 28);
        post = new Post("초밥 만드는 법", "좋은 사시미가 필요하다.", user);

        userRepository.save(user);
    }

    @Test
    @DisplayName("post를 id로 정상 조회한다.")
    public void findPostById() {
        // When
        var find = postRepository.findById(1L);

        // Then
        assertThat(find.isPresent()).isTrue();
        assertThat(find.get()).isEqualTo(post);
    }

    @Test
    @DisplayName("post를 user name으로 정상 조회할 수 있다.")
    public void findPostByUser() {
        // When
        var posts = postRepository.findByUserName(user.getName());

        // Then
        assertThat(posts.size()).isEqualTo(1L);
        assertThat(posts.get(0)).isEqualTo(post);
    }

    @Test
    @DisplayName("잘못된 user name으로 조회시 빈 list를 반환한다.")
    public void findFailByUser() {
        // When
        var posts = postRepository.findByUserName("오잉");

        // Then
        assertThat(posts.isEmpty()).isTrue();
    }

    @Test
    @DisplayName("post list를 title로 정상 조회한다.")
    public void findPostByTitle() {
        // When
        var posts = postRepository.findByTitle("초밥");

        // Then
        assertThat(posts.size()).isEqualTo(1L);
        assertThat(posts.get(0)).isEqualTo(post);
    }


    @Test
    @DisplayName("post title을 정상 수정한다.")
    public void updatePostTitle() {
        // Given
        var find = postRepository.findById(1L);
        var findPost = find.get();

        // When
        findPost.changeTitle("초밥 달인");
        var posts = postRepository.findByTitle("달인");

        // Then
        assertThat(posts.size()).isEqualTo(1L);
        assertThat(posts.get(0)).isEqualTo(post);
    }

    @Test
    @DisplayName("post content를 정상 수정한다.")
    public void updatePostContent() {
        // Given
        var find = postRepository.findById(1L);
        var findPost = find.get();

        // When
        findPost.changeContent("초밥 달인 구하기~~~");
        var updatePost = postRepository.findById(1L);

        // Then
        assertThat(updatePost.isPresent()).isTrue();
        assertThat(updatePost.get()).isSameAs(post);
    }

    @Test
    @DisplayName("post를 정상 삭제한다.")
    public void deletePost() {
        // Given
        var findPost = postRepository.findById(1L).get();

        // When
        postRepository.delete(findPost);
        var find = postRepository.findById(1L);

        // Then
        assertThat(find.isEmpty()).isTrue();
    }

    @Test
    @DisplayName("BaseEntity 필드가 정상 등록된다.")
    public void BaseEntityTest() {
        // Given // When
        var findPost = postRepository.findById(1L).get();
        findPost.setCreatedBy("관리자");

        // Then
        assertThat(findPost.getCreatedAt()).isNotNull();
        log.info("{}", findPost.getCreatedAt());
        assertThat(findPost.getCreatedBy()).isEqualTo("관리자");
    }

}