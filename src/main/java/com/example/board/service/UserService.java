package com.example.board.service;

import com.example.board.converter.UserConverter;
import com.example.board.domain.User;
import com.example.board.dto.request.user.CreateUserRequest;
import com.example.board.dto.request.user.SignInRequest;
import com.example.board.dto.request.user.SignInResponse;
import com.example.board.dto.request.user.UpdateUserRequest;
import com.example.board.dto.response.UserResponse;
import com.example.board.exception.CustomError;
import com.example.board.exception.CustomException;
import com.example.board.jwt.JwtPayload;
import com.example.board.jwt.JwtProvider;
import com.example.board.repository.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class UserService {

    private final AuthService authService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    public SignInResponse signUp(CreateUserRequest requestDto) {
        validateUserName(requestDto.name());

        final User user = UserConverter.toUser(requestDto, passwordEncoder);
        final Long userId = userRepository.save(user).getId();

        return generateTokens(userId);
    }

    @Transactional(readOnly = true)
    public SignInResponse signIn(SignInRequest requestDto) {
        final User user = getAvailableUserByName(requestDto.name());

        if (!passwordEncoder.matches(requestDto.password(), user.getPassword())) {
            throw new CustomException(CustomError.WRONG_PASSWORD);
        }

        return generateTokens(user.getId());
    }

    public UserResponse getMyInfo() {
        final User user = getAvailableUserById(authService.getCurrentUserId());
        return UserConverter.toUserResponse(user);
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
            throw new CustomException(CustomError.DUPLICATED_USER_NAME);
        }
    }

    public User getAvailableUserById(Long id) {
        final User user = userRepository.findById(id).orElseThrow(() -> new CustomException(CustomError.USER_NOT_FOUND));
        if (user.isDeleted()) {
            throw new CustomException(CustomError.ALREADY_DELETED_USER);
        }
        return user;
    }

    private User getAvailableUserByName(String name) {
        final User user = userRepository.findByName(name).orElseThrow(() -> new CustomException(CustomError.USER_NOT_FOUND));
        if (user.isDeleted()) {
            throw new CustomException(CustomError.ALREADY_DELETED_USER);
        }
        return user;
    }

    private SignInResponse generateTokens(Long id) {
        JwtPayload payload = new JwtPayload(id, List.of("USER"));

        final String accessToken = jwtProvider.generateAccessToken(payload);
        final String refreshToken = jwtProvider.generateRefreshToken(payload);
        //TODO: refreshToken 캐시에 저장

        return SignInResponse.of(accessToken, refreshToken);
    }
}
