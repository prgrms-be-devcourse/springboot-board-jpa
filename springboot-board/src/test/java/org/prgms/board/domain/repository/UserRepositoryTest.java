package org.prgms.board.domain.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgms.board.domain.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    private Long userId;

    @BeforeEach
    void setUp() {
        User user = User.builder()
            .name("김부희")
            .age(26)
            .hobby("만들기")
            .build();

        userId = userRepository.save(user).getId();
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @DisplayName("사용자 저장 확인")
    @Test
    void userInsertTest() {
        assertThat(userRepository.findById(userId).isPresent()).isEqualTo(true);
    }

    @DisplayName("사용자 정보 수정 확인")
    @Test
    void userUpdateTest() {
        User user = userRepository.findById(userId).get();
        user.changeInfo("김부희", 26, "터프팅");

        Optional<User> retrievedUser = userRepository.findById(userId);
        assertThat(retrievedUser.isPresent()).isEqualTo(true);
        assertThat(retrievedUser.get().getHobby()).isEqualTo("터프팅");
    }

    @DisplayName("사용자 삭제 확인")
    @Test
    void userDeleteTest() {
        User user = userRepository.findById(userId).get();
        userRepository.delete(user);

        Optional<User> retrievedUser = userRepository.findById(userId);
        assertThat(retrievedUser.isPresent()).isEqualTo(false);
    }

    @DisplayName("사용자 정보 상세 조회 확인")
    @Test
    void userFindByIdTest() {
        Optional<User> retrievedUser = userRepository.findById(userId);
        assertThat(retrievedUser.isPresent()).isEqualTo(true);
        assertThat(retrievedUser.get().getName()).isEqualTo("김부희");
        assertThat(retrievedUser.get().getAge()).isEqualTo(26);
        assertThat(retrievedUser.get().getHobby()).isEqualTo("만들기");
    }
}