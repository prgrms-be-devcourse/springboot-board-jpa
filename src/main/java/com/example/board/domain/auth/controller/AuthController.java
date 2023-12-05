package com.example.board.domain.auth.controller;

import com.example.board.domain.auth.dto.TokenReissueRequest;
import com.example.board.domain.auth.dto.TokenResponse;
import com.example.board.domain.auth.service.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/reissue")
    public ResponseEntity<TokenResponse> reissue(
        @RequestBody TokenReissueRequest tokenReissueRequest,
        HttpServletResponse response
    ){
        TokenResponse tokenResponse = authService.reissueToken(tokenReissueRequest.refreshToken());
        response.setHeader("Authorization", tokenResponse.accessToken());
        return ResponseEntity.ok(tokenResponse);
    }
}
