package com.kdt.boardMission.repository;

import com.kdt.boardMission.domain.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserRepositoryTest {

    @Autowired
    UserRepository repository;

    @BeforeEach
    void setup(){
        repository.deleteAll();
        repository.save(new User("default name", 20, "hobby"));
    }

    @AfterEach
    void clean(){
        repository.deleteAll();
    }

    @Test
    @DisplayName("저장")
    public void saveTest() throws Exception {

        //given
        User user = new User("name", 10, "hobby");
        assertThat(repository.findAll().size()).isEqualTo(1);

        //when
        User save = repository.save(user);

        //then
        assertThat(repository.findAll().size()).isEqualTo(2);
    }

    @Test
    @DisplayName("중복된 이름 저장")
    public void saveSameNameTest() throws Exception {

        //given
        User user = new User("default name", 10, "hobby");

        //when

        //then
        assertThrows(RuntimeException.class, () -> repository.save(user));
    }

    @Test
    @DisplayName("수정")
    public void updateTest() throws Exception {

        //given
        User user = new User("name", 10, "hobby");
        repository.save(user);

        //when
        user.updateHobby("newHobby");
        repository.save(user);

        //then
        assertThat(repository.findById(user.getId()).get().getHobby()).isEqualTo("newHobby");
    }

    @Test
    @DisplayName("삭제")
    public void deleteTest() throws Exception {

        //given
        User user = new User("name", 10, "hobby");
        repository.save(user);
        assertThat(repository.findById(user.getId())).isPresent();

        //when
        repository.delete(user);

        //then
        assertThat(repository.findById(user.getId())).isEmpty();
    }
}