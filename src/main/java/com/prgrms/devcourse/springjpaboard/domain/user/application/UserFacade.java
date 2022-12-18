package com.prgrms.devcourse.springjpaboard.domain.user.application;

import org.springframework.stereotype.Service;

import com.prgrms.devcourse.springjpaboard.domain.user.application.converter.UserConverter;
import com.prgrms.devcourse.springjpaboard.domain.user.application.dto.UserSaveDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserFacade {

	private final UserService userService;

	public void create(UserSaveDto userRequestDto) {

		userService.create(UserConverter.toUser(userRequestDto));

	}
}
