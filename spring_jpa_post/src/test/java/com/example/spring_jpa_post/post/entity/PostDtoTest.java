package com.example.spring_jpa_post.post.entity;

import com.example.spring_jpa_post.post.dto.PostDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import java.util.Set;

import static org.assertj.core.api.Assertions.*;

@Slf4j
@DisplayName("PostDto validation 테스트")
class PostDtoTest {
    private static ValidatorFactory factory;
    private static Validator validator;

    @BeforeAll
    static void init() {
        factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    @DisplayName("PostDto validation 성공")
    void createDtoTest() {
        //given
        PostDto postDto = PostDto.builder().title("title").content("content").build();
        //when
        Set<ConstraintViolation<PostDto>> violation = validator.validate(postDto);
        //then // 성공이면 빈값을 가진다.
        assertThat(violation).isEmpty();
    }

    @DisplayName("PostDto validation 실패")
    @ParameterizedTest
    @CsvSource({",", "title,", ",content", "'',''"})
    void failCreateDtoTest(String title, String content) {
        //given
        PostDto postDto = PostDto.builder().title(title).content(content).build();
        //when
        Set<ConstraintViolation<PostDto>> violation = validator.validate(postDto);
        //then
        assertThat(violation).isNotEmpty();
        violation.forEach(v -> log.info("{}는 {} {}", v.getPropertyPath(), v.getExecutableReturnValue(), v.getMessage()));
    }
}