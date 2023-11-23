package com.programmers.springboard.dto;

import static org.assertj.core.api.Assertions.*;

import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import com.programmers.springboard.request.PostCreateRequest;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PostCreateRequestTest {

	private static ValidatorFactory factory;
	private static Validator validator;

	@BeforeAll
	public static void init() {
		factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}


	@Test
	void 제목_길이_60자_이상_검증_실패() {
		StringBuilder sb = new StringBuilder();
		sb.append("가".repeat(70));

		// Given
		PostCreateRequest request = new PostCreateRequest(sb.toString(), "content",1L);

		// When
		Set<ConstraintViolation<PostCreateRequest>> violations = validator.validate(request);

		// Then
		assertThat(violations).hasSize(1);
		assertThat(violations).extracting(ConstraintViolation::getMessage)
			.containsExactly("제목은 60자 미만입니다");
	}

	@Test
	void 빈_내용_검증_실패() {
		// Given
		PostCreateRequest request = new PostCreateRequest("title", null, 1L);

		// When
		Set<ConstraintViolation<PostCreateRequest>> violations = validator.validate(request);

		// Then
		assertThat(violations).hasSize(1);
		assertThat(violations).extracting(ConstraintViolation::getMessage)
			.containsExactly("내용을 입력해주세요");
	}

	@Test
	void 빈_제목_검증_실패() {
		// Given
		PostCreateRequest request = new PostCreateRequest("", "test", 1L);

		// When
		Set<ConstraintViolation<PostCreateRequest>> violations = validator.validate(request);

		// Then
		assertThat(violations).hasSize(1);
		assertThat(violations).extracting(ConstraintViolation::getMessage)
			.containsExactly("제목을 입력해주세요");
	}

	@Test
	void 멤버아이디_null_검증_실패() {
		// Given
		PostCreateRequest request = new PostCreateRequest("test", "test", null);

		// When
		Set<ConstraintViolation<PostCreateRequest>> violations = validator.validate(request);

		// Then
		assertThat(violations).hasSize(1);
		assertThat(violations).extracting(ConstraintViolation::getMessage)
			.containsExactly("멤버 아이디를 입력해주세요");
	}
}
