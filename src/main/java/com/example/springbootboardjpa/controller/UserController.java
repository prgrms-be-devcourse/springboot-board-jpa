package com.example.springbootboardjpa.controller;

import com.example.springbootboardjpa.dto.user.request.UserCreateRequest;
import com.example.springbootboardjpa.dto.user.request.UserUpdateRequest;
import com.example.springbootboardjpa.dto.user.response.UserRepsonse;
import com.example.springbootboardjpa.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserRepsonse createUser(@RequestBody UserCreateRequest createRequest) {
        return userService.createUser(createRequest);
    }

    @GetMapping
    public List<UserRepsonse> findByUserAll() {
        return userService.findAllUsers();
    }

    @GetMapping("/{id}")
    public UserRepsonse findByUserById(@PathVariable Long id) {
        return userService.findUserById(id);
    }

    @PatchMapping("/{id}")
    public UserRepsonse updateUser(@PathVariable Long id, @RequestBody UserUpdateRequest updateRequest) {
        return userService.updateUser(id, updateRequest);
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteByUserById(@PathVariable Long id) {
        userService.deleteCustomerById(id);
    }
}
