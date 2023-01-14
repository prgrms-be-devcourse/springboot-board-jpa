package com.prgrms.web.api.v2;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prgrms.domain.user.UserService;
import com.prgrms.dto.UserDto.LoginRequest;
import com.prgrms.dto.UserDto.Response;
import com.prgrms.dto.UserDto.UserCreateRequest;
import com.prgrms.web.auth.SessionManager;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("api/v2/users")
public class UserApiVer2 {

    private final UserService service;
    private final SessionManager sessionManager;

    public UserApiVer2(UserService service, SessionManager sessionManager) {
        this.service = service;
        this.sessionManager = sessionManager;
    }

    @GetMapping("/me")
    public ResponseEntity<Response> getLoggedUserInfo(HttpServletRequest request) {

        long loggedUserId = sessionManager.getSession(request);
        Response userInfo = service.findUserById(loggedUserId);

        return ResponseEntity.ok(userInfo);
    }

    @PostMapping()
    public ResponseEntity<Void> userSignUp(@RequestBody UserCreateRequest userDto) {

        service.insertUser(userDto);
        URI loginPageUri = URI.create("api/v2/users/login");

        return ResponseEntity.created(loginPageUri)
            .build();
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestBody LoginRequest loginDto,
        HttpServletResponse response,
        HttpServletRequest request) {

        Response loggedUser = service.login(loginDto);
        sessionManager.createSession(loggedUser.getUserId(), request,
            response);

        return ResponseEntity.ok()
            .build();
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request, HttpServletResponse response) {

        sessionManager.removeSession(request, response);

        return ResponseEntity.ok()
            .build();
    }

}
