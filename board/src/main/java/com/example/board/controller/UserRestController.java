package com.example.board.controller;

import com.example.board.dto.ApiResult;
import com.example.board.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.board.dto.UserDto.Request;
import static com.example.board.dto.UserDto.Response;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserRestController {
    private final UserService service;

    @GetMapping
    public ApiResult<List<Response>> findAll() {
        List<Response> responses = service.findAll();
        return ApiResult.successOf(responses);
    }

    @GetMapping("/{userId}")
    public ApiResult<Response> findById(@PathVariable Long userId) {
        Response response = service.findById(userId);
        return ApiResult.successOf(response);
    }

    @PostMapping
    public ApiResult<Response> save(@Validated @RequestBody Request request) {
        Response response = service.save(request);
        return ApiResult.successOf(response);
    }

    @PostMapping("/{userId}")
    public ApiResult<Void> update(@PathVariable Long userId,
                                  @Validated @RequestBody Request request) {
        service.update(userId, request);
        return ApiResult.successOf();
    }
}
