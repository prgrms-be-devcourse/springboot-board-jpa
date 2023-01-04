package devcourse.board.web.api.v2;

import devcourse.board.domain.login.LoginService;
import devcourse.board.domain.login.model.LoginRequest;
import devcourse.board.web.authentication.SessionManager;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/v2")
public class LoginApiV2 {

    private final SessionManager sessionManager;

    private final LoginService loginService;

    public LoginApiV2(SessionManager sessionManager, LoginService loginService) {
        this.sessionManager = sessionManager;
        this.loginService = loginService;
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(
            @Valid @RequestBody LoginRequest loginRequest,
            HttpServletResponse response
    ) {
        Long memberId = loginService.login(loginRequest);
        sessionManager.registerNewSession(response, memberId);
        return ResponseEntity.ok()
                .build();
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(
            HttpServletRequest request
    ) {
        sessionManager.expireSession(request);
        return ResponseEntity.ok()
                .build();
    }
}
