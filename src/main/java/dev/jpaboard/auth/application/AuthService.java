package dev.jpaboard.auth.application;

import dev.jpaboard.user.domain.Email;
import dev.jpaboard.user.domain.Password;
import dev.jpaboard.user.domain.User;
import dev.jpaboard.user.dto.request.UserCreateRequest;
import dev.jpaboard.user.dto.request.UserLoginRequest;
import dev.jpaboard.user.exception.UserNotFoundException;
import dev.jpaboard.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    @Transactional
    public void signUp(UserCreateRequest request) {
        User user = request.toUser();
        userRepository.save(user);
    }

    public Long login(UserLoginRequest request) {
        User user = findByEmailAndPassword(request.email(), request.password());
        return user.getId();
    }

    private User findByEmailAndPassword(String email, String password) {
        return userRepository.findByEmailAndPassword(new Email(email), new Password(password))
                .orElseThrow(UserNotFoundException::new);
    }

}
