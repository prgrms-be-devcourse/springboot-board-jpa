package com.dojinyou.devcourse.boardjpa.user.controller;


import com.dojinyou.devcourse.boardjpa.user.controller.dto.UserCreateRequest;
import com.dojinyou.devcourse.boardjpa.user.service.UserService;
import com.dojinyou.devcourse.boardjpa.user.service.dto.UserCreateDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("api/v1/users")
public class UserApiController {

    private final UserService userService;


    public UserApiController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public void createUser(@RequestBody @Valid UserCreateRequest userCreateRequest) {
        userService.createUser(new UserCreateDto.Builder().age(userCreateRequest.getAge())
                                                          .name(userCreateRequest.getName())
                                                          .hobby(userCreateRequest.getHobby())
                                                          .build());
    }

}
