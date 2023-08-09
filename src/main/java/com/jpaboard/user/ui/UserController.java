package com.jpaboard.user.ui;

import com.jpaboard.user.application.UserService;
import com.jpaboard.user.domain.User;
import com.jpaboard.user.ui.dto.UserDto;
import org.springframework.stereotype.Controller;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    public long createUser(UserDto.Response response) {
        User user = UserConverter.convertUser(response);
        long id = userService.saveUser(user);
        return id;
    }

    public UserDto.Response getUserById(long id) {
        UserDto.Response response = userService.getUserById(id);
        return response;
    }

}
