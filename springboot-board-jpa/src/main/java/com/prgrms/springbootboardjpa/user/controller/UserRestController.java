package com.prgrms.springbootboardjpa.user.controller;

import com.prgrms.springbootboardjpa.user.dto.UserDto;
import com.prgrms.springbootboardjpa.user.dto.UserResponse;
import com.prgrms.springbootboardjpa.user.service.UserService;
import com.prgrms.springbootboardjpa.exception.response.ErrorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class UserRestController {
    private final String PATH = "/api/v1/users";

    @Autowired
    UserService userService;

    @PostMapping(path = PATH)
    public UserResponse save(@RequestBody @Valid UserDto userDto){
        return userService.save(userDto);
    }

    @GetMapping(path = PATH)
    public List<UserResponse> allUsers(Pageable pageable){
        return userService.findAll(pageable).stream().toList();
    }

}
