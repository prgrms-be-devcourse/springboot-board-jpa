package devcourse.board.api;

import devcourse.board.api.model.CookieName;
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

@RestController
@RequestMapping("/login")
public class LoginApiController {

    private final LoginService loginService;

    public LoginApiController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping
    public ResponseEntity<Void> login(
            @Valid @RequestBody LoginRequest loginRequest,
            HttpServletResponse response
    ) {
        Long memberId = loginService.login(loginRequest);
        response.addCookie(new Cookie(CookieName.MEMBER_ID.getCookieName(), String.valueOf(memberId)));
        return ResponseEntity.ok()
                .build();
    }
}
