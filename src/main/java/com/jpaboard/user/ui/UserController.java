package com.jpaboard.user.ui;

import com.jpaboard.user.application.UserService;
import com.jpaboard.user.domain.User;
import com.jpaboard.user.ui.dto.UserResponse;
import org.springframework.stereotype.Controller;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    public long createUser(UserResponse userResponse) {
        User user = UserConverter.convertUser(userResponse);
        long id = userService.saveUser(user);
        return id;
    }

    public UserResponse getUserById(long id) {
        UserResponse userResponse = userService.getUserById(id);
        return userResponse;
    }

}
