package com.prgrms.devcourse.springjpaboard.domain.user.service.facade;

import org.springframework.stereotype.Service;

import com.prgrms.devcourse.springjpaboard.domain.user.service.UserService;
import com.prgrms.devcourse.springjpaboard.domain.user.service.converter.UserConverter;
import com.prgrms.devcourse.springjpaboard.domain.user.service.dto.UserSaveDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserFacade {

	private final UserService userService;

	public void create(UserSaveDto userRequestDto) {

		userService.create(UserConverter.toUser(userRequestDto));

	}
}
