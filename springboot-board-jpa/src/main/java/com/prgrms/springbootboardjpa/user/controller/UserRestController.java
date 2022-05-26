package com.prgrms.springbootboardjpa.user.controller;

import com.prgrms.springbootboardjpa.user.dto.UserDto;
import com.prgrms.springbootboardjpa.user.dto.UserResponse;
import com.prgrms.springbootboardjpa.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/users")
public class UserRestController {

    private final UserService userService;

    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public UserResponse save(@RequestBody @Valid UserDto userDto){
        return userService.save(userDto);
    }

    @GetMapping
    public List<UserResponse> allUsers(Pageable pageable){
        return userService.findAll(pageable).stream().toList();
    }

}
