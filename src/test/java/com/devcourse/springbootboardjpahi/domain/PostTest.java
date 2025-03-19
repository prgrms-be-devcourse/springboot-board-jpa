package com.devcourse.springbootboardjpahi.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatException;

import com.github.javafaker.Faker;
import java.time.LocalDateTime;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PostTest {

    static final Faker faker = new Faker();

    @Autowired
    TestEntityManager entityManager;

    User author;

    @BeforeEach
    void setUp() {
        String name = faker.name().firstName();
        int age = faker.number().numberBetween(0, 120);
        String hobby = faker.esports().game();
        author = User.builder()
                .name(name)
                .age(age)
                .hobby(hobby)
                .build();

        entityManager.persist(author);
    }

    @DisplayName("포스트 인스턴스를 생성한다.")
    @Test
    void testCreate() {
        // given
        String title = faker.book().title();
        String content = faker.howIMetYourMother().catchPhrase();

        // when
        Post expected = Post.builder()
                .title(title)
                .content(content)
                .user(author)
                .build();

        Post actual = entityManager.persistFlushFind(expected);

        // then
        assertThat(actual).usingRecursiveComparison()
                .ignoringExpectedNullFields()
                .isEqualTo(expected);
    }

    @DisplayName("제목이 null일 경우 예외가 발생한다.")
    @Test
    void testCreateNullName() {
        // given
        String content = faker.shakespeare().romeoAndJulietQuote();

        // when
        Post expected = Post.builder()
                .content(content)
                .user(author)
                .build();

        ThrowingCallable target = () -> entityManager.persistFlushFind(expected);

        // then
        assertThatException().isThrownBy(target)
                .isInstanceOf(ConstraintViolationException.class)
                .withMessageContaining("Column 'title' cannot be null");
    }

    @DisplayName("내용이 null일 경우 예외가 발생한다.")
    @Test
    void testCreateNullAge() {
        // given
        String title = faker.book().title();

        // when
        Post expected = Post.builder()
                .title(title)
                .user(author)
                .build();

        ThrowingCallable target = () -> entityManager.persistFlushFind(expected);

        // then
        assertThatException().isThrownBy(target)
                .isInstanceOf(ConstraintViolationException.class)
                .withMessageContaining("Column 'content' cannot be null");
    }

    @DisplayName("포스트의 작성자를 가져온다.")
    @Test
    void testMappingUser() {
        // given // when
        String title = faker.book().title();
        String content = faker.gameOfThrones().quote();

        Post post = Post.builder()
                .title(title)
                .content(content)
                .user(author)
                .build();

        entityManager.persistAndFlush(post);

        // then
        entityManager.clear();

        Post actualPost = entityManager.find(Post.class, post.getId());
        User actualAuthor = actualPost.getUser();

        assertThat(actualAuthor).isNotNull()
                .hasFieldOrPropertyWithValue("id", author.getId())
                .hasFieldOrPropertyWithValue("name", author.getName())
                .hasFieldOrPropertyWithValue("age", author.getAge());
    }

    @DisplayName("포스트의 제목을 수정한다.")
    @Test
    void testUpdateTitle() throws InterruptedException {
        // given
        String title = faker.book().title();
        String content = faker.gameOfThrones().quote();
        Post post = Post.builder()
                .title(title)
                .content(content)
                .user(author)
                .build();

        entityManager.persistAndFlush(post);

        LocalDateTime beforeUpdated = post.getUpdatedAt();
        String expected = faker.book().title();

        Thread.sleep(100);

        // when
        post.updateTitle(expected);

        entityManager.flush();

        // then
        entityManager.clear();

        Post actualPost = entityManager.find(Post.class, post.getId());
        String actual = actualPost.getTitle();
        LocalDateTime afterUpdated = actualPost.getUpdatedAt();

        assertThat(actual).isEqualTo(expected);
        assertThat(afterUpdated).isAfter(beforeUpdated);
    }

    @DisplayName("포스트의 내용을 수정한다.")
    @Test
    void testUpdateContent() throws InterruptedException {
        // given
        String title = faker.book().title();
        String content = faker.gameOfThrones().quote();
        Post post = Post.builder()
                .title(title)
                .content(content)
                .user(author)
                .build();

        entityManager.persistAndFlush(post);

        LocalDateTime beforeUpdated = post.getUpdatedAt();
        String expected = faker.gameOfThrones().quote();

        Thread.sleep(100);

        // when
        post.updateContent(expected);

        entityManager.flush();

        // then
        entityManager.clear();

        Post actualPost = entityManager.find(Post.class, post.getId());
        String actual = actualPost.getContent();
        LocalDateTime afterUpdated = actualPost.getUpdatedAt();

        assertThat(actual).isEqualTo(expected);
        assertThat(afterUpdated).isAfter(beforeUpdated);
    }
}
