package com.prgrms.board.repository;

import com.prgrms.board.domain.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
class UserRepositoryTest {

    @Autowired
    private UserRepository repository;

    @AfterEach
    void tearDown(){
        repository.deleteAll();
    }

    @DisplayName("User 저장 확인")
    @Test
    void saveCustomerTest() {
        //given
        User user = User.builder()
                .name("Kimdahee")
                .age(99)
                .hobby("making")
                .build();

        //when
        repository.save(user);

        //then
        User findUser = repository.findById(user.getId()).get();
        assertThat(findUser.getName()).isEqualTo(user.getName());
        assertThat(findUser.getAge()).isEqualTo(user.getAge());
        assertThat(findUser.getHobby()).isEqualTo(user.getHobby());
        assertThat(findUser.getCreatedAt()).isEqualTo(user.getCreatedAt());
        assertThat(findUser.getUpdatedAt()).isEqualTo(user.getUpdatedAt());
    }

    @DisplayName("User 정보 갱신")
    @Test
    void updateCustomerTest(){
        //given
        User user = User.builder()
                .name("Kimdahee")
                .age(99)
                .hobby("making")
                .build();

        //when
        repository.save(user);
        user.changeUserInfo("Kimdahee", 10, "노래듣기");
        User findUser = repository.findById(user.getId()).get();

        //then
        assertThat(findUser.getName()).isEqualTo(user.getName());
        assertThat(findUser.getAge()).isEqualTo(user.getAge());
        assertThat(findUser.getHobby()).isEqualTo(user.getHobby());
        assertThat(findUser.getCreatedAt()).isEqualTo(user.getCreatedAt());
        assertThat(findUser.getUpdatedAt()).isEqualTo(user.getUpdatedAt());
    }

    @DisplayName("User 정보 삭제")
    @Test
    void deleteCustomerTest(){
        //given
        User user = User.builder()
                .name("Kimdahee")
                .age(99)
                .hobby("making")
                .build();

        //when
        repository.save(user);
        repository.deleteById(user.getId());
        Optional<User> findUser = repository.findById(user.getId());

        //then
        assertThat(findUser.isPresent()).isEqualTo(false);
    }

}
