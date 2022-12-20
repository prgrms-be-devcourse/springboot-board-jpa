package org.programmers.board.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.programmers.board.entity.User;
import org.programmers.board.entity.vo.Name;
import org.programmers.board.exception.TooLongNameException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("User 저장")
    void saveUser() {
        User user = new User(new Name("지웅"), 27, "독서");

        User savedUser = userRepository.save(user);

        assertAll(
                () -> assertThat(savedUser.getId()).isNotNull(),
                () -> assertThat(savedUser.getName().getName()).isEqualTo(user.getName().getName()),
                () -> assertThat(savedUser.getAge()).isEqualTo(user.getAge()),
                () -> assertThat(savedUser.getHobby()).isEqualTo(user.getHobby())
        );
    }

    @Test
    @DisplayName("User 저장 실패 테스트 - 사유: 이름 공백")
    void savedUserFailedByBlank() {

        assertThatThrownBy(() ->
                userRepository.save(new User(new Name(""), 27, "독서")))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("이름은 공백일 수 없습니다.");
    }

    @Test
    @DisplayName("User 저장 실패 테스트 - 사유: 이름 길이")
    void savedUserFailedByLength() {

        assertThatThrownBy(() ->
                userRepository.save(new User(new Name("엄청나게긴유저네임을입력했다그래서실패한다."), 27, "독서")))
                .isInstanceOf(TooLongNameException.class)
                .hasMessageContaining("이름이 너무 깁니다. 15자 이하로 해주세요.");
    }

    @Test
    @DisplayName("전체조회 테스트")
    void findAllUser() {
        User user = new User(new Name("지웅"), 27, "독서");
        userRepository.save(user);

        List<User> all = userRepository.findAll();

        assertAll(
                () -> assertThat(all).hasSize(1),
                () -> assertThat(all.get(0).getId()).isNotNull(),
                () -> assertThat(all.get(0).getName().getName()).isEqualTo(user.getName().getName()),
                () -> assertThat(all.get(0).getAge()).isEqualTo(user.getAge()),
                () -> assertThat(all.get(0).getHobby()).isEqualTo(user.getHobby())
        );
    }

    @Test
    @DisplayName("ID로 유저 조회")
    void findByIdTest() {
        User user = new User(new Name("지웅"), 27, "독서");
        userRepository.save(user);

        User savedUser = userRepository.findById(user.getId()).orElseThrow(
                () -> new IllegalArgumentException("해당 User가 존재하지 않습니다.")
        );

        assertAll(
                () -> assertThat(savedUser.getId()).isNotNull(),
                () -> assertThat(savedUser.getName().getName()).isEqualTo(user.getName().getName()),
                () -> assertThat(savedUser.getAge()).isEqualTo(user.getAge()),
                () -> assertThat(savedUser.getHobby()).isEqualTo(user.getHobby())
        );
    }

    @Test
    @DisplayName("유저 삭제 테스트")
    void deleteTest() {
        User user = new User(new Name("지웅"), 27, "독서");
        userRepository.save(user);

        userRepository.deleteById(user.getId());

        Optional<User> findUser = userRepository.findById(user.getId());

        assertThat(findUser.isPresent()).isFalse();
    }
}