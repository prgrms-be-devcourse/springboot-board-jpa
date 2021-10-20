package spring.jpa.board.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.samePropertyValuesAs;

import java.util.List;
import javassist.NotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import spring.jpa.board.domain.User;
import spring.jpa.board.dto.user.UserConverter;
import spring.jpa.board.dto.user.UserDto;
import spring.jpa.board.repository.UserRepository;

@SpringBootTest
class UserServiceTest {

  @Autowired
  UserRepository userRepository;

  @Autowired
  UserService userService;

  @Autowired
  UserConverter userConverter;


  @AfterEach
  public void cleanUp() {
    userRepository.deleteAll();
  }


  @Test
  @DisplayName("사용자를 생성할 수 있다.")
  public void saveUserTest() {
    //given
    User user = new User("강희정", 24, "낮잠");

    //when
    userService.save(userConverter.convertUserDto(user));
    //then
    List<UserDto> all = userService.findAll();
    assertThat(all, hasSize(1));
  }


  @Test
  @DisplayName("모든 사용자 목록을 읽을 수 있다.")
  public void findAllTest() {
    //given
    User user1 = new User("강희정", 24, "낮잠");
    userService.save(userConverter.convertUserDto(user1));

    User user2 = new User("강희정", 24, "낮잠");
    userService.save(userConverter.convertUserDto(user2));

    //when
    List<UserDto> all = userService.findAll();

    //then
    assertThat(all, hasSize(2));
  }


  @Test
  @DisplayName("id로 사용자를 찾을 수 있다.")
  public void findByIdTest() throws NotFoundException {
    //given
    User user = new User("강희정", 24, "낮잠");
    UserDto saveUser = userService.save(userConverter.convertUserDto(user));

    //when
    UserDto findUser = userService.findById(saveUser.getId());

    //then
    assertThat(findUser, samePropertyValuesAs(saveUser));
  }


}
