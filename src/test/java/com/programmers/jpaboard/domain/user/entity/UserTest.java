package com.programmers.jpaboard.domain.user.entity;

import static org.assertj.core.api.Assertions.*;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.programmers.jpaboard.domain.post.entity.Post;

@DataJpaTest
public class UserTest {

	private Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

	@Test
	@DisplayName("정상적인 사용자 값이 입력되면 생성에 성공한다.")
	void successCreateUser() {
		// given
		User user = new User("권성준", "google@gmail.com", 26, "취미");

		// when
		Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);

		// then
		assertThat(constraintViolations.isEmpty()).isTrue();
	}

	@ParameterizedTest
	@ValueSource(strings = {"", " ", "    "})
	@DisplayName("사용자 이름에 빈 값이나 공백이 입력되면 생성에 실패한다.")
	void failCreateUserByNameIsBlank(String name) {
		// given
		User user = new User(name, "google@gmail.com", 26, "취미");

		// when
		Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);

		// then
		assertThat(constraintViolations.size()).isEqualTo(1);
	}

	@Test
	@DisplayName("사용자 이름에 최대 길이 이상이 입력되면 생성에 실패한다.")
	void failCreateUserByNameIsOverMax() {
		// given
		String overMaxName = "권권권권권권권권권권권권권권권권권권권권권권권권권권권권권권권";

		User user = new User(overMaxName, "google@gmail.com", 26, "취미");

		// when
		Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);

		// then
		assertThat(constraintViolations.size()).isEqualTo(1);
	}

	@ParameterizedTest
	@ValueSource(strings = {"google@gmail.com", "naver@naver.com", "seoultech@seoultech.ac.kr", "daum@hanmail.net"})
	@DisplayName("사용자 이메일에 올바른 형식이 입력되면 생성에 성공한다.")
	void successCreateUserByCorrectEmail(String email) {
		// given
		User user = new User("권성준", email, 26, "취미");

		// when
		Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);

		// then
		assertThat(constraintViolations.isEmpty()).isTrue();
	}

	@ParameterizedTest
	@ValueSource(strings = {"", " ", "google", "@gmail.com", "google@gamil."})
	@DisplayName("사용자 이메일에 잘못된 형식이 입력되면 생성에 실패한다.")
	void failCreateUserByWrongEmail(String email) {
		// given
		User user = new User("권성준", email, 26, "취미");

		// when
		Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);

		// then
		assertThat(constraintViolations.size()).isEqualTo(1);
	}

	@Test
	@DisplayName("사용자 나이에 음수 값이 입력되면 생성에 실패한다.")
	void failCreateUserByAgeIsMinus() {
		// given
		User user = new User("권성준", "google@gmail.com", -1, "취미");

		// when
		Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);

		// then
		assertThat(constraintViolations.size()).isEqualTo(1);
	}

	@Test
	@DisplayName("사용자 취미에 최대 길이 이상이 입력되면 생성에 실패한다.")
	void failCreateUserNameIsOverMax() {
		// given
		User user = new User("권성준", "google@gmail.com", 26, "취미취미취미취미취미취미취미취미취미취미취");

		// when
		Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);

		// then
		assertThat(constraintViolations.size()).isEqualTo(1);
	}

	@Test
	@DisplayName("사용자와 게시글의 연관관계 설정에 성공한다.")
	void successSetUser() {
		// given
		User user = new User("권성준", "google@gmail.com", 26, "취미");
		Post post = new Post("제목", "내용입니다", user);

		// when
		user.addPost(post);

		// then
		assertThat(user.getPosts().contains(post)).isTrue();
		assertThat(post.getUser()).isEqualTo(user);
	}

	@Test
	@DisplayName("사용자 정보 수정에 성공한다")
	void successChangePost() {
		// given
		User user = new User("권성준", "google@gmail.com", 26, "취미");
		String changeEmail = "naver@naver.com";
		String changeHobby = "없음";

		// when
		user.changeUser(changeEmail, changeHobby);

		// then
		assertThat(user)
			.hasFieldOrPropertyWithValue("email", changeEmail)
			.hasFieldOrPropertyWithValue("hobby", changeHobby);
	}
}
