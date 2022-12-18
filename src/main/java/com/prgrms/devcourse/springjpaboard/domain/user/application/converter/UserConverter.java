package com.prgrms.devcourse.springjpaboard.domain.user.application.converter;

import com.prgrms.devcourse.springjpaboard.domain.user.User;
import com.prgrms.devcourse.springjpaboard.domain.user.application.dto.UserSaveDto;

public class UserConverter {

	public static User toUser(UserSaveDto userSaveDto) {
		return User.builder()
			.name(userSaveDto.getName())
			.age(userSaveDto.getAge())
			.hobby(userSaveDto.getHobby())
			.build();
	}

}
