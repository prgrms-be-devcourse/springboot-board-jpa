package com.juwoong.springbootboardjpa.user.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.juwoong.springbootboardjpa.user.api.model.UserRequest;
import com.juwoong.springbootboardjpa.user.application.UserDto;
import com.juwoong.springbootboardjpa.user.application.UserService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/create")
    public ResponseEntity<UserDto> createUser(@RequestBody UserRequest request) {
        UserDto user = userService.createUser(request.getName(), request.getAge(), request.getHobby());

        return ResponseEntity.ok(user);
    }

    @GetMapping("/search/{id}")
    public ResponseEntity<UserDto> searchById(@PathVariable("id") Long id) {
        UserDto user = userService.searchById(id);

        return ResponseEntity.ok(user);
    }

}
