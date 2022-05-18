package org.programmers.springbootboardjpa.controller;

import org.programmers.springbootboardjpa.controller.dto.UserDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    @GetMapping("/users/{id}")
    public List<UserDto> users(@PathVariable("id") Long userId) {
        return null;
    }


}