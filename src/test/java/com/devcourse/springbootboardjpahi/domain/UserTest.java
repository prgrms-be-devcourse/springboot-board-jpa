package com.devcourse.springbootboardjpahi.domain;

import com.github.javafaker.Faker;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.hibernate.PropertyValueException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatException;

@DataJpaTest
@EnableJpaAuditing
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserTest {

    static final Faker faker = new Faker();

    @Autowired
    TestEntityManager entityManager;

    @DisplayName("유저 인스턴스를 생성한다.")
    @Test
    void create() {
        // given
        String name = faker.name().firstName();
        int age = faker.number().randomDigitNotZero();
        String hobby = faker.esports().game();

        // when
        User expected = User.builder()
                .name(name)
                .age(age)
                .hobby(hobby)
                .build();

        User actual = entityManager.persistFlushFind(expected);

        // then
        assertThat(actual).usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @DisplayName("이름이 null일 경우 예외가 발생한다.")
    @Test
    void createNullName() throws Throwable {
        // given
        int age = faker.number().randomDigitNotZero();
        String hobby = faker.esports().game();

        // when
        User expected = User.builder()
                .name(null)
                .age(age)
                .hobby(hobby)
                .build();

        ThrowingCallable target = () -> entityManager.persistFlushFind(expected);

        // then
        assertThatException().isThrownBy(target)
                .isInstanceOf(PropertyValueException.class)
                .withMessageContaining("not-null property references a null or transient value")
                .withMessageContaining("domain.User.name");
    }

    @DisplayName("나이가 null일 경우 예외가 발생한다.")
    @Test
    void createNullAge() throws Throwable {
        // given
        String name = faker.name().firstName();
        String hobby = faker.esports().game();

        // when
        User expected = User.builder()
                .name(name)
                .age(null)
                .hobby(hobby)
                .build();

        ThrowingCallable target = () -> entityManager.persistFlushFind(expected);

        // then
        assertThatException().isThrownBy(target)
                .isInstanceOf(PropertyValueException.class)
                .withMessageContaining("not-null property references a null or transient value")
                .withMessageContaining("domain.User.age");
    }
}
