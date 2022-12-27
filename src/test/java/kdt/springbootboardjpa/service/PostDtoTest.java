package kdt.springbootboardjpa.service;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import kdt.springbootboardjpa.service.dto.PostDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class PostDtoTest {

    ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    Validator validator = validatorFactory.getValidator();


    @Test
    @DisplayName("[성공] Post 생성하기")
    void testCreatePost() {
        PostDto postDto = PostDto.builder()
                .title("title")
                .content("content")
                .createdBy(1L)
                .build();

        Set<ConstraintViolation<PostDto>> violations = validator.validate(postDto);
        List<String> msgs = violations.stream().map(ConstraintViolation::getMessage).toList();

        assertThat(msgs).hasSize(0);
    }

    @Test
    @DisplayName("[실패] Post 생성 시, title/content가 공란/null 일 경우")
    void testCreatePost_null_blank_fail() {
        PostDto postDto = PostDto.builder()
                .title("")
                //.content("content")
                .createdBy(1L)
                .build();

        Set<ConstraintViolation<PostDto>> violations = validator.validate(postDto);
        List<String> msgs = violations.stream().map(ConstraintViolation::getMessage).toList();

        assertThat(msgs).hasSize(2);
        assertThat(msgs).contains("NotBlank");
    }

    @Test
    @DisplayName("[실패] Post 생성 시, createdBy가 양수가 아닐 경우")
    void testCreatePost_negative_fail() {
        PostDto postDto = PostDto.builder()
                .title("title")
                .content("content")
                .createdBy(-1L)
                .build();

        Set<ConstraintViolation<PostDto>> violations = validator.validate(postDto);
        List<String> msgs = violations.stream().map(ConstraintViolation::getMessage).toList();

        assertThat(msgs).hasSize(1);
        assertThat(msgs).contains("Positive");
    }
}
