package com.prgrms.java.controller;

import com.prgrms.java.domain.Email;
import com.prgrms.java.dto.user.GetUserDetailsResponse;
import com.prgrms.java.dto.user.LoginRequest;
import com.prgrms.java.dto.user.CreateUserRequest;
import com.prgrms.java.service.UserService;
import com.prgrms.java.global.util.CookieUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.prgrms.java.global.util.CookieUtil.LOGIN_TOKEN;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<Void> joinUser(@Valid @RequestBody CreateUserRequest createUserRequest) {
        userService.joinUser(createUserRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .build();
    }

    @PostMapping("/login")
    public ResponseEntity<Void> loginUser(@Valid @RequestBody LoginRequest loginRequest) {
        userService.loginUser(loginRequest);
        ResponseCookie cookie = ResponseCookie.from(LOGIN_TOKEN, loginRequest.email())
                .build();
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .build();
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logoutUser() {
        ResponseCookie cookie = ResponseCookie.from(LOGIN_TOKEN, null)
                .maxAge(0L)
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .build();
    }


    @GetMapping
    public ResponseEntity<GetUserDetailsResponse> getUserDetails(HttpServletRequest httpServletRequest) {
        final String identifier = CookieUtil.getValue(httpServletRequest, LOGIN_TOKEN);
        final Email email = new Email(identifier);

        GetUserDetailsResponse userDetails = userService.getUserDetails(email);
        return ResponseEntity.ok(userDetails);
    }

}
