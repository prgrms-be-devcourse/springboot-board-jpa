package org.jpa.kdtboard.user.controller;

import lombok.RequiredArgsConstructor;
import org.jpa.kdtboard.common.Response;
import org.jpa.kdtboard.post.dto.PostDto;
import org.jpa.kdtboard.user.dto.UserDto;
import org.jpa.kdtboard.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.NoSuchAlgorithmException;

/**
 * Created by yunyun on 2021/10/14.
 */

@RestController
@RequiredArgsConstructor
public class UserController {

    @ExceptionHandler
    private Response<String> exceptionHandle(Exception exception) {
        return Response.fail(HttpStatus.INTERNAL_SERVER_ERROR.value(), exception.getMessage());
    }

    private final UserService userService;

    @PostMapping("/users")
    public Response<Long> save(@RequestBody UserDto userDto) {
        return Response.ok(userService.save(userDto));
    }
}
