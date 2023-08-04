package com.programmers.springbootboardjpa.domain.user.controller;

import com.programmers.springbootboardjpa.domain.user.dto.UserRequestDto;
import com.programmers.springbootboardjpa.domain.user.dto.UserResponseDto;
import com.programmers.springbootboardjpa.domain.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
@RestController
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserResponseDto> create(@RequestBody @Valid UserRequestDto userRequestDto) {
        UserResponseDto userResponseDto = userService.create(userRequestDto);

        return ResponseEntity.ok().body(userResponseDto);
    }

    @GetMapping
    public ResponseEntity<Page<UserResponseDto>> findAll(@PageableDefault(sort = "id", size = 10, direction = Sort.Direction.DESC) Pageable pageable) {
        Page<UserResponseDto> userResponseDtos = userService.findAll(pageable);

        return ResponseEntity.ok().body(userResponseDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> findById(@PathVariable Long id) {
        UserResponseDto userResponseDto = userService.findById(id);

        return ResponseEntity.ok().body(userResponseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDto> update(@PathVariable Long id, @RequestBody @Valid UserRequestDto userRequestDto) {
        UserResponseDto userResponseDto = userService.update(id, userRequestDto);

        return ResponseEntity.ok().body(userResponseDto);
    }
}
