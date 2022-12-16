package com.assignment.board.repository;

import com.assignment.board.entity.Hobby;
import com.assignment.board.entity.Post;
import com.assignment.board.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
@Transactional
class PostRepositoryTest {

    @Autowired
    PostRepository repository;

    @AfterEach
    void tearDown() {
        repository.deleteAll();
    }

    @Test
    @DisplayName("게시글 저장")
    void save_test() {
        // Given
        Post post = new Post();
        post.setId(1L);
        post.setTitle("게시글 제목");
        post.setContent("게시글 컨텐츠");

        User user = new User();
        user.setId(1L);
        user.setName("김소현");
        user.setAge(23);
        user.setHobby(Hobby.COOK);
        user.addPost(post);

        // When
        repository.save(post);

        // Then
        Post selectedEntity = repository.findById(1L).get();
        assertThat(selectedEntity.getId()).isEqualTo(post.getId());

        // Logger Test (연관 관계)
        log.info("Post of User's name is {}", selectedEntity.getUser().getName());
    }

    @Test
    @DisplayName("게시글 수정")
    void update_test() {
        // Given
        Post post = new Post();
        post.setId(1L);
        post.setTitle("게시글 제목");
        post.setContent("게시글 컨텐츠");

        User user = new User();
        user.setId(1L);
        user.setName("김소현");
        user.setAge(23);
        user.setHobby(Hobby.COOK);
        user.addPost(post);

        Post savedEntity = repository.save(post);

        // When
        savedEntity.setTitle("수정한 제목");

        // Then
        Post selectedEntity = repository.findById(post.getId()).get();
        assertThat(selectedEntity.getTitle()).isEqualTo(savedEntity.getTitle());
    }

    @Test
    @DisplayName("전체 조회")
    void find_all_test() {
        // Given
        Post post = new Post();
        post.setId(1L);
        post.setTitle("게시글 제목");
        post.setContent("게시글 컨텐츠");

        User user = new User();
        user.setId(1L);
        user.setName("김소현");
        user.setAge(23);
        user.setHobby(Hobby.COOK);
        user.addPost(post);

        repository.save(post);

        // When
        List<Post> all = repository.findAll();

        // Then
        assertThat(all.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("단건 조회")
    void find_one_test() {
        // Given
        Post post = new Post();
        post.setId(1L);
        post.setTitle("게시글 제목");
        post.setContent("게시글 컨텐츠");

        User user = new User();
        user.setId(1L);
        user.setName("김소현");
        user.setAge(23);
        user.setHobby(Hobby.COOK);
        user.addPost(post);

        repository.save(post);

        // When
        Post selectedEntity = repository.findById(post.getId()).get();

        // Then
        assertThat(selectedEntity.getId()).isEqualTo(post.getId());
    }

}