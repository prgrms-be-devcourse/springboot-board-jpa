package com.prgrms.be.app.domain.dto;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;


class PostDTOTest {

    private static final String TITLE = "title";
    private static final String CONTENT = "content";
    private static Long USER_ID = 1L;

    private Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    @DisplayName("CreateRequest을 정상 범위의 제목, 본문, 유저 id로 설정할 수 있다.")
    void successPostCreateDtoTest() {
        // given
        PostDTO.CreateRequest request = new PostDTO.CreateRequest(TITLE, CONTENT, USER_ID);
        // when
        Set<ConstraintViolation<PostDTO.CreateRequest>> violations = validator.validate(request);
        // then
        Assertions.assertThat(violations).isEmpty();
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "  "})
    @DisplayName("CreateRequest에 빈 제목이나 공백만으로 제목을 설정할 수 없다.")
    void blankTitlePostCreateDtoTest(String title) {
        // given
        PostDTO.CreateRequest request = new PostDTO.CreateRequest(title, CONTENT, USER_ID);
        // when
        Set<ConstraintViolation<PostDTO.CreateRequest>> violations = validator.validate(request);
        // then
        Assertions.assertThat(violations).isNotEmpty();
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "김수한무 거북이와 두루미 삼천갑자 동방삭 치치카포 사리사리센터"})
    @DisplayName("CreateRequest에 1보다 짧거나 20보다 긴 제목을 설정할 수 없다.")
    void shortAndLongTitlePostCreateDtoTest(String title) {
        // given
        PostDTO.CreateRequest request = new PostDTO.CreateRequest(title, CONTENT, USER_ID);
        // when
        Set<ConstraintViolation<PostDTO.CreateRequest>> violations = validator.validate(request);
        // then
        Assertions.assertThat(violations).isNotEmpty();
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "  "})
    @DisplayName("CreateRequest에 빈 본문이나 공백만으로 본문을 설정할 수 없다.")
    void blankContentPostCreateDtoTest(String content) {
        // given
        PostDTO.CreateRequest request = new PostDTO.CreateRequest(TITLE, content, USER_ID);
        // when
        Set<ConstraintViolation<PostDTO.CreateRequest>> violations = validator.validate(request);
        // then
        Assertions.assertThat(violations).isNotEmpty();
    }

    @Test
    @DisplayName("UpdateRequest을 정상 범위의 제목, 본문으로 설정할 수 있다.")
    void successPostUpdateDtoTest() {
        // given
        PostDTO.UpdateRequest request = new PostDTO.UpdateRequest(TITLE, CONTENT);
        // when
        Set<ConstraintViolation<PostDTO.UpdateRequest>> violations = validator.validate(request);
        // then
        Assertions.assertThat(violations).isEmpty();
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "  "})
    @DisplayName("UpdateRequest를 빈 제목이나 공백만으로 제목을 설정할 수 없다.")
    void blankTitlePostUpdateDtoTest(String title) {
        // given
        PostDTO.UpdateRequest request = new PostDTO.UpdateRequest(title, CONTENT);
        // when
        Set<ConstraintViolation<PostDTO.UpdateRequest>> violations = validator.validate(request);
        // then
        Assertions.assertThat(violations).isNotEmpty();
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "김수한무 거북이와 두루미 삼천갑자 동방삭 치치카포 사리사리센터"})
    @DisplayName("UpdateRequest에 1보다 짧거나 20보다 긴 제목을 설정할 수 없다.")
    void shortAndLongTitlePostUpdateDtoTest(String title) {
        // given
        PostDTO.UpdateRequest request = new PostDTO.UpdateRequest(title, CONTENT);
        // when
        Set<ConstraintViolation<PostDTO.UpdateRequest>> violations = validator.validate(request);
        // then
        Assertions.assertThat(violations).isNotEmpty();
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "  "})
    @DisplayName("UpdateRequest에 빈 본문이나 공백만으로 본문을 설정할 수 없다.")
    void blankContentPostUpdateDtoTest(String content) {
        // given
        PostDTO.UpdateRequest request = new PostDTO.UpdateRequest(TITLE, content);
        // when
        Set<ConstraintViolation<PostDTO.UpdateRequest>> violations = validator.validate(request);
        // then
        Assertions.assertThat(violations).isNotEmpty();
    }
}