package spring.jpa.board.service;

import java.util.List;
import java.util.stream.Collectors;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.jpa.board.domain.User;
import spring.jpa.board.dto.user.UserConverter;
import spring.jpa.board.dto.user.UserDto;
import spring.jpa.board.repository.UserRepository;

@Slf4j
@Service
public class UserService {

  private final UserRepository userRepository;

  private final UserConverter userConverter;

  public UserService(UserRepository userRepository,
      UserConverter userConverter) {
    this.userRepository = userRepository;
    this.userConverter = userConverter;
  }

  @Transactional
  public UserDto save(UserDto userDto) {
    User saveUser = userRepository.save(userConverter.convertUser(userDto));

    if (saveUser.getCreatedBy() == null) {
      saveUser.setCreatedBy(saveUser.getId());
      userRepository.save(saveUser);
    }
    return userConverter.convertUserDto(
        saveUser);
  }

  @Transactional
  public UserDto findById(Long id) throws NotFoundException {
    return userRepository.findById(id)
        .map(userConverter::convertUserDto)
        .orElseThrow(() -> new NotFoundException("사용자 정보를 찾을 수 없습니다."));
  }

  @Transactional
  public List<UserDto> findAll() {
    return userRepository.findAll()
        .stream().map(userConverter::convertUserDto)
        .collect(Collectors.toList());
  }


  @Transactional
  public void deleteAll() {
    userRepository.deleteAll();
  }

  @Transactional
  public void deleteById(Long id) {
    userRepository.deleteById(id);
  }
}
