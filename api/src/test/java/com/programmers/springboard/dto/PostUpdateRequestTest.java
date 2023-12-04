package com.programmers.springboard.dto;

import static org.assertj.core.api.Assertions.*;

import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.programmers.springboard.request.PostUpdateRequest;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

public class PostUpdateRequestTest {


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
		PostUpdateRequest request = new PostUpdateRequest(sb.toString(), "content");

		// When
		Set<ConstraintViolation<PostUpdateRequest>> violations = validator.validate(request);

		// Then
		assertThat(violations).hasSize(1);
		assertThat(violations).extracting(ConstraintViolation::getMessage)
			.containsExactly("제목은 60자 미만입니다");
	}

	@Test
	void 빈_내용_검증_실패() {
		// Given
		PostUpdateRequest request = new PostUpdateRequest("title", "");

		// When
		Set<ConstraintViolation<PostUpdateRequest>> violations = validator.validate(request);

		// Then
		assertThat(violations).hasSize(1);
		assertThat(violations).extracting(ConstraintViolation::getMessage)
			.containsExactly("내용을 입력해주세요");
	}
}
