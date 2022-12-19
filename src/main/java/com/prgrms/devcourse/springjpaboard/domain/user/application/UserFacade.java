package com.prgrms.devcourse.springjpaboard.domain.user.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prgrms.devcourse.springjpaboard.domain.user.application.converter.UserConverter;
import com.prgrms.devcourse.springjpaboard.domain.user.application.dto.UserSaveDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserFacade {

	private final UserService userService;

	@Transactional
	public void create(UserSaveDto userRequestDto) {

		userService.create(UserConverter.toUser(userRequestDto));

	}
}
