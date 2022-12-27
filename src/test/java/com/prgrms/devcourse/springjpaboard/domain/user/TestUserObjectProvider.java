package com.prgrms.devcourse.springjpaboard.domain.user;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TestUserObjectProvider {

	public static User createUser() {
		return User.builder()
			.name("이건우")
			.age(25)
			.hobby("basketball")
			.build();
	}

	public static User createUser(String name, Integer age, String hobby) {
		return User.builder()
			.name(name)
			.age(age)
			.hobby(hobby)
			.build();
	}

}
