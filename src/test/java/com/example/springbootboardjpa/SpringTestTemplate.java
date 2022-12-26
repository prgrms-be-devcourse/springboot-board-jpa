package com.example.springbootboardjpa;

import com.example.springbootboardjpa.model.Post;
import com.example.springbootboardjpa.model.User;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SpringTestTemplate {

    private Validator validator;

    @BeforeEach
    public void setUp(){
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    /**
     * bean validation test
     */
    @Test
    @DisplayName("title 길이는 50을 넘으면 안된다.")
    public void titleOverLengthTest() {
        // Given
        var user = new User("영지", 28);
        var post = new Post("012345678901234567890123456789012345678901234567891",
                "게시글", user);

        // When
        Set<ConstraintViolation<Post>> validate = validator.validate(post);

        // Then
        assertThat(validate.size()).isEqualTo(1);
        assertThat(validate.iterator().next().getMessage()).isEqualTo("유효 글자 수를 초과하였습니다.");
    }



}
