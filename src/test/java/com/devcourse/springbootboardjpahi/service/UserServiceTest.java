package com.devcourse.springbootboardjpahi.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.devcourse.springbootboardjpahi.domain.User;
import com.devcourse.springbootboardjpahi.dto.CreateUserRequest;
import com.devcourse.springbootboardjpahi.dto.UserResponse;
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
        CreateUserRequest createUserRequest = generateCreateUserRequest();

        // when
        UserResponse created = userService.create(createUserRequest);

        // then
        Optional<User> actual = userRepository.findById(created.id());

        assertThat(actual).isNotEmpty();
        assertThat(actual.get()).hasFieldOrPropertyWithValue("id", created.id())
                .hasFieldOrPropertyWithValue("name", created.name())
                .hasFieldOrPropertyWithValue("age", created.age())
                .hasFieldOrPropertyWithValue("hobby", created.hobby())
                .hasFieldOrPropertyWithValue("createdAt", created.createdAt());
    }

    @DisplayName("전체 유저 목록을 조회한다.")
    @Test
    void testFindAll() {
        // given
        int userCount = faker.number().numberBetween(5, 10);

        saveUsers(userCount);

        // when
        List<UserResponse> users = userService.findAll();

        // then
        assertThat(users).hasSize(userCount);
    }

    private CreateUserRequest generateCreateUserRequest() {
        String name = faker.name().firstName();
        int age = faker.number().numberBetween(0, 120);
        String hobby = faker.esports().game();

        return new CreateUserRequest(name, age, hobby);
    }

    private void saveUser() {
        CreateUserRequest createUserRequest = generateCreateUserRequest();
        User user = User.builder()
                .name(createUserRequest.name())
                .age(createUserRequest.age())
                .hobby(createUserRequest.hobby())
                .build();

        userRepository.save(user);
    }

    private void saveUsers(int count) {
        for (int i = 0; i < count; i++) {
            saveUser();
        }
    }
}
