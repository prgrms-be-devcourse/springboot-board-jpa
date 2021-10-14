package org.prgrms.springbootboard.domain;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
class UserRepositoryTest {

    @Autowired
    private UserRepository repository;

    private User user;
    private Long id;
    private String name;
    private int age;
    private String hobby;

    @BeforeEach
    void setUp() {
        repository.deleteAll();

        id = 1L;
        name = "정미";
        age = 24;
        hobby = "피아노";
        user = new User(name, age, hobby);
    }

    @Test
    void 유저를_저장한다() {
        // when
        User saved = repository.save(user);

        // then
        log.info("user id is {}", saved.getId());

        assertAll(
            () -> assertThat(saved).isNotNull(),
            () -> assertThat(saved.getName()).isEqualTo(name),
            () -> assertThat(saved.getAge()).isEqualTo(age),
            () -> assertThat(saved.getHobby()).isEqualTo(hobby),
            () -> assertThat(saved.getPosts().size()).isEqualTo(0),
            () -> assertThat(saved.getCreatedAt()).isNotNull(),
            () -> assertThat(saved.getLastModifiedAt()).isAfterOrEqualTo(saved.getCreatedAt())
        );
    }

    @Test
    void 아이디로_유저를_검색한다() {
        // given
        User saved = repository.save(user);

        // when
        Long id = saved.getId();
        Optional<User> found = repository.findById(id);

        // then
        log.info("user id is {}", id);
        assertAll(
            () -> assertThat(found.isPresent()).isTrue(),
            () -> assertThat(found.get().getId()).isEqualTo(id),
            () -> assertThat(found.get().getName()).isEqualTo(name),
            () -> assertThat(found.get().getAge()).isEqualTo(age),
            () -> assertThat(found.get().getHobby()).isEqualTo(hobby),
            () -> assertThat(found.get().getCreatedAt()).isNotNull(),
            () -> assertThat(found.get().getLastModifiedAt()).isAfterOrEqualTo(found.get().getCreatedAt())
        );
    }

    @Test
    void 모든_유저를_검색한다() {
        // given
        String name2 = "jummi";
        int age2 = 30;
        String hobby2 = "영화 시청";
        User user2 = new User(name2, age2, hobby2);
        User saved1 = repository.save(user);
        User saved2 = repository.save(user2);

        // when
        List<User> all = repository.findAll();

        // then
        log.info("user1 id is {}", saved1.getId());
        log.info("user2 id is {}", saved2.getId());

        assertAll(
            () -> assertThat(all.size()).isEqualTo(2),
            () -> assertThat(all.get(0).getId()).isEqualTo(saved1.getId()),
            () -> assertThat(all.get(1).getId()).isEqualTo(saved2.getId())
        );
    }

    @Test
    void 유저_정보를_수정한다() {
        // given
        User saved = repository.save(user);
        User found = repository.findById(saved.getId()).get();

        // when
        String newName = "mac";
        String newHobby = "청소";
        found.changeName(newName);
        found.changeHobby(newHobby);
        User updated = repository.save(found);

        // then
        assertAll(
            () -> assertThat(updated.getId()).isEqualTo(found.getId()),
            () -> assertThat(updated.getName()).isEqualTo(newName),
            () -> assertThat(updated.getHobby()).isEqualTo(newHobby),
            () -> assertThat(updated.getLastModifiedAt()).isAfter(updated.getCreatedAt())
        );
    }

    @Test
    void 유저를_삭제한다() {
        // given
        User saved = repository.save(user);

        // when
        Long id = saved.getId();
        repository.deleteById(id);
        Optional<User> found = repository.findById(id);

        // then
        assertThat(found.isEmpty()).isTrue();
    }

}
