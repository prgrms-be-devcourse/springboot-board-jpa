package com.programmers.iyj.springbootboard.domain.user.repository;

import com.programmers.iyj.springbootboard.domain.user.domain.Hobby;
import com.programmers.iyj.springbootboard.domain.user.domain.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintViolationException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository repository;

    @Test
    @DisplayName("DB should save entity")
    public void whenInsertUserName() {
        // Given
        User user = User.builder()
                .name("john")
                .age(15)
                .hobby(Hobby.NETFLIX)
                .build();

        // When
        repository.save(user);

        // Then
        User entity = repository.findById(1L).get();
        assertThat(entity.getName()).isEqualTo("john");
    }

    @Test
    @DisplayName("If the user name is null, it cannot be inserted.")
    public void whenInsertNullUserName() {
        // Given
        User user = User.builder()
                .name(null)
                .age(15)
                .hobby(Hobby.NETFLIX)
                .build();

        // When, Then
        assertThrows(ConstraintViolationException.class, () ->
                repository.save(user)
        );
    }

    @Test
    @DisplayName("If the age is null, it can be inserted.")
    public void whenInsertNullAge() {
        // Given
        User user = User.builder()
                .name("john")
                .age(null)
                .build();

        // When
        repository.save(user);

        // Then
        User entity = repository.findById(1L).get();
        assertThat(entity.getName()).isEqualTo("john");
    }
}