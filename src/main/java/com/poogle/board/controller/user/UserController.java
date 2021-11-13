package com.poogle.board.controller.user;

import com.poogle.board.controller.ApiResult;
import com.poogle.board.converter.Converter;
import com.poogle.board.error.NotFoundException;
import com.poogle.board.model.user.User;
import com.poogle.board.service.user.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import static com.poogle.board.controller.ApiResult.OK;

@RestController
@RequestMapping("api/users")
public class UserController {

    private final UserService userService;
    private final Converter converter;

    public UserController(UserService userService, Converter converter) {
        this.userService = userService;
        this.converter = converter;
    }

    @PostMapping
    public ApiResult<UserResponse> join(@RequestBody UserRequest request) {
        return OK(converter.convertUserDto(userService.join(request.newUser())));
    }

    @GetMapping
    public ApiResult<Page<UserResponse>> list(Pageable pageable) {
        return OK(
                userService.findUsers(pageable)
                        .map(converter::convertUserDto)
        );
    }

    @GetMapping("/{id}")
    public ApiResult<UserResponse> getUser(@PathVariable Long id) {
        return OK(userService.findUser(id)
                .map(converter::convertUserDto)
                .orElseThrow(() -> new NotFoundException(User.class, id)));
    }

    @PutMapping("/{id}")
    public ApiResult<UserResponse> update(
            @PathVariable Long id,
            @RequestBody UserRequest request) {
        return OK(converter.convertUserDto(userService.modify(id, request)));
    }

}
