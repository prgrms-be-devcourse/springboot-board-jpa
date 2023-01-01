package com.prgrms.domain.user;

import static com.prgrms.dto.UserDto.UserCreateRequest;

import com.prgrms.dto.UserDto.LoginRequest;
import com.prgrms.dto.UserDto.Response;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.net.URI;
import java.util.Arrays;
import javax.naming.AuthenticationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/users")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping("/me")
    public ResponseEntity<Response> getLoggedUserInfo(HttpServletRequest request)
        throws AuthenticationException {

        long loggedUserId = Long.parseLong(getCookieUserId(request).getValue());
        Response userInfo = service.findUserById(loggedUserId);

        return ResponseEntity.ok(userInfo);
    }

    @PostMapping()
    public ResponseEntity<Void> userSignUp(@RequestBody UserCreateRequest userDto) {

        service.insertUser(userDto);
        URI loginPageUri = URI.create("api/users/login");

        return ResponseEntity.created(loginPageUri)
            .build();
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestBody LoginRequest loginDto,
        HttpServletResponse servletResponse) {

        Response response = service.login(loginDto);
        setCookieUserId(response.getUserId(), servletResponse);

        URI myPageUri = URI.create("api/users/me");

        return ResponseEntity.ok()
            .location(myPageUri)
            .build();
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletResponse response,HttpServletRequest request)
        throws AuthenticationException {

        Cookie cookieUserId = getCookieUserId(request);
        cookieUserId.setValue("");
        response.addCookie(cookieUserId);

        return ResponseEntity.ok()
            .build();
    }

    private void setCookieUserId(Long userId, HttpServletResponse response) {

        Cookie userCookie = new Cookie("userId", String.valueOf(userId));
        response.addCookie(userCookie);
    }

    private Cookie getCookieUserId(HttpServletRequest request)
        throws AuthenticationException {

        return Arrays.stream(request.getCookies())
            .filter(cookie -> "userId".equals(cookie.getName()))
            .filter(cookie -> !"".equals(cookie.getValue()))
            .findAny()
            .orElseThrow(() -> new AuthenticationException("접근 권한이 없습니다."));
    }

}
