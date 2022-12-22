package com.example.springbootboardjpa.model;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class PostTest {

    static final String NO_TITLE = "(제목없음)";

    private Validator validator;

    @BeforeEach
    public void setUp(){
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    @DisplayName("title 빈칸 입력시 (제목없음)으로 저장")
    public void titleBlankSave() {
        var user = new User("영지", 28);
        var post = new Post("   ", "제목없는 게시글", user);

        assertThat(post.getTitle()).isEqualTo(NO_TITLE);
    }

    @Test
    @DisplayName("title은 null이면 안된다.")
    public void titleNullTest() {
        var user = new User("영지", 28);
        var post = new Post(null, "게시글", user);

        Set<ConstraintViolation<Post>> validate = validator.validate(post);
        assertThat(validate.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("title 길이는 50을 넘으면 안된다.")
    public void titleOverLengthTest() {
        var user = new User("영지", 28);
        var post = new Post("012345678901234567890123456789012345678901234567891",
                "게시글", user);

        Set<ConstraintViolation<Post>> validate = validator.validate(post);
        assertThat(validate.size()).isEqualTo(1);
        assertThat(validate.iterator().next().getMessage()).isEqualTo("유효 글자 수를 초과하였습니다.");
    }

    @Test
    @DisplayName("content는 null이면 안된다.")
    public void contentNullTest() {
        var user = new User("영지", 28);
        var post = new Post("제목", null, user);

        Set<ConstraintViolation<Post>> validate = validator.validate(post);
        assertThat(validate.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("User은 null이면 안된다.")
    public void UserNullTest() {
        var post = new Post("제목", "게시글", null);

        Set<ConstraintViolation<Post>> validate = validator.validate(post);
        assertThat(validate.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("User validation을 수행한다.")
    public void UserFieldValidationTest() {
        var user = new User("영지", null);
        var post = new Post("제목", "게시글", user);

        Set<ConstraintViolation<Post>> validate = validator.validate(post);
        assertThat(validate.size()).isEqualTo(1);
    }

}