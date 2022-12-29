package com.prgrms.java.controller;

import com.prgrms.java.domain.Email;
import com.prgrms.java.domain.LoginState;
import com.prgrms.java.dto.user.GetUserDetailsResponse;
import com.prgrms.java.dto.user.PostLoginRequest;
import com.prgrms.java.dto.user.PostUserRequest;
import com.prgrms.java.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<Void> joinUser(@Valid @RequestBody PostUserRequest postUserRequest) {
        userService.joinUser(postUserRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/login")
    public ResponseEntity<Void> loginUser(@Valid @RequestBody PostLoginRequest postLoginRequest) {
        LoginState loginState = userService.loginUser(postLoginRequest);

        return switch (loginState) {
            case SUCCESS -> {
                ResponseCookie cookie = ResponseCookie.from("login-token", postLoginRequest.email()).build();
                yield ResponseEntity.ok()
                        .header(HttpHeaders.SET_COOKIE, cookie.toString()).build();
            }
            case FAIL -> ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        };
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logoutUser() {
        ResponseCookie cookie = ResponseCookie.from("login-token", null)
                .maxAge(0L)
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .build();
    }


    @GetMapping
    public ResponseEntity<GetUserDetailsResponse> getUserDetails(HttpServletRequest httpServletRequest) {
        Email email = Email.fromCookie(httpServletRequest);
        userService.validMember(email);

        GetUserDetailsResponse userDetails = userService.getUserDetails(email);
        return ResponseEntity.ok(userDetails);
    }

}
