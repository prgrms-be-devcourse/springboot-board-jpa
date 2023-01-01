package com.prgrms.domain.user;

import static com.prgrms.dto.UserDto.Request;

import com.prgrms.dto.UserDto.Request.Login;
import com.prgrms.dto.UserDto.Response;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.net.URI;
import java.nio.file.AccessDeniedException;
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

        long loggedUserId = Long.parseLong(service.getCookieUserId(request).getValue());
        Response userInfo = service.findUserById(loggedUserId);

        return ResponseEntity.ok(userInfo);
    }

    @PostMapping("/new")
    public ResponseEntity<Void> userSignUp(@RequestBody Request userDto) {

        service.insertUser(userDto);
        URI loginPageUri = URI.create("api/users/login");

        return ResponseEntity.created(loginPageUri)
            .build();
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestBody Login loginDto,
        HttpServletResponse servletResponse) {

        Response response = service.login(loginDto, servletResponse);

        URI myPageUri = URI.create("api/users/me" + response.getUserId());

        return ResponseEntity.created(myPageUri)
            .build();
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletResponse response,HttpServletRequest request)
        throws AccessDeniedException {

        service.logout(request, response);

        return ResponseEntity.ok().build();
    }

}
