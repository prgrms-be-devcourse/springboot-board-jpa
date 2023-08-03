package com.programmers.board.controller;

import com.programmers.board.constant.AuthConst;
import com.programmers.board.dto.request.LoginRequest;
import com.programmers.board.exception.AuthorizationException;
import com.programmers.board.service.LoginService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static com.programmers.board.constant.AuthConst.LOGIN_USER_ID;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class LoginController {
    private final LoginService loginService;

    @PostMapping("/login")
    public void login(@RequestBody @Valid LoginRequest request,
                      HttpServletRequest httpServletRequest) {
        Long userId = loginService.login(request.getName());
        HttpSession session = httpServletRequest.getSession();
        session.setAttribute(LOGIN_USER_ID, userId);
    }

    @GetMapping("/logout")
    public void logout(HttpServletRequest httpServletRequest) {
        HttpSession session = httpServletRequest.getSession(false);
        if (session == null) {
            throw new AuthorizationException(AuthConst.NO_AUTHORIZATION);
        }
        session.invalidate();
    }
}
