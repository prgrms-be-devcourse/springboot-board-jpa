package dev.jpaboard.user.api;

import dev.jpaboard.common.exception.AuthorizedException;
import dev.jpaboard.user.application.UserService;
import dev.jpaboard.user.dto.request.UserCreateRequest;
import dev.jpaboard.user.dto.request.UserLoginRequest;
import dev.jpaboard.user.dto.request.UserUpdateRequest;
import dev.jpaboard.user.dto.response.UserResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/sign-up")
    @ResponseStatus(OK)
    public void signUp(@RequestBody UserCreateRequest request) {
        userService.signUp(request);
    }

    @PostMapping("/login")
    @ResponseStatus(OK)
    public Long login(@RequestBody UserLoginRequest request, HttpServletRequest httpRequest) {
        httpRequest.getSession().invalidate();
        HttpSession session = httpRequest.getSession(true);

        Long userId = userService.login(request);
        session.setAttribute("userId", userId);
        session.setMaxInactiveInterval(3600);
        return userId;
    }

    @GetMapping("/logout")
    @ResponseStatus(NO_CONTENT)
    public void logout(HttpServletRequest httpRequest) {
        HttpSession session = httpRequest.getSession(false);
        if (Objects.isNull(session)) {
            throw new AuthorizedException();
        }
        session.invalidate();
    }

    @PatchMapping("/update")
    @ResponseStatus(NO_CONTENT)
    public void update(@RequestBody UserUpdateRequest request,
                       @SessionAttribute(name = "userId") Long userId) {
        userService.update(request, userId);
    }

    @GetMapping("/me")
    public UserResponse findUser(@SessionAttribute(name = "userId") Long userId) {
        return userService.findUser(userId);
    }

    @DeleteMapping("/me")
    public void delete(@SessionAttribute(name = "userId") Long userId) {
        userService.delete(userId);
    }

}
