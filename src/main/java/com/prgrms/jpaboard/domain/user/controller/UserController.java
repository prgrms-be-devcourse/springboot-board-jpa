package com.prgrms.jpaboard.domain.user.controller;

import com.prgrms.jpaboard.domain.user.dto.UserRequestDto;
import com.prgrms.jpaboard.domain.user.service.UserService;
import com.prgrms.jpaboard.global.common.response.ResponseDto;
import com.prgrms.jpaboard.global.common.response.ResultDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
@RestController
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<ResponseDto> createUser(@Valid @RequestBody UserRequestDto userRequestDto) {
        ResultDto result = userService.createUser(userRequestDto);

        ResponseDto responseDto = new ResponseDto(HttpStatus.CREATED.value(), "user created successfully", result);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(responseDto);
    }
}
