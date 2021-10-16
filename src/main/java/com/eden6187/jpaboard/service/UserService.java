package com.eden6187.jpaboard.service;

import com.eden6187.jpaboard.controller.UserController.AddUserRequestDto;
import com.eden6187.jpaboard.service.converter.UserConverter;
import com.eden6187.jpaboard.exception.DuplicatedUserNameException;
import com.eden6187.jpaboard.model.User;
import com.eden6187.jpaboard.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

  private final UserRepository userRepository;
  private final UserConverter userConverter;

  @Transactional
  public Long addUser(AddUserRequestDto userDto) throws DuplicatedUserNameException {
    User user = userConverter.convertUser(userDto);
    try {
      User savedUser = userRepository.save(user);
      return savedUser.getId();
    } catch (DataIntegrityViolationException exception) {
      // unique 제약 조건은 비즈니스 로직상의 문제가 아니고 DB 자체에서 제약 조건을 만족하지 못해서 발생한 예외이다.
      // 따라서, 복구를 하는 것을 강제하기 보다는 오류가 발생했음을 가능한 신속하게 알려주어야 한다.
      // 다만, 이때 왜 이러한 오류가 발생했는지를 알려주어야 하기 때문에
      // 예외를 좀 더 구체적인 의미를 가지는 런타임 예외로 변환한다.
      throw new DuplicatedUserNameException(exception);
    }
  }

}
