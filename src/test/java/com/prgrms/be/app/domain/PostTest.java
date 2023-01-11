package com.prgrms.be.app.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertThrows;

class PostTest {
    private static final String TITLE = "제목";
    private static final String CONTENT = "본문";
    private static final User USER = new User("이름", 25, "취미");

    private static Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    @DisplayName("게시글을 정상 범위의 제목과 본문으로 설정할 수 있다.")
    void successPostCreateTest() {
        // given
        Post post = new Post(TITLE, CONTENT, USER);

        // when
        Set<ConstraintViolation<Post>> violations = validator.validate(post);

        // then
        Assertions.assertThat(violations).isEmpty();
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "  "})
    @DisplayName("게시글의 제목에 빈 값이나 공백만 넣고 게시글을 생성할 수 없다.")
    void blankTitlePostCreateTest(String title) {
        // then
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new Post(title, CONTENT, USER));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "  "})
    @DisplayName("게시글의 본문에 빈 값이나 공백만 넣고 게시글을 생성할 수 없다.")
    void blankContentPostCreateTest(String content) {
        // given
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new Post(TITLE, content, USER));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "  "})
    @DisplayName("게시글 수정 시 빈 값이나 공백이 제목에 들어가면 예외가 발생한다.")
    void blankTitlePostUpdateTest(String title) {
        // given
        Post post = new Post(TITLE, CONTENT, USER);

        Set<ConstraintViolation<Post>> violations = validator.validate(post);
        Assertions.assertThat(violations).isEmpty();

        // when, then
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> post.changePost(title, CONTENT));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "  "})
    @DisplayName("게시글 수정 시 빈 값이나 공백이 본문에 들어가면 예외가 발생한다.")
    void blankContentPostUpdateTest(String content) {
        // given
        Post post = new Post(TITLE, CONTENT, USER);

        Set<ConstraintViolation<Post>> violations = validator.validate(post);
        Assertions.assertThat(violations).isEmpty();

        // when, then
        assertThrows(IllegalArgumentException.class, () -> post.changePost(TITLE, content));
    }
}