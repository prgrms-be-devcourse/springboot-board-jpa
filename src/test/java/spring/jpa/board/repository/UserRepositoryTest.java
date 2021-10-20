package spring.jpa.board.repository;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import spring.jpa.board.domain.User;

@SpringBootTest
class UserRepositoryTest {

  @Autowired
  UserRepository userRepository;


  @Test
  @DisplayName("사용자를 생성할 수 있다.")
  public void userCreateTest() {
    //given 
    User user = new User("강희정", 24, "낮잠");

    //when
    userRepository.save(user);

    //then
    List<User> users = userRepository.findAll();
    assertThat(users, hasSize(1));
  }


}
