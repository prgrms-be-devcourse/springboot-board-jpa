package dev.jpaboard.user.api;

import dev.jpaboard.user.application.UserService;
import dev.jpaboard.user.dto.request.UserUpdateRequest;
import dev.jpaboard.user.dto.response.UserInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public UserInfoResponse findUser(@SessionAttribute(name = "userId") Long userId) {
        return userService.findUser(userId);
    }

    @GetMapping("/me")
    public UserResponse findUser(@SessionAttribute(name = "userId") Long userId) {
        return userService.findUser(userId);
    }

    @DeleteMapping("/me")
    public void deleteUser(@SessionAttribute(name = "userId") Long userId) {
        userService.delete(userId);
    }

}
