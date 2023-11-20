package com.example.board.controller;

import com.example.board.dto.ApiResult;
import com.example.board.dto.PostDto;
import com.example.board.dto.UserDto;
import com.example.board.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserRestController {
    private final UserService service;

    @GetMapping
    public ApiResult<List<UserDto.Response>> findAll() {
        List<UserDto.Response> responses = service.findAll();
        return ApiResult.successOf(responses);
    }

    @GetMapping("/{id}")
    public ApiResult<UserDto.Response> findById(@PathVariable Long id) {
        UserDto.Response response = service.findById(id);
        return ApiResult.successOf(response);
    }

    @PostMapping
    public ApiResult<UserDto.Response> save(@RequestBody UserDto.Request request) {
        UserDto.Response response = service.save(request);
        return ApiResult.successOf(response);
    }

    @PostMapping("/{id}")
    public ApiResult<Void> update(@PathVariable Long id,
                                       @RequestBody UserDto.Request request) {
        service.update(id, request);
        return ApiResult.successOf();
    }
}
