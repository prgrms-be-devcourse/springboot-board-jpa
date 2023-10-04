package com.programmers.springbootboardjpa.controller;

import com.programmers.springbootboardjpa.dto.user.UserCreateRequest;
import com.programmers.springbootboardjpa.dto.user.UserResponse;
import com.programmers.springbootboardjpa.dto.user.UserUpdateRequest;
import com.programmers.springbootboardjpa.global.ApiResponse;
import com.programmers.springbootboardjpa.global.validate.ValidationSequence;
import com.programmers.springbootboardjpa.service.UserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void save(@Validated(ValidationSequence.class) @RequestBody UserCreateRequest userCreateRequest) {
        userService.save(userCreateRequest);
    }

    @GetMapping
    public ApiResponse<List<UserResponse>> findAll(Pageable pageable) {
        List<UserResponse> userResponses = userService.findAll(pageable);

        return ApiResponse.ok(userResponses);
    }

    @GetMapping("/{id}")
    public ApiResponse<UserResponse> findById(@PathVariable Long id) {
        UserResponse userResponse = userService.findById(id);

        return ApiResponse.ok(userResponse);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        userService.deleteById(id);
    }

    @PatchMapping("/{id}")
    public void updateById(@PathVariable Long id, @Validated(ValidationSequence.class) @RequestBody UserUpdateRequest userUpdateRequest) {
        userService.updateById(id, userUpdateRequest);
    }

}
