package com.example.board.auth.service;

import com.example.board.auth.domain.RefreshToken;
import com.example.board.auth.dto.request.LoginRequest;
import com.example.board.auth.dto.request.SignUpRequest;
import com.example.board.auth.dto.response.JwtToken;
import com.example.board.auth.dto.response.LoginResponse;
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

    public LoginResponse login(LoginRequest requestDto) {
        User user = userRepository.findByEmailAndDeletedAt(requestDto.email(), null)
                .orElseThrow(() -> new CustomException(ResponseStatus.USER_NOT_FOUND));
        // 인증을 위한 AuthenticationToken 생성
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getId(), requestDto.password());
        try {
            // AuthenticationProvider 에 의해 내부적으로 CustomUserDetailService 호출, 인증 성공 시 Authentication 객체 반환
            Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

            final JwtToken jwtToken = jwtTokenProvider.generateToken(authentication);
            refreshTokenRepository.save(new RefreshToken(String.valueOf(user.getId()), jwtToken.getRefreshToken(), jwtToken.getAccessToken()));

            return new LoginResponse(jwtToken);
        } catch (BadCredentialsException e) {
            throw new CustomException(ResponseStatus.PASSWORD_NOT_MATCHED);
        }
    }

    public void saveTokenInfo(Long userId, String refreshToken, String accessToken) {
        refreshTokenRepository.save(new RefreshToken(String.valueOf(userId), refreshToken, accessToken));
    }

    public void removeRefreshToken(String accessToken) {
        final RefreshToken refreshToken = refreshTokenRepository.findByAccessToken(accessToken).orElseThrow(() -> new TokenException(ResponseStatus.REFRESH_TOKEN_NOT_FOUND));
        refreshTokenRepository.delete(refreshToken);
    }

    private boolean isUserExistsByEmail(String email) {
        Optional<User> user = userRepository.findByEmailAndDeletedAt(email, null);
        return !user.isEmpty();
    }

}
