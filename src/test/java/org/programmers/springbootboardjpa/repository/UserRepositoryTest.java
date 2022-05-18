package org.programmers.springbootboardjpa.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.programmers.springbootboardjpa.domain.Age;
import org.programmers.springbootboardjpa.domain.Name;
import org.programmers.springbootboardjpa.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;
    static User testUser1;
    static User testUser2;

    @BeforeEach
    void setTestEntity() {
        testUser1 = new User("javaGuru", new Name("Joshua", "Bloch"), new Age(LocalDate.of(1961, 8, 28)));
        testUser2 = new User("resCogitans", new Name("Descartes", "Rene"), new Age(LocalDate.of(1596, 3, 31)));
        userRepository.save(testUser1);
        userRepository.save(testUser2);
    }

    @Test
    @DisplayName("닉네임 중복체크 - 중복인 경우")
    void checkDuplicateNickname_Duplicate() {
        boolean nicknameDuplication = userRepository.existsByNicknameIgnoreCase(testUser1.getNickname());

        assertThat(nicknameDuplication).isTrue();
    }

    @Test
    @DisplayName("닉네임 중복체크 - 중복인 경우: 대소문자 구별 없음")
    void checkDuplicateNickname_DuplicateIgnoreCase() {
        boolean nicknameDuplication = userRepository.existsByNicknameIgnoreCase("rescogitans");

        assertThat(nicknameDuplication).isTrue();
    }

    @Test
    @DisplayName("닉네임 중복체크 - 중복이 아닌 경우")
    void checkDuplicateNickname_NotDuplicate() {
        boolean nicknameDuplication = userRepository.existsByNicknameIgnoreCase("somethingNew");

        assertThat(nicknameDuplication).isFalse();
    }
}