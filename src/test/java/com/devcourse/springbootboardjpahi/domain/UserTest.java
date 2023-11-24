package com.devcourse.springbootboardjpahi.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatException;

import com.github.javafaker.Faker;
import java.util.List;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserTest {

    static final Faker faker = new Faker();

    @Autowired
    TestEntityManager entityManager;

    @DisplayName("유저 인스턴스를 생성한다.")
    @Test
    void testCreate() {
        // given
        String name = faker.name().firstName();
        int age = faker.number().numberBetween(0, 120);
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
                .ignoringExpectedNullFields()
                .isEqualTo(expected);
    }

    @DisplayName("이름이 null일 경우 예외가 발생한다.")
    @Test
    void testCreateNullName() {
        // given
        int age = faker.number().numberBetween(0, 120);
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
                .isInstanceOf(ConstraintViolationException.class)
                .withMessageContaining("Column 'name' cannot be null");
    }

    @DisplayName("나이가 null일 경우 예외가 발생한다.")
    @Test
    void testCreateNullAge() {
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
                .isInstanceOf(ConstraintViolationException.class)
                .withMessageContaining("Column 'age' cannot be null");
    }

    @DisplayName("유저의 포스트들을 가져온다.")
    @Test
    void testMappingPosts() {
        // given
        String name = faker.name().firstName();
        int age = faker.number().numberBetween(0, 120);
        String hobby = faker.esports().game();
        User author = User.builder()
                .name(name)
                .age(age)
                .hobby(hobby)
                .build();

        entityManager.persistAndFlush(author);

        // when
        String title = faker.book().title();
        String content = faker.harryPotter().quote();
        Post firstPost = Post.builder()
                .title(title)
                .content(content)
                .user(author)
                .build();
        Post secondPost = Post.builder()
                .title(title)
                .content(content)
                .user(author)
                .build();

        entityManager.persistAndFlush(firstPost);
        entityManager.persistAndFlush(secondPost);

        //then
        entityManager.clear();

        User actualUser = entityManager.find(User.class, author.getId());
        List<Long> actualPostIds = actualUser.getPosts()
                .stream()
                .map(Post::getId)
                .toList();

        assertThat(actualPostIds).containsExactlyInAnyOrder(firstPost.getId(), secondPost.getId());
    }
}
