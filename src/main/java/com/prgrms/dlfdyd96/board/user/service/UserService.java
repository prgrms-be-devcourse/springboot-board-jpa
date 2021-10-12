package com.prgrms.dlfdyd96.board.user.service;

import com.prgrms.dlfdyd96.board.domain.User;
import com.prgrms.dlfdyd96.board.user.converter.UserConverter;
import com.prgrms.dlfdyd96.board.user.dto.UserDto;
import com.prgrms.dlfdyd96.board.user.repository.UserRepository;
import javassist.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService {

  private final UserRepository userRepository;
  private final UserConverter userConverter;

  public UserService(UserRepository userRepository,
      UserConverter userConverter) {
    this.userRepository = userRepository;
    this.userConverter = userConverter;
  }

  public Long save(UserDto userDto) {
    User user = userConverter.convertUser(userDto);
    return userRepository.save(user).getId();
  }

  public Long update(Long id, UserDto userDto) throws NotFoundException {
    User user = userRepository.findById(id)
        .orElseThrow(() -> new NotFoundException("사용자를 찾을 수 없습니다."));
    user.update(userDto); // full update
    return user.getId();
  }

  public Page<UserDto> findUsers(Pageable pageable) {
    return userRepository.findAll(pageable).map(userConverter::convertUserDto);
  }

  public UserDto findOne(Long id) throws NotFoundException {
    return userRepository.findById(id)
        .map(userConverter::convertUserDto)
        .orElseThrow(() -> new NotFoundException("사용자를 찾을 수 없습니다."));
  }

  public void delete(Long id) throws NotFoundException {
    User user = userRepository.findById(id)
        .orElseThrow(() -> new NotFoundException("사용자를 찾을 수 없습니다."));
    userRepository.delete(user);
  }
}
