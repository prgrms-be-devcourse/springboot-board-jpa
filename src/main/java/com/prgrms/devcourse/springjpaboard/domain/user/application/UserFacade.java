package com.prgrms.devcourse.springjpaboard.domain.user.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prgrms.devcourse.springjpaboard.domain.user.User;
import com.prgrms.devcourse.springjpaboard.domain.user.application.converter.UserConverter;
import com.prgrms.devcourse.springjpaboard.domain.user.dto.UserCreateRequest;
import com.prgrms.devcourse.springjpaboard.domain.user.dto.UserCreateResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserFacade {

	private final UserService userService;

	private final UserConverter userConverter;

	@Transactional
	public UserCreateResponse create(UserCreateRequest userCreateRequest) {
		User user = userConverter.toUser(userCreateRequest);
		Long savedId = userService.create(user);
		return userConverter.toUserCreateResponse(savedId);
	}
}
