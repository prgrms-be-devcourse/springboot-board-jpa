package com.example.board.auth.controller;

import com.example.board.auth.dto.request.LoginRequest;
import com.example.board.auth.dto.request.ReissueRequest;
import com.example.board.auth.dto.request.SignUpRequest;
import com.example.board.auth.dto.response.TokenResponse;
import com.example.board.auth.service.AuthService;
import com.example.board.dto.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<Void>> signUp(@RequestBody @Valid SignUpRequest requestDto) {
        authService.signup(requestDto);
        return ResponseEntity.ok(ApiResponse.success());
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<TokenResponse>> login(@RequestBody @Valid LoginRequest requestDto) {
        TokenResponse responseDto = authService.login(requestDto);
        return ResponseEntity.ok(ApiResponse.success(responseDto));
    }

    @PostMapping("/reissue")
    public ResponseEntity<ApiResponse<TokenResponse>> reissue(@RequestBody @Valid ReissueRequest requestDto) {
        TokenResponse responseDto = authService.reissue(requestDto);
        return ResponseEntity.ok(ApiResponse.success(responseDto));
    }
}
