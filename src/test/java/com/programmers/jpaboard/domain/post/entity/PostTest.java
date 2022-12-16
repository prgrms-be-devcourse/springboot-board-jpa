package com.programmers.jpaboard.domain.post.entity;

import static org.assertj.core.api.Assertions.*;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.programmers.jpaboard.domain.user.entity.User;

class PostTest {

	Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

	@Test
	@DisplayName("정상적인 게시글이 입력되면 생성에 성공한다.")
	void successCreatePost() {
		// given
		Post post = new Post("제목", "내용입니다.");

		// when
		Set<ConstraintViolation<Post>> constraintViolations = validator.validate(post);

		// then
		assertThat(constraintViolations.isEmpty()).isTrue();
	}

	@ParameterizedTest
	@ValueSource(strings = {"", " ", "    "})
	@DisplayName("게시글 제목에 빈 값이나 공백이 입력되면 생성에 실패한다.")
	void failCreatePostByTitleIsBlank(String title) {
		// given
		Post post = new Post(title, "내용입니다.");

		// when
		Set<ConstraintViolation<Post>> constraintViolations = validator.validate(post);

		// then
		assertThat(constraintViolations.size()).isEqualTo(1);
	}

	@Test
	@DisplayName("게시물과 사용자의 연관관계 설정에 성공한다.")
	void successSetUser() {
		// given
		Post post = new Post("제목", "내용입니다");
		User user = new User("권성준", "google@gmail.com", 26, "취미");

		// when
		post.setUser(user);

		// then
		assertThat(post.getUser()).isEqualTo(user);
		assertThat(user.getPosts().contains(post)).isTrue();
	}

	@Test
	@DisplayName("게시물 정보 수정에 성공한다")
	void successChangePost() {
		// given
		Post post = new Post("제목", "내용입니다");
		String changeTitle = "변경된 제목";
		String changeContent = "변경된 내용입니다.";

		// when
		post.changePost(changeTitle, changeContent);

		// then
		assertThat(post.getTitle()).isEqualTo(changeTitle);
		assertThat(post.getContent()).isEqualTo(changeContent);
	}
}