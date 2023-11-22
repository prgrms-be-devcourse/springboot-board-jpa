package com.programmers.springboard.dto;

import static org.assertj.core.api.Assertions.*;

import java.util.Set;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import com.programmers.springboard.request.CreatePostRequest;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

@SpringBootTest
@Import(ValidatorConfig.class)
public class CreatePostRequestTest {

	@Autowired
	private Validator validator;

	@Test
	void 제목_길이_60자_이상_검증_실패() {
		StringBuilder sb = new StringBuilder();
		sb.append("가".repeat(70));

		// Given
		CreatePostRequest request = new CreatePostRequest(sb.toString(), "content",1L);

		// When
		Set<ConstraintViolation<CreatePostRequest>> violations = validator.validate(request);

		// Then
		assertThat(violations).hasSize(1);
		assertThat(violations).extracting(ConstraintViolation::getMessage)
			.containsExactly("제목은 60자 미만입니다");
	}

	@Test
	void 빈_내용_검증_실패() {
		// Given
		CreatePostRequest request = new CreatePostRequest("title", null, 1L);

		// When
		Set<ConstraintViolation<CreatePostRequest>> violations = validator.validate(request);

		// Then
		assertThat(violations).hasSize(1);
		assertThat(violations).extracting(ConstraintViolation::getMessage)
			.containsExactly("내용을 입력해주세요");
	}

}
