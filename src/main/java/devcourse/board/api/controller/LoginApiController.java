package devcourse.board.api.controller;

import devcourse.board.api.authentication.CookieConst;
import devcourse.board.domain.login.LoginService;
import devcourse.board.domain.login.model.LoginRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
public class LoginApiController {

    private final LoginService loginService;

    public LoginApiController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(
            @Valid @RequestBody LoginRequest loginRequest,
            HttpServletResponse response
    ) {
        Long memberId = loginService.login(loginRequest);
        response.addCookie(new Cookie(CookieConst.MEMBER_ID, String.valueOf(memberId)));
        return ResponseEntity.ok()
                .build();
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(
            HttpServletResponse response
    ) {
        Cookie cookie = new Cookie(CookieConst.MEMBER_ID, null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);

        return ResponseEntity.ok()
                .build();
    }
}
