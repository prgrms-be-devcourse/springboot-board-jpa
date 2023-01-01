package com.prgrms.domain.user;

import static com.prgrms.dto.UserDto.Request;

import com.prgrms.dto.UserDto.Request.Login;
import com.prgrms.dto.UserDto.Response;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.net.URI;
import java.nio.file.AccessDeniedException;
import java.util.Arrays;
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
        throws AccessDeniedException {

        long loggedUserId = Long.parseLong(getCookieUserId(request).getValue());
        Response userInfo = service.findUserById(loggedUserId);

        return ResponseEntity.ok(userInfo);
    }

    @PostMapping()
    public ResponseEntity<Void> userSignUp(@RequestBody Request userDto) {

        service.insertUser(userDto);
        URI loginPageUri = URI.create("api/users/login");

        return ResponseEntity.created(loginPageUri)
            .build();
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestBody Login loginDto,
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
        throws AccessDeniedException {

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

    private Cookie getCookieUserId(HttpServletRequest request) throws AccessDeniedException {

        return Arrays.stream(request.getCookies())
            .filter(i -> "userId".equals(i.getName()))
            .filter(i -> !"".equals(i.getValue()))
            .findAny()
            .orElseThrow(() -> new AccessDeniedException("접근 권한이 없습니다"));
    }

}
