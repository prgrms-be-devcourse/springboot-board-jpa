package com.kdt.board.user.repository;

import static org.assertj.core.api.Assertions.*;

import com.kdt.board.user.domain.User;
import java.util.Optional;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    EntityManager em;

    @Test
    @DisplayName("저장된 User를 검색할 수 있다.")
    void userFindTest() {
        // Given
        User user = new User("CHOI", 27);
        userRepository.save(user);
        em.flush();
        em.clear();

        // When
        Optional<User> findUser = userRepository.findById(user.getId());

        // Then
        assertThat(findUser.isEmpty()).isFalse();
    }
    
    @Test
    @DisplayName("저장된 User 정보 업데이트, dirty checking")
    void userUpdateTest() {
        // Given
        User user = new User("CHOI", 27);
        userRepository.save(user);
        em.flush();
        em.clear();

        // When
        User findUser = userRepository.findById(user.getId()).get();
        findUser.changeHobby("영화 감상");
        em.flush();
        em.clear();

        // Then
        User updateUser = userRepository.findById(user.getId()).get();
        assertThat(updateUser.getHobby()).isEqualTo("영화 감상");
    }

    @Test
    @DisplayName("저장된 User 삭제")
    void userDeleteById() {
        // Given
        User user = new User("CHOI", 27);
        userRepository.save(user);
        em.flush();
        em.clear();

        // When
        userRepository.deleteById(user.getId());
        em.flush();

        // Then
        Optional<User> findUser = userRepository.findById(user.getId());
        assertThat(findUser.isEmpty()).isTrue();
    }
}