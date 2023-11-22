package com.devcourse.springbootboardjpahi.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.devcourse.springbootboardjpahi.domain.User;
import com.devcourse.springbootboardjpahi.dto.UserDto;
import com.devcourse.springbootboardjpahi.repository.UserRepository;
import com.github.javafaker.Faker;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestInstance(Lifecycle.PER_CLASS)
class UserServiceTest {
    static final Faker faker = new Faker();

    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;

    @BeforeAll
    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @DisplayName("유저를 생성한다.")
    @Test
    void testCreate() {
        // given
        UserDto userDto = generateUserDto();

        // when
        User created = userService.create(userDto);

        // then
        Optional<User> actual = userRepository.findById(created.getId());

        assertThat(actual).isNotEmpty();
        assertThat(actual.get()).usingRecursiveComparison()
                .ignoringExpectedNullFields()
                .isEqualTo(created);
    }

    @DisplayName("전체 유저 목록을 조회한다.")
    @Test
    void testFindAll() {
        // given
        int userCount = faker.number().numberBetween(5, 10);

        saveUsers(userCount);

        // when
        List<User> users = userService.findAll();

        // then
        assertThat(users).hasSize(userCount);
    }

    private UserDto generateUserDto() {
        String name = faker.name().firstName();
        int age = faker.number().randomDigitNotZero();
        String hobby = faker.esports().game();

        return UserDto.builder()
                .name(name)
                .age(age)
                .hobby(hobby)
                .build();
    }

    private void saveUser() {
        UserDto userDto = generateUserDto();
        User user = User.builder()
                .name(userDto.name())
                .age(userDto.age())
                .hobby(userDto.hobby())
                .build();

        userRepository.save(user);
    }

    private void saveUsers(int count) {
        for (int i = 0; i < count; i++) {
            saveUser();
        }
    }
}
