package org.prgms.board.domain.user.service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import javax.transaction.Transactional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgms.board.domain.user.domain.User;
import org.prgms.board.domain.user.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Transactional
class UserServiceImplTest {

	@Autowired
	UserService userService;

	@DisplayName("사용자 save test")
	@Test
	void save_user_test() {
		// given
		String name = "seunghan";
		int age = 25;

		//when
		final User savedUser = userService.saveUser(name, age);

		//then
		assertThat(savedUser.getName()).isEqualTo(name);
		assertThat(savedUser.getAge()).isEqualTo(age);
	}

	@DisplayName("사용자 update test")
	@Test
	void update_user_test() {
		// given
		final User savedUser = userService.saveUser("seunghan", 25);

		//when
		String updateName = "afterUpdateName";
		int updateAge = 100;

		userService.updateUser(updateName, updateAge, savedUser.getId());

		//then
		final User findUser = userService.findUser(savedUser.getId());

		assertThat(findUser.getName()).isEqualTo(updateName);
		assertThat(findUser.getAge()).isEqualTo(updateAge);
	}

	@DisplayName("사용자 delete test")
	@Test
	void delete_user_test() {
		// given
		final User savedUser = userService.saveUser("seunghan", 25);

		//when
		userService.deleteUser(savedUser.getId());

		//then
		assertThrows(IllegalStateException.class, () -> userService.findUser(savedUser.getId()));

	}

}
