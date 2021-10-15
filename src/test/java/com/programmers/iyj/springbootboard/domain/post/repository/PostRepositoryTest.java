package com.programmers.iyj.springbootboard.domain.post.repository;

import com.programmers.iyj.springbootboard.domain.post.domain.Post;
import com.programmers.iyj.springbootboard.domain.user.domain.Hobby;
import com.programmers.iyj.springbootboard.domain.user.domain.User;
import com.programmers.iyj.springbootboard.domain.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.validation.ConstraintViolationException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class PostRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Test
    @DisplayName("DB should save entity")
    public void whenInsert() {
        // Given
        User user = User.builder()
                .name("john")
                .build();
        Post post = Post.builder()
                .title("title1")
                .content("hihi")
                .user(user)
                .build();

        userRepository.save(user);

        // When
        postRepository.save(post);

        // Then
        Post entity = postRepository.findById(1L).get();
        assertThat(entity.getTitle()).isEqualTo("title1");
    }

    @Test
    @DisplayName("If the user is null, it cannot be inserted.")
    public void whenInsertNullUser() {
        // Given
        Post post = Post.builder()
                .title("title")
                .content("empty")
                .user(null)
                .build();

        // When, Then
        assertThrows(ConstraintViolationException.class, () ->
                postRepository.save(post)
        );
    }
}