package dev.jpaboard.user.application;

import dev.jpaboard.user.domain.User;
import dev.jpaboard.user.dto.request.UserCreateRequest;
import dev.jpaboard.user.dto.request.UserLoginRequest;
import dev.jpaboard.user.dto.request.UserUpdateRequest;
import dev.jpaboard.user.dto.response.UserResponse;
import dev.jpaboard.user.exception.UserNotFoundException;
import dev.jpaboard.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public UserResponse signUp(UserCreateRequest request) {
        User user = UserCreateRequest.toUser(request);
        User savedUser = userRepository.save(user);
        return UserResponse.from(savedUser);
    }

    public Long login(UserLoginRequest request) {
        User user = findByEmailAndPassword(request.email(), request.password());
        return user.getId();
    }

    @Transactional
    public UserResponse update(UserUpdateRequest request, Long userId) {
        User user = findUserById(userId);
        user.update(request.name(), request.hobby());
        return UserResponse.from(user);
    }

    public UserResponse findUser(Long userId) {
        User user = findUserById(userId);
        return UserResponse.from(user);
    }

    @Transactional
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    private User findUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);
    }

    private User findByEmailAndPassword(String email, String password) {
        return userRepository.findByEmailAndPassword(email, password)
                .orElseThrow(UserNotFoundException::new);
    }

}
