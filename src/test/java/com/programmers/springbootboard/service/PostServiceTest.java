package com.programmers.springbootboard.service;

import com.programmers.springbootboard.dto.PostRequestDto;
import com.programmers.springbootboard.dto.PostResponseDto;
import com.programmers.springbootboard.entity.Post;
import com.programmers.springbootboard.entity.User;
import com.programmers.springbootboard.repository.PostRepository;
import com.programmers.springbootboard.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
class PostServiceTest {

    @Autowired
    PostService postService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    EntityManager em;

    private User user;

    @BeforeEach
    void setup() {
        user = User.builder()
                .name("user")
                .age(10)
                .hobby("springboot")
                .build();
        userRepository.save(user);
    }

    @Test
    @DisplayName("포스트 생성 테스트")
    void testCreatPost() {
        // Given
        PostRequestDto dto = PostRequestDto.builder().title("title").content("content").username(user.getName()).build();

        // When
        PostResponseDto response = postService.createPost(dto);

        // Then
        assertThat(response.getTitle()).isEqualTo("title");
        assertThat(response.getContent()).isEqualTo("content");
        assertThat(response.getUserName()).isEqualTo(user.getName());
    }

    @Test
    @DisplayName("포스트 단 건 조회 테스트")
    public void testReadPost() {
        // Given
        Post post = Post.builder().title("title").content("content").user(user).build();
        Post savedPost = postRepository.save(post);
        em.flush();
        em.clear();

        // When
        PostResponseDto response = postService.readPost(savedPost.getId());

        // Then
        assertThat(response.getTitle()).isEqualTo(savedPost.getTitle());
        assertThat(response.getContent()).isEqualTo(savedPost.getContent());
        assertThat(response.getUserName()).isEqualTo(user.getName());
    }

    @Test
    @DisplayName("포스트 단 건 조회 실패시 예외 테스트")
    public void testReadPostFail() {
        assertThatThrownBy(() -> postService.readPost(1L)).hasMessage("No Post found with id: 1");
    }

    @Test
    @DisplayName("포스트 페이지 조회 테스트")
    public void testReadAllPost() {
        // Given
        for (int i = 0; i < 10; i++) {
            postRepository.save(Post.builder().title("title" + i).content("content" + i).user(user).build());
        }
        em.flush();
        em.clear();

        // When
        Page<PostResponseDto> response = postService.readPostPage(
                PageRequest.of(2, 3, Sort.by(Sort.Direction.ASC, "title")));

        // Then
        assertThat(response.hasContent()).isTrue();
        assertThat(response.getSize()).isEqualTo(3);
        List<PostResponseDto> posts = response.getContent();
        assertThat(posts).extracting("title").containsExactly("title6", "title7", "title8");
    }

    @Test
    @DisplayName("포스트 수정 테스트")
    public void testUpdatePost() {
        // Given
        Post post = Post.builder().title("title").content("content").user(user).build();
        Post savedPost = postRepository.save(post);
        em.flush();
        em.clear();
        PostRequestDto dto = PostRequestDto.builder()
                .id(1L)
                .title("title2")
                .content("content2")
                .username("user")
                .build();
        // When
        postService.updatePost(dto);
        em.flush();
        em.clear();
        PostResponseDto response = postService.readPost(1L);

        // Then
        assertThat(response.getTitle()).isEqualTo("title2");
        assertThat(response.getContent()).isEqualTo("content2");
    }

}