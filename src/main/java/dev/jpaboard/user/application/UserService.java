package dev.jpaboard.user.application;

import dev.jpaboard.user.domain.User;
import dev.jpaboard.user.dto.request.UserUpdateRequest;
import dev.jpaboard.user.dto.response.UserInfoResponse;
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
    public void update(UserUpdateRequest request, Long userId) {
        User user = findUserById(userId);
        user.update(request.name(), request.hobby());
    }

    public UserInfoResponse findUser(Long userId) {
        User user = findUserById(userId);
        return UserInfoResponse.from(user);
    }

    @Transactional
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    private User findUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);
    }

}
