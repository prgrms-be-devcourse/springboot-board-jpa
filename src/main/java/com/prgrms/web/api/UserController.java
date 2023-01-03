package com.prgrms.web.api;

import static com.prgrms.dto.UserDto.UserCreateRequest;
import static com.prgrms.web.CookieManager.createCookie;
import static com.prgrms.web.CookieManager.getCookie;
import static com.prgrms.web.CookieManager.removeCookie;

import com.prgrms.domain.user.UserService;
import com.prgrms.dto.UserDto.LoginRequest;
import com.prgrms.dto.UserDto.Response;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.net.URI;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/users")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping("/me")
    public ResponseEntity<Response> getLoggedUserInfo(HttpServletRequest request) {

        long loggedUserId = Long.parseLong(getCookie(request).getValue());
        Response userInfo = service.findUserById(loggedUserId);

        return ResponseEntity.ok(userInfo);
    }

    @PostMapping()
    public ResponseEntity<Void> userSignUp(@RequestBody UserCreateRequest userDto) {

        service.insertUser(userDto);
        URI loginPageUri = URI.create("api/v1/users/login");

        return ResponseEntity.created(loginPageUri)
            .build();
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestBody LoginRequest loginDto,
        HttpServletResponse servletResponse) {

        Response response = service.login(loginDto);
        createCookie(response.getUserId(), servletResponse);

        URI myPageUri = URI.create("api/v1/users/me");

        return ResponseEntity.ok()
            .location(myPageUri)
            .build();
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletResponse response, HttpServletRequest request) {

        removeCookie(request, response);

        return ResponseEntity.ok()
            .build();
    }


}
