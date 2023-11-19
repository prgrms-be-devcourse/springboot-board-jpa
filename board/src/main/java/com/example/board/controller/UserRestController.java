package com.example.board.controller;

import com.example.board.dto.PostDto;
import com.example.board.dto.UserDto;
import com.example.board.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserRestController {
    private final UserService service;

    @GetMapping
    public ResponseEntity<List<UserDto.Response>> findAll() {
        List<UserDto.Response> responses = service.findAll();
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto.Response> findById(@PathVariable Long id) {
        UserDto.Response response = service.findById(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<UserDto.Response> save(@RequestBody UserDto.Request request) {
        UserDto.Response response = service.save(request);
        return ResponseEntity.status(201).body(response);
    }

    @PostMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id,
                                       @RequestBody UserDto.Request request) {
        service.update(id, request);
        return ResponseEntity.ok().build();
    }
}
