package com.prgrms.java.controller;

import com.prgrms.java.domain.Email;
import com.prgrms.java.global.model.MySession;
import com.prgrms.java.dto.user.GetUserDetailsResponse;
import com.prgrms.java.dto.user.LoginRequest;
import com.prgrms.java.global.model.SessionDto;
import com.prgrms.java.global.service.SessionHandler;
import com.prgrms.java.service.UserService;
import com.prgrms.java.global.util.CookieUtil;
import com.prgrms.java.global.util.SessionUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.prgrms.java.global.model.MySession.SESSION_ID;

@RestController
@RequestMapping("/users/v2")
public class UserSessionController {

    private final UserService userService;
    private final SessionHandler sessionHandler;

    public UserSessionController(UserService userService, SessionHandler sessionHandler) {
        this.userService = userService;
        this.sessionHandler = sessionHandler;
    }

    @PostMapping("/login")
    public ResponseEntity<Void> loginUser(@Valid @RequestBody LoginRequest loginRequest) throws Exception {
        final long userId = userService.loginUser(loginRequest);
        final SessionDto sessionDto = new SessionDto(loginRequest.email(), userId);
        final String sessionId = sessionHandler.resolve(sessionDto);
        final ResponseCookie cookie = SessionUtil.getNewCookie(sessionId);

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .build();
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logoutUser(HttpServletRequest httpServletRequest) {
        final ResponseCookie cookie = SessionUtil.getDeletedCookie();
        sessionHandler.delete(httpServletRequest);

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .build();
    }

    @GetMapping
    public ResponseEntity<GetUserDetailsResponse> getUserDetails(HttpServletRequest httpServletRequest) throws Exception {
        Email email = authenticate(httpServletRequest);

        GetUserDetailsResponse userDetails = userService.getUserDetails(email);
        return ResponseEntity.ok(userDetails);
    }

    private Email authenticate(HttpServletRequest httpServletRequest) {
        MySession session = sessionHandler.find(httpServletRequest);
        userService.validMember(session.email());
        return session.email();
    }
}
