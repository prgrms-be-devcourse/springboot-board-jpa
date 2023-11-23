package com.devcourse.springbootboardjpahi.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import com.devcourse.springbootboardjpahi.domain.Post;
import com.devcourse.springbootboardjpahi.domain.User;
import com.devcourse.springbootboardjpahi.dto.CreatePostRequest;
import com.devcourse.springbootboardjpahi.dto.PostResponse;
import com.devcourse.springbootboardjpahi.repository.PostRepository;
import com.devcourse.springbootboardjpahi.repository.UserRepository;
import com.github.javafaker.Faker;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
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
class PostServiceTest {

    static final Faker faker = new Faker();

    @Autowired
    PostService postService;
    @Autowired
    PostRepository postRepository;
    @Autowired
    UserRepository userRepository;

    @BeforeAll
    @AfterEach
    void clear() {
        postRepository.deleteAll();
        userRepository.deleteAll();
    }

    @DisplayName("포스트를 생성한다.")
    @Test
    void testCreate() {
        // given
        User user = generateAuthor();
        userRepository.save(user);
        CreatePostRequest request = generateRequest(user.getId());

        // when
        PostResponse response = postService.create(request);

        // then
        Optional<Post> actual = postRepository.findById(response.id());

        assertThat(actual).isNotEmpty();
        assertThat(actual.get()).hasFieldOrPropertyWithValue("id", response.id())
                .hasFieldOrPropertyWithValue("title", response.title())
                .hasFieldOrPropertyWithValue("content", response.content());
    }

    @DisplayName("존재하지 않는 유저의 아이디일 경우 포스트 생성에 실패한다.")
    @Test
    void testCreateNonExistentUser() {
        // given
        long fakeId = faker.random().nextLong();
        CreatePostRequest request = generateRequest(fakeId);

        // when
        ThrowingCallable target = () -> postService.create(request);

        // then
        assertThatExceptionOfType(NoSuchElementException.class).isThrownBy(target);
    }

    private CreatePostRequest generateRequest(Long userId) {
        String title = faker.book().title();
        String content = faker.shakespeare().hamletQuote();

        return new CreatePostRequest(title, content, userId);
    }

    private User generateAuthor() {
        String name = faker.name().firstName();
        int age = faker.number().randomDigitNotZero();
        String hobby = faker.esports().game();

        return User.builder()
                .name(name)
                .age(age)
                .hobby(hobby)
                .build();
    }
}
