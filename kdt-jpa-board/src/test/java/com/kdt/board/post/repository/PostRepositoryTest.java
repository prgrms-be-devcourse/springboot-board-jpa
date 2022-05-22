package com.kdt.board.post.repository;

import static org.assertj.core.api.Assertions.*;

import com.kdt.board.common.exception.NotFoundException;
import com.kdt.board.post.domain.Post;
import com.kdt.board.user.domain.User;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class PostRepositoryTest {

    @Autowired
    PostRepository postRepository;

    @Autowired
    EntityManager em;

    @Test
    @DisplayName("post 저장 성공")
    void postSaveTest() {
        // Given
        User user = new User("CHOI", 27);
        em.persist(user);
        Post post = new Post("title");
        post.setUser(user);

        // When
        postRepository.save(post);
        em.flush();
        em.clear();

        // Then
        List<Post> posts = postRepository.findAll();
        assertThat(posts.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("저장된 Post 정보 업데이트, dirty Checking")
    void postFindTest() {
        // Given
        User user = new User("CHOI", 27);
        em.persist(user);
        Post post = new Post("title");
        post.setUser(user);
        postRepository.save(post);
        em.flush();
        em.clear();

        // When
        Post findPost = postRepository.findById(post.getId())
            .orElseThrow(NotFoundException::new);
        findPost.changeTitle("newTitle");
        findPost.changeContent("newContent");
        em.flush();
        em.clear();

        // Then
        Post updatePost = postRepository.findById(post.getId())
            .orElseThrow(NotFoundException::new);
        assertThat(updatePost.getTitle()).isEqualTo("newTitle");
    }

    @Test
    @DisplayName("저장된 Post 삭제")
    void postDeleteById() {
        // Given
        User user = new User("CHOI", 27);
        em.persist(user);
        Post post = new Post("title");
        post.setUser(user);
        postRepository.save(post);
        em.flush();
        em.clear();

        // When
        postRepository.deleteById(post.getId());

        // Then
        Optional<Post> findPost = postRepository.findById(post.getId());
        assertThat(findPost.isEmpty()).isTrue();
    }

    @Test
    @DisplayName("User Id 통해서 전체 Post 조회 (페이지 적용)")
    void findPostsByUserIdTest() {
        // Given
        User user = new User("CHOI", 27);
        em.persist(user);
        for (int i = 0; i < 20; i++) {
            postRepository.save(new Post("title"+i, "content"+i, user));
        }
        PageRequest pageRequest = PageRequest.of(0, 10, Sort.by(Direction.DESC, "createdAt"));

        // When
        Page<Post> posts = postRepository.findPostsByUserId(user.getId(), pageRequest);

        // Then
        assertThat(posts.getContent().size()).isEqualTo(10);
        assertThat(posts.getTotalElements()).isEqualTo(20);
        assertThat(posts.getTotalPages()).isEqualTo(2);
    }
}