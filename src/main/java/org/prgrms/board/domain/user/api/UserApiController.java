package org.prgrms.board.domain.user.api;

import lombok.extern.slf4j.Slf4j;
import org.prgrms.board.domain.user.requset.UserCreateRequest;
import org.prgrms.board.domain.user.requset.UserLoginRequest;
import org.prgrms.board.domain.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserApiController {

    private final UserService userService;

    public UserApiController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/sign-up")
    public ResponseEntity<HttpStatus> signUp(@Valid @RequestBody UserCreateRequest createRequest) {
        userService.save(createRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<HttpStatus> login(@Valid @RequestBody UserLoginRequest loginRequest, HttpSession session) {
        long userId = userService.login(loginRequest);
        session.setAttribute("userId", userId);
        log.info("session id: {}", session.getId());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<HttpStatus> logout(HttpSession session) {
        session.invalidate();
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
