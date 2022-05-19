package org.prgms.board.domain.user.service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgms.board.domain.BaseTest;
import org.prgms.board.domain.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;

class UserServiceImplTest extends BaseTest {

	@Autowired
	UserService userService;

	@Test
	void 사용자_Save_테스트() {
		// when
		final User savedUser = userService.saveUser("seunghan", 25);
		clearPersistenceContext();

		//then
		final User findUser = userService.findUser(savedUser.getId());

		assertThat(findUser.getName()).isEqualTo("seunghan");
		assertThat(findUser.getAge()).isEqualTo(25);
	}

	@Test
	void 사용자_수정_테스트() {
		// given
		final User savedUser = userService.saveUser("seunghan", 25);

		//when
		userService.updateUser("updatedName", 20, savedUser.getId());
		clearPersistenceContext();

		//then
		final User findUser = userService.findUser(savedUser.getId());

		assertThat(findUser.getName()).isEqualTo("updatedName");
		assertThat(findUser.getAge()).isEqualTo(20);
	}

	@DisplayName("사용자 delete test")
	@Test
	void delete_user_test() {
		// given
		final User savedUser = userService.saveUser("seunghan", 25);

		//when
		userService.deleteUser(savedUser.getId());
		clearPersistenceContext();

		//then
		assertThrows(IllegalStateException.class, () -> userService.findUser(savedUser.getId()));
	}

}
