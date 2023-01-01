package com.programmers.jpaboard.domain.user.repository;

import static org.assertj.core.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.programmers.jpaboard.domain.user.entity.User;

@DataJpaTest
class UserRepositoryTest {

	@Autowired
	UserRepository userRepository;

	@AfterEach
	void tearDown() {
		userRepository.deleteAll();
	}

	@Test
	@DisplayName("정상적인 사용자 저장과 아이디로의 조회에 성공한다.")
	void saveAndFindByIdTest() {
		// given
		User user = new User("권성준", "google@gmail.com", 100, "인터넷 서핑");

		// when
		User savedUser = userRepository.save(user);
		User findUser = userRepository.findById(savedUser.getId()).get();

		// then
		assertThat(savedUser)
			.hasFieldOrPropertyWithValue("name", user.getName())
			.hasFieldOrPropertyWithValue("email", user.getEmail())
			.hasFieldOrPropertyWithValue("age", user.getAge())
			.hasFieldOrPropertyWithValue("hobby", user.getHobby());
		assertThat(findUser).isEqualTo(savedUser);
	}

	@Test
	@DisplayName("사용자 수정에 성공한다.")
	void updateUser() {
		// given
		User user = new User("권성준", "google@gmail.com", 100, "인터넷 서핑");
		userRepository.save(user);
		String changeEmail = "naver@naver.com";
		String changeHobby = "취미 없음";

		// when
		user.changeUser(changeEmail, changeHobby);
		User findUser = userRepository.findById(user.getId()).get();

		// then
		assertThat(findUser).isEqualTo(user);
	}

	@Test
	@DisplayName("사용자의 아이디로 삭제에 성공한다.")
	void deleteTest() {
		// given
		User user = new User("권성준", "google@gmail.com", 100, "인터넷 서핑");
		userRepository.save(user);

		// when
		userRepository.deleteById(user.getId());
		Optional<User> optionalUser = userRepository.findById(user.getId());

		// then
		assertThat(optionalUser.isEmpty()).isTrue();
	}
}