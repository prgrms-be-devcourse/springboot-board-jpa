package com.prgrms.jpaboard.domain.post.domain;

import com.prgrms.jpaboard.domain.user.domain.User;
import com.prgrms.jpaboard.domain.user.domain.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class PostRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @BeforeEach
    void setUp() {
        User user = User.builder()
                .name("jerry")
                .age(25)
                .hobby("누워 있기")
                .createdBy("jerry")
                .build();

        userRepository.save(user);
    }

    @Test
    @DisplayName("게시글 생성 테스트")
    void testCreate() {
        User user = userRepository.findAll().get(0);

        Post post = Post.builder()
                .title("제목")
                .content("주요 컨텐츠...")
                .createdBy(user.getId().toString())
                .build();
        post.setUser(user);

        Post savedPost = postRepository.save(post);
        Optional<Post> returnedPost = postRepository.findById(savedPost.getId());

        assertThat(returnedPost).isNotEmpty();
    }

    @Test
    @DisplayName("게시글 생성 by cascade 테스트")
    void testCreateByCascade() {
        User savedUser = userRepository.findAll().get(0);

        Post post = Post.builder()
                .title("제목")
                .content("주요 컨텐츠...")
                .createdBy(savedUser.getId().toString())
                .build();
        post.setUser(savedUser);
        savedUser.addPost(post);

        List<Post> posts = postRepository.findAll();

        assertThat(posts.size()).isEqualTo(1);
    }

    // 수정
    @Test
    @DisplayName("게시글 수정 테스트")
    void testUpdate() {
        User user = userRepository.findAll().get(0);

        Post post = Post.builder()
                .title("제목")
                .content("주요 컨텐츠...")
                .createdBy(user.getId().toString())
                .build();
        post.setUser(user);

        Post savedPost = postRepository.save(post);
        savedPost.updateContent("컨텐츠가 업데이트 되었습니다...");
        Optional<Post> updatedPost = postRepository.findById(savedPost.getId());

        assertThat(updatedPost).isNotEmpty();
        assertThat(updatedPost.get().getContent()).isEqualTo("컨텐츠가 업데이트 되었습니다...");
    }

    @Test
    @DisplayName("게시글 삭제 테스트")
    void testDelete() {
        User user = userRepository.findAll().get(0);

        Post post = Post.builder()
                .title("제목")
                .content("주요 컨텐츠...")
                .createdBy(user.getId().toString())
                .build();
        post.setUser(user);

        Post savedPost = postRepository.save(post);
        postRepository.delete(savedPost);

        Optional<Post> deletedPost = postRepository.findById(savedPost.getId());
        assertThat(deletedPost).isEmpty();
    }

    @Test
    @DisplayName("게시물 페이징 조회 테스트(with user)")
    void testFindAllWithUser() {
        User user = userRepository.findAll().get(0);

        Post post = Post.builder()
                .title("제목")
                .content("주요 컨텐츠...")
                .createdBy(user.getId().toString())
                .build();
        post.setUser(user);

        postRepository.save(post);
        Page<Post> postPagingResult = postRepository.findAllWithUser(PageRequest.of(0, 10));
        assertThat(postPagingResult.getContent().size()).isEqualTo(1);
    }

    @Test
    @DisplayName("게시물 단건 조회 테스트(with user)")
    void testFindByIdWithUser() {
        User user = userRepository.findAll().get(0);

        Post post = Post.builder()
                .title("제목")
                .content("주요 컨텐츠...")
                .createdBy(user.getId().toString())
                .build();
        post.setUser(user);

        Post savedPost = postRepository.save(post);
        Optional<Post> returnedPost = postRepository.findByIdWithUser(savedPost.getId());

        assertThat(returnedPost).isNotEmpty();
    }

}