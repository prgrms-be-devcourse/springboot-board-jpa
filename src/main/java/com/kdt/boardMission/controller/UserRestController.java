package com.kdt.boardMission.controller;

import com.kdt.boardMission.ApiResponse;
import com.kdt.boardMission.dto.UserDto;
import com.kdt.boardMission.service.UserService;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class UserRestController {

    private final UserService userService;

    @PostMapping("/user")
    public ResponseEntity<Long> enrollUser(@RequestBody UserDto userDto) {
        long userId = userService.saveUser(userDto);
        return ResponseEntity.ok(userId);
    }

    @GetMapping("/user")
    public ResponseEntity<Page<UserDto>> getUserByTitle(@RequestParam("name") Optional<String> name, Pageable pageable) {
        if (name.isEmpty()) {
            Page<UserDto> userDtoPage = userService.findAll(pageable);
            return ResponseEntity.ok(userDtoPage);
        }
        Page<UserDto> userDtoPageByName = userService.findUserByName(name.get(), pageable);
        return ResponseEntity.ok(userDtoPageByName);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<UserDto> getOne(@PathVariable("userId") Long userId) throws NotFoundException {
        UserDto userDto = userService.findUserById(userId);
        return ResponseEntity.ok(userDto);
    }

    @PutMapping("/user/edit")
    public ResponseEntity<UserDto> editHobby(@RequestBody UserDto userDto) throws NotFoundException {
        userService.updateUser(userDto);
        return ResponseEntity.ok(userDto);
    }
}
