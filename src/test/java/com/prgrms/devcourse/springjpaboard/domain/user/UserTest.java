package com.prgrms.devcourse.springjpaboard.domain.user;

import static com.prgrms.devcourse.springjpaboard.domain.user.UserObjectProvider.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class UserTest {

	@Test
	void 객체_생성_실패_name_null() {

		String name = null;
		Integer age = 25;
		String hobby = "basketball";

		Assertions.assertThrows(IllegalArgumentException.class, () -> createUser(name, age, hobby));

	}

	@Test
	void 객체_생성_실패_name_empty() {

		String name = "";
		Integer age = 25;
		String hobby = "basketball";

		Assertions.assertThrows(IllegalArgumentException.class, () -> createUser(name, age, hobby));

	}

	@Test
	void 객체_생성_실패_name_blank() {

		String name = " ";
		Integer age = 25;
		String hobby = "basketball";

		Assertions.assertThrows(IllegalArgumentException.class, () -> createUser(name, age, hobby));

	}

	@Test
	void 객체_생성_실패_name_length() {

		String name = "안녕하세요저의이름은이건우입니다.이것은20글자가넘습니다.";
		Integer age = 25;
		String hobby = "basketball";

		Assertions.assertThrows(IllegalArgumentException.class, () -> createUser(name, age, hobby));

	}

	@Test
	void 객체_생성_실패_age_range() {

		String name = "이건우";
		Integer age = -5;
		String hobby = "basketball";

		Assertions.assertThrows(IllegalArgumentException.class, () -> createUser(name, age, hobby));

	}

	@Test
	void 객체_생성_실패_age_null() {

		String name = "이건우";
		Integer age = null;
		String hobby = "basketball";

		Assertions.assertThrows(IllegalArgumentException.class, () -> createUser(name, age, hobby));

	}

	@Test
	void 객체_생성_실패_hobby_null() {

		String name = "이건우";
		Integer age = 25;
		String hobby = null;

		Assertions.assertThrows(IllegalArgumentException.class, () -> createUser(name, age, hobby));

	}

	@Test
	void 객체_생성_실패_hobby_empty() {

		String name = "이건우";
		Integer age = 25;
		String hobby = "";

		Assertions.assertThrows(IllegalArgumentException.class, () -> createUser(name, age, hobby));

	}

	@Test
	void 객체_생성_실패_hobby_blank() {

		String name = "이건우";
		Integer age = 25;
		String hobby = " ";

		Assertions.assertThrows(IllegalArgumentException.class, () -> createUser(name, age, hobby));

	}

}