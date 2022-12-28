package com.prgrms.devcourse.springjpaboard.domain.user.api;

import static org.springframework.http.MediaType.*;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prgrms.devcourse.springjpaboard.domain.user.application.UserFacade;
import com.prgrms.devcourse.springjpaboard.domain.user.dto.UserCreateRequest;
import com.prgrms.devcourse.springjpaboard.domain.user.dto.UserCreateResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

	private final UserFacade userFacade;

	/**
	 * <pre>
	 *     user 생성
	 * </pre>
	 * @param userCreateRequest - user의 name, age, hobby를 저장한 Dto
	 * @return
	 */
	@PostMapping(consumes = APPLICATION_JSON_VALUE)
	public ResponseEntity<UserCreateResponse> create(@Valid @RequestBody UserCreateRequest userCreateRequest) {
		UserCreateResponse userCreateResponse = userFacade.create(userCreateRequest);

		return ResponseEntity.ok(userCreateResponse);
	}

}
