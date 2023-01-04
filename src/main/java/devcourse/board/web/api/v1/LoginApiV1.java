package devcourse.board.web.api.v1;

import devcourse.board.domain.login.LoginService;
import devcourse.board.domain.login.model.LoginRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import static devcourse.board.web.authentication.AuthenticationUtil.COOKIE_NAME;
import static devcourse.board.web.authentication.AuthenticationUtil.newEmptyCookie;

@RestController
@RequestMapping("/api/v1")
public class LoginApiV1 {

    private final LoginService loginService;

    public LoginApiV1(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(
            @Valid @RequestBody LoginRequest loginRequest,
            HttpServletResponse response
    ) {
        Long memberId = loginService.login(loginRequest);
        response.addCookie(new Cookie(COOKIE_NAME, String.valueOf(memberId)));
        return ResponseEntity.ok()
                .build();
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(
            HttpServletResponse response
    ) {
        response.addCookie(newEmptyCookie(COOKIE_NAME));
        return ResponseEntity.ok()
                .build();
    }
}
