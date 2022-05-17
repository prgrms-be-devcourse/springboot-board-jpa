package org.prgms.board.domain.user.domain;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgms.board.domain.post.domain.Post;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserTest {

	@DisplayName("User 생성 성공 테스트")
	@Test
	void create_user_success() {
		// given
		String name = "seung han";
		int age = 25;

		// when
		final User user = User.create(name, age);

		// then
		assertThat(user.getName()).isEqualTo(name);
		assertThat(user.getAge()).isEqualTo(age);
	}

	@DisplayName("name이 1글자 이상, 30자 이하가 아닐 때 User 생성 실패 테스트")
	@Test
	void create_user_fail() {
		// name test
		assertThrows(IllegalArgumentException.class, () -> User.create(null, 25));
		assertThrows(IllegalArgumentException.class, () -> User.create("", 25));
		assertThrows(IllegalArgumentException.class, () -> User.create("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", 25)); // 31자
	}

	@DisplayName("age가 양수가 아닐 때 User 생성 실패 테스트")
	@Test
	void create_user_fail_with_invalid_age() {
		assertThrows(IllegalArgumentException.class, () -> User.create("seung han", 0));
		assertThrows(IllegalArgumentException.class, () -> User.create("seung han", -1));
	}

	@DisplayName("User update 성공 테스트")
	@Test
	void update_user_success() {
		// given
		String name = "seung han";
		int age = 25;

		final User user = User.create(name, age);

		// when
		String updateName = "updated han";
		int updateAge = 40;

		user.updateUser(updateName, updateAge);

		//then
		assertThat(user.getName()).isEqualTo(updateName);
		assertThat(user.getAge()).isEqualTo(updateAge);
	}

	@DisplayName("name이 1글자 이상, 30자 이하가 아닐 때 User update 실패 테스트")
	@Test
	void update_user_fail_with_invalid_name() {
		String name = "seung han";
		int age = 25;

		final User user = User.create(name, age);

		assertThrows(IllegalArgumentException.class, () -> user.updateUser(null, 25));
		assertThrows(IllegalArgumentException.class, () -> user.updateUser("", 25));

	}

	@DisplayName("age가 양수가 아닐 때 User update 실패 테스트")
	@Test
	void update_user_fail() {
		String name = "seung han";
		int age = 25;

		final User user = User.create(name, age);

		assertThrows(IllegalArgumentException.class, () -> user.updateUser("seung han", 0));
		assertThrows(IllegalArgumentException.class, () -> user.updateUser("seung han", -1));
	}

}
