package dev.jpaboard.user.application;

import dev.jpaboard.user.dto.request.UserCreateRequest;
import dev.jpaboard.user.domain.User;
import dev.jpaboard.user.dto.request.UserLoginRequest;
import dev.jpaboard.user.dto.response.UserResponse;
import dev.jpaboard.user.dto.request.UserUpdateRequest;
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
        return UserResponse.toDto(savedUser);
    }

  public Long login(UserLoginRequest request) {
    User user = userRepository.findByEmailAndPassword(request.email(), request.password())
            .orElseThrow(UserNotFoundException::new);
    return user.getId();
  }

  @Transactional
  public UserResponse update(UserUpdateRequest request, Long userId) {
    User user = userRepository.findById(userId)
            .orElseThrow(UserNotFoundException::new);
    user.update(request.name(), request.hobby());
    return UserResponse.toDto(user);
  }

  public UserResponse findUser(Long id) {
    User user = userRepository.findById(id)
            .orElseThrow(UserNotFoundException::new);
    return UserResponse.toDto(user);
  }

    @Transactional
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

}
