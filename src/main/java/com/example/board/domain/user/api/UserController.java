package com.example.board.domain.user.api;

import static com.example.board.domain.user.dto.UserDto.*;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.board.domain.user.service.UserService;
import com.example.board.global.dto.BaseResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/users")
@RestController
public class UserController {

    private final UserService userService;

    @PostMapping
    public BaseResponse<SingleUserDetailResponse> enrollUser(@RequestBody @Valid CreateUserRequest createUserRequest) {
        return BaseResponse.of(
                HttpStatus.OK,
                "response.enroll-user.title",
                "response.enroll-user.content",
                userService.enroll(createUserRequest));
    }
}
