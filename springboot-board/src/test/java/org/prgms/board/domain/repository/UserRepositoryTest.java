package org.prgms.board.domain.repository;

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
public class UserRepositoryTest {
    private static final String UPDATE_NAME = "김부희";
    private static final int UPDATE_AGE = 26;
    private static final String UPDATE_HOBBY = "터프팅";

    @Autowired
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();

        user = User.builder()
            .name("김부희")
            .age(26)
            .hobby("만들기")
            .build();

        userRepository.save(user);
    }

    @DisplayName("사용자 저장 확인")
    @Test
    void userInsertTest() {
        assertThat(userRepository.findById(user.getId()).isPresent()).isEqualTo(true);
    }

    @DisplayName("사용자 정보 수정 확인")
    @Test
    void userUpdateTest() {
        user.changeInfo(UPDATE_NAME, UPDATE_AGE, UPDATE_HOBBY);

        Optional<User> retrievedUser = userRepository.findById(user.getId());
        assertThat(retrievedUser.isPresent()).isEqualTo(true);
        assertThat(retrievedUser.get().getHobby()).isEqualTo(UPDATE_HOBBY);
    }

    @DisplayName("사용자 삭제 확인")
    @Test
    void userDeleteTest() {
        userRepository.delete(user);

        Optional<User> retrievedUser = userRepository.findById(user.getId());
        assertThat(retrievedUser.isPresent()).isEqualTo(false);
    }

    @DisplayName("사용자 정보 상세 조회 확인")
    @Test
    void userFindByIdTest() {
        Optional<User> retrievedUser = userRepository.findById(user.getId());
        assertThat(retrievedUser.isPresent()).isEqualTo(true);
        assertThat(retrievedUser.get().getName()).isEqualTo(user.getName());
        assertThat(retrievedUser.get().getAge()).isEqualTo(user.getAge());
        assertThat(retrievedUser.get().getHobby()).isEqualTo(user.getHobby());
    }
}