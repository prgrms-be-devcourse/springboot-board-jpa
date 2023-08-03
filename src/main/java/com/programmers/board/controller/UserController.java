package com.programmers.board.controller;

import com.programmers.board.constant.SessionConst;
import com.programmers.board.controller.response.PageResult;
import com.programmers.board.controller.response.Result;
import com.programmers.board.dto.UserDto;
import com.programmers.board.dto.request.UserCreateRequest;
import com.programmers.board.dto.request.UserUpdateRequest;
import com.programmers.board.dto.request.UsersGetRequest;
import com.programmers.board.exception.AuthenticationException;
import com.programmers.board.service.UserService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/users")
    public PageResult<UserDto> findUsers(@ModelAttribute @Valid UsersGetRequest request) {
        Page<UserDto> users = userService.findUsers(
                request.getPage(),
                request.getSize());
        return new PageResult<>(users);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/users")
    public Result<Long> createUser(@RequestBody @Valid UserCreateRequest request) {
        Long userId = userService.createUser(
                request.getName(),
                request.getAge(),
                request.getHobby());
        return new Result<>(userId);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/users/{userId}")
    public Result<UserDto> findUser(@PathVariable("userId") Long userId) {
        UserDto user = userService.findUser(userId);
        return new Result<>(user);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping("/users/{userId}")
    public void updateUser(@PathVariable("userId") Long userId,
                           @SessionAttribute(name = SessionConst.LOGIN_USER_ID, required = false) Long loginUserId,
                           @RequestBody @Valid UserUpdateRequest request) {
        checkLogin(loginUserId);
        userService.updateUser(
                loginUserId,
                userId,
                request.getName(),
                request.getAge(),
                request.getHobby());
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/users/{userId}")
    public void deleteUser(@PathVariable("userId") Long userId,
                           @SessionAttribute(name = SessionConst.LOGIN_USER_ID, required = false) Long loginUserId) {
        checkLogin(loginUserId);
        userService.deleteUser(userId, loginUserId);
    }

    private void checkLogin(Long loginUserId) {
        if (loginUserId == null) {
            throw new AuthenticationException(SessionConst.NO_LOGIN);
        }
    }
}
