package com.prgrms.boardjpa.users.service;

import com.prgrms.boardjpa.entity.User;
import com.prgrms.boardjpa.users.repository.UserRepository;
import com.prgrms.boardjpa.utils.converter.UserConverter;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class UserService {

  private final UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public User getUser(Long userId) {
    return userRepository
        .findById(userId)
        .orElseThrow(
            () -> new NoSuchElementException("존재하지 않는 user userId: " + userId)
        );
  }

}
