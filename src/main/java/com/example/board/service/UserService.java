package com.example.board.service;

import com.example.board.converter.UserConverter;
import com.example.board.domain.User;
import com.example.board.dto.request.user.CreateUserRequest;
import com.example.board.dto.request.user.SignInRequest;
import com.example.board.dto.request.user.SignInResponse;
import com.example.board.dto.request.user.UpdateUserRequest;
import com.example.board.dto.response.UserResponse;
import com.example.board.exception.CustomException;
import com.example.board.exception.ErrorCode;
import com.example.board.jwt.JwtPayload;
import com.example.board.jwt.JwtProvider;
import com.example.board.repository.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    public Long signUp(CreateUserRequest requestDto) {
        validateUserName(requestDto.name());
        final User user = UserConverter.toUser(requestDto, passwordEncoder);
        return userRepository.save(user).getId();
    }

    public SignInResponse signIn(SignInRequest requestDto) {
        final User user = getAvailableUserByName(requestDto.name());
        if (!passwordEncoder.matches(requestDto.password(), user.getPassword())) {
            throw new CustomException(ErrorCode.WRONG_PASSWORD);
        }

        JwtPayload payload = new JwtPayload(user.getId(), List.of("USER"));

        final String accessToken = jwtProvider.generateAccessToken(payload);
        final String refreshToken = jwtProvider.generateRefreshToken(payload);
        //TODO: refreshToken 캐시에 저장

        return SignInResponse.of(accessToken, refreshToken);
    }

    public UserResponse getMyInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof Long) {
            final Long userId = (Long) authentication.getPrincipal();
            final User user = getAvailableUserById(userId);
            return UserConverter.toUserResponse(user);
        } else {
            //TODO
        }
        return null;
    }

    @Transactional(readOnly = true)
    public UserResponse getUser(Long id) {
        final User user = getAvailableUserById(id);
        return UserConverter.toUserResponse(user);
    }

    public void updateUser(Long id, UpdateUserRequest requestDto) {
        final User user = getAvailableUserById(id);
        user.update(requestDto.name(), requestDto.age(), requestDto.hobby());
    }

    public void deleteUser(Long id) {
        final User user = getAvailableUserById(id);
        user.delete();
    }

    private void validateUserName(String name) {
        List<User> user = userRepository.findByNameAndDeletedAt(name, null);
        if (!user.isEmpty()) {
            throw new CustomException(ErrorCode.DUPLICATED_USER_NAME);
        }
    }

    public User getAvailableUserById(Long id) {
        final User user = userRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        if (user.isDeleted()) {
            throw new CustomException(ErrorCode.ALREADY_DELETED_USER);
        }
        return user;
    }

    private User getAvailableUserByName(String name) {
        final User user = userRepository.findByName(name).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        if (user.isDeleted()) {
            throw new CustomException(ErrorCode.ALREADY_DELETED_USER);
        }
        return user;
    }
}
