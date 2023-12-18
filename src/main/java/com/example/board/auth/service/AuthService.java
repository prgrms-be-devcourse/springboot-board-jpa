package com.example.board.auth.service;

import com.example.board.auth.domain.TokenInfo;
import com.example.board.auth.dto.request.LoginRequest;
import com.example.board.auth.dto.request.ReissueRequest;
import com.example.board.auth.dto.request.SignUpRequest;
import com.example.board.auth.dto.response.TokenResponse;
import com.example.board.auth.exception.TokenException;
import com.example.board.auth.provider.JwtTokenProvider;
import com.example.board.auth.repository.RefreshTokenRepository;
import com.example.board.converter.UserConverter;
import com.example.board.domain.User;
import com.example.board.dto.response.ResponseStatus;
import com.example.board.exception.CustomException;
import com.example.board.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    public void signup(SignUpRequest requestDto) {
        if (isUserExistsByEmail(requestDto.email()))
            throw new CustomException(ResponseStatus.DUPLICATED_USER_EMAIL);
        if (!requestDto.isPasswordEqualsToPasswordConfirm())
            throw new CustomException(ResponseStatus.PASSWORD_CONFIRM_NOT_MATCHED);

        final User user = UserConverter.toUser(requestDto);
        user.updatePassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public TokenResponse login(LoginRequest requestDto) {
        User user = userRepository.findByEmailAndDeletedAt(requestDto.email(), null)
                .orElseThrow(() -> new CustomException(ResponseStatus.USER_NOT_FOUND));
        // 인증을 위한 AuthenticationToken 생성
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getId(), requestDto.password());
        try {
            // AuthenticationProvider 에 의해 내부적으로 CustomUserDetailService 호출, 인증 성공 시 Authentication 객체 반환
            Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

            final TokenResponse tokenResponse = jwtTokenProvider.generateToken(authentication);
            refreshTokenRepository.save(new TokenInfo(String.valueOf(user.getId()), tokenResponse.refreshToken(), tokenResponse.accessToken()));
            return tokenResponse;
        } catch (BadCredentialsException e) {
            throw new CustomException(ResponseStatus.PASSWORD_NOT_MATCHED);
        }
    }

    public TokenResponse reissue(ReissueRequest requestDto) {
        final TokenInfo tokenInfo = refreshTokenRepository.findByRefreshToken(requestDto.refreshToken())
                .orElseThrow(() -> new TokenException(ResponseStatus.REFRESH_TOKEN_NOT_FOUND));

        // refresh token 유효성 재검증
        final String refreshToken = tokenInfo.getRefreshToken();
        jwtTokenProvider.validateToken(refreshToken);

        // token 재발급
        final TokenResponse tokenResponse = jwtTokenProvider.regenerateToken(tokenInfo.getAccessToken());
        // redis 에 저장된 token 정보 업데이트
        tokenInfo.updateTokens(tokenResponse.refreshToken(), tokenResponse.accessToken());
        refreshTokenRepository.save(tokenInfo);
        return tokenResponse;
    }

    private boolean isUserExistsByEmail(String email) {
        Optional<User> user = userRepository.findByEmailAndDeletedAt(email, null);
        return user.isPresent();
    }
}
