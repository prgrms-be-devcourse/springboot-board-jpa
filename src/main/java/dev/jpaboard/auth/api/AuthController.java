package dev.jpaboard.auth.api;

import dev.jpaboard.auth.application.AuthService;
import dev.jpaboard.common.exception.AuthorizedException;
import dev.jpaboard.user.dto.request.UserCreateRequest;
import dev.jpaboard.user.dto.request.UserLoginRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/sign-up")
    @ResponseStatus(OK)
    public void signUp(@RequestBody UserCreateRequest request) {
        authService.signUp(request);
    }

    @PostMapping("/login")
    @ResponseStatus(OK)
    public Long login(@RequestBody UserLoginRequest request, HttpServletRequest httpRequest) {
        httpRequest.getSession().invalidate();
        HttpSession session = httpRequest.getSession(true);

        Long userId = authService.login(request);
        session.setAttribute("userId", userId);
        session.setMaxInactiveInterval(3600);
        return userId;
    }

    @PostMapping("/logout")
    @ResponseStatus(NO_CONTENT)
    public void logout(HttpServletRequest httpRequest) {
        HttpSession session = httpRequest.getSession(false);
        if (Objects.isNull(session)) {
            throw new AuthorizedException();
        }
        session.invalidate();
    }

}
