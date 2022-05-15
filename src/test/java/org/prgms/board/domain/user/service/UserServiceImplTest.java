package org.prgms.board.domain.user.service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import javax.transaction.Transactional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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

		final UserDto.Save saveDto = new UserDto.Save(name, age);

		//when
		final UserDto.Response response = userService.saveUser(saveDto);

		//then
		assertThat(response.getName()).isEqualTo(name);
		assertThat(response.getAge()).isEqualTo(age);
	}

	@DisplayName("사용자 update test")
	@Test
	void update_user_test() {
		// given
		final UserDto.Save saveDto = new UserDto.Save("seunghan", 25);

		final UserDto.Response savedUserResponse = userService.saveUser(saveDto);

		//when
		String updateName = "afterUpdateName";
		int updateAge = 100;

		final UserDto.Update updateDto = new UserDto.Update(updateName, updateAge, savedUserResponse.getId());
		userService.updateUser(updateDto);

		//then
		final UserDto.Response response = userService.getUser(savedUserResponse.getId());

		assertThat(response.getName()).isEqualTo(updateName);
		assertThat(response.getAge()).isEqualTo(updateAge);
	}

	@DisplayName("사용자 delete test")
	@Test
	void delete_user_test() {
		// given
		final UserDto.Save saveDto = new UserDto.Save("seunghan", 25);

		final UserDto.Response savedUserResponse = userService.saveUser(saveDto);

		//when
		userService.deleteUser(savedUserResponse.getId());

		//then
		assertThrows(IllegalStateException.class, () -> userService.getUser(savedUserResponse.getId()));

	}

}
