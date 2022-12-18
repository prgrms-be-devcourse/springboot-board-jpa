package com.prgrms.devcourse.springjpaboard.domain.user.api;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prgrms.devcourse.springjpaboard.domain.user.service.dto.UserSaveDto;
import com.prgrms.devcourse.springjpaboard.domain.user.service.facade.UserFacade;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

	private final UserFacade userFacade;

	@PostMapping
	public ResponseEntity<Void> create(@Valid @RequestBody UserSaveDto userRequestDto) {
		userFacade.create(userRequestDto);

		return ResponseEntity.ok().build();
	}

}
