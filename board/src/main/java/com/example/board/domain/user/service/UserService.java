package com.example.board.domain.user.service;

import com.example.board.domain.user.User;
import com.example.board.domain.user.dto.UserCreateRequest;
import com.example.board.domain.user.dto.UserResponse;
import com.example.board.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;

  @Transactional
  public UserResponse createUser(UserCreateRequest userCreateRequest) {

    User savedUser = userRepository.save(
        new User(userCreateRequest.name(), userCreateRequest.age(), userCreateRequest.hobby()));
    
    return UserResponse.from(savedUser);
  }
}
