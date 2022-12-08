package org.programmers.kdtboard.controller;

import javax.validation.Valid;

import org.programmers.kdtboard.controller.response.ApiResponse;
import org.programmers.kdtboard.dto.UserDto;
import org.programmers.kdtboard.dto.UserDto.Response;
import org.programmers.kdtboard.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequestMapping(value = "/api/v1/users")
@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ResponseStatus(HttpStatus.CREATED)
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
