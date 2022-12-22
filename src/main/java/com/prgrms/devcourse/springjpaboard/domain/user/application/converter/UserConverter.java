package com.prgrms.devcourse.springjpaboard.domain.user.application.converter;

import org.springframework.stereotype.Component;

import com.prgrms.devcourse.springjpaboard.domain.user.User;
import com.prgrms.devcourse.springjpaboard.domain.user.dto.UserSaveDto;

@Component
public class UserConverter {

	public User toUser(UserSaveDto userSaveDto) {
		return User.builder()
			.name(userSaveDto.getName())
			.age(userSaveDto.getAge())
			.hobby(userSaveDto.getHobby())
			.build();
	}

}
