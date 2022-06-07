package org.programmers.kdtboard.controller;

import javax.validation.Valid;

import org.programmers.kdtboard.controller.response.ApiResponse;
import org.programmers.kdtboard.dto.UserDto;
import org.programmers.kdtboard.dto.UserDto.Response;
import org.programmers.kdtboard.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "/api/v1/users")
@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ApiResponse<Response> create(@RequestBody @Valid UserDto.CreateRequest userCreateDto) {
        var userResponseDto = this.userService.create(userCreateDto.name(), userCreateDto.age(), userCreateDto.hobby());

        return ApiResponse.create(userResponseDto);
    }

    @GetMapping
    public ApiResponse<Page<Response>> findAll(Pageable pageable) {
        return ApiResponse.ok(this.userService.findAll(pageable));
    }
}
