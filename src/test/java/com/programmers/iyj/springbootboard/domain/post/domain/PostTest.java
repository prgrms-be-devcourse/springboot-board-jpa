package com.programmers.iyj.springbootboard.domain.post.domain;

import com.programmers.iyj.springbootboard.domain.user.domain.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class PostTest {
    public static Validator validator;

    @BeforeAll
    public static void setupValidatorInstance() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    @DisplayName("Title, Content, User should not be null")
    public void whenTitleContentUserAreNull() {
        // Given
        Post post = Post.builder()
                .title(null)
                .content(null)
                .user(null)
                .build();

        // When
        Set<ConstraintViolation<Post>> violations = validator.validate(post);

        // Then
        assertThat(violations.size()).isEqualTo(3);
    }

    @Test
    @DisplayName("Title should not be empty")
    public void whenTitleIsEmpty() {
        // Given
        Post post = Post.builder()
                .title("")
                .content("hihi")
                .user(
                        User.builder()
                                .name("john")
                                .build()
                )
                .build();

        // When
        Set<ConstraintViolation<Post>> violations = validator.validate(post);

        // Then
        assertThat(violations.size()).isEqualTo(1);
    }
}