package org.prgrms.board.domain.user.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.prgrms.board.domain.user.domain.Email;
import org.prgrms.board.domain.user.domain.Name;
import org.prgrms.board.domain.user.domain.Password;
import org.prgrms.board.domain.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    TestEntityManager testEntityManager;

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    void 이메일로_조회할수있다() {
        //given
        User user = User.builder()
                .age(27)
                .email(new Email("dbslzld15@naver.com"))
                .name(Name.builder()
                        .firstName("우진")
                        .lastName("박")
                        .build())
                .password(new Password("abcd12345@"))
                .build();
        userRepository.save(user);
        //when
        User findUser = userRepository.findByEmail(user.getEmail().getValue()).get();
        //then
        assertThat(findUser).usingRecursiveComparison().isEqualTo(user);
    }

    @Test
    void 패스워드로_조회할수있다() {
        //given
        User user = User.builder()
                .age(27)
                .email(new Email("dbslzld15@naver.com"))
                .name(Name.builder()
                        .firstName("우진")
                        .lastName("박")
                        .build())
                .password(new Password("abcd12345@"))
                .build();
        userRepository.save(user);
        //when
        User findUser = userRepository.findByPassword(user.getPassword().getValue()).get();
        //then
        assertThat(findUser).usingRecursiveComparison().isEqualTo(user);
    }

    @Test
    void ID를_통해_조회할수있다(){
        //given
        User user = User.builder()
                .age(27)
                .email(new Email("dbslzld15@naver.com"))
                .name(Name.builder()
                        .firstName("우진")
                        .lastName("박")
                        .build())
                .password(new Password("abcd12345@"))
                .build();
        userRepository.save(user);
        //when
        User findUser = userRepository.findById(user.getId()).get();
        //then
        assertThat(findUser).usingRecursiveComparison().isEqualTo(user);
    }

    @Test
    void 유저를_저장할수있다(){
        //given
        User user = User.builder()
                .age(27)
                .email(new Email("dbslzld15@naver.com"))
                .name(Name.builder()
                        .firstName("우진")
                        .lastName("박")
                        .build())
                .password(new Password("abcd12345@"))
                .build();
        //when
        User savedUser = userRepository.save(user);
        //then
        assertThat(savedUser).usingRecursiveComparison().isEqualTo(user);
    }
}