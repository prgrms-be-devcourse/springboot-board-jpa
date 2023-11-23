package com.devcourse.springbootboardjpahi.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import com.devcourse.springbootboardjpahi.domain.Post;
import com.devcourse.springbootboardjpahi.domain.User;
import com.devcourse.springbootboardjpahi.dto.CreatePostRequest;
import com.devcourse.springbootboardjpahi.dto.PageResponse;
import com.devcourse.springbootboardjpahi.dto.PostDetailResponse;
import com.devcourse.springbootboardjpahi.dto.PostResponse;
import com.devcourse.springbootboardjpahi.dto.UpdatePostRequest;
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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

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
        CreatePostRequest request = generateCreateRequest(user.getId());

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
        CreatePostRequest request = generateCreateRequest(fakeId);

        // when
        ThrowingCallable target = () -> postService.create(request);

        // then
        assertThatExceptionOfType(NoSuchElementException.class).isThrownBy(target);
    }

    @DisplayName("포스트를 상세 조회한다.")
    @Test
    void testFindById() {
        // given
        User author = generateAuthor();
        userRepository.save(author);
        CreatePostRequest createPostRequest = generateCreateRequest(author.getId());
        PostResponse post = postService.create(createPostRequest);

        // when
        PostDetailResponse postDetailResponse = postService.findById(post.id());

        // then
        assertThat(postDetailResponse)
                .hasFieldOrPropertyWithValue("id", post.id())
                .hasFieldOrPropertyWithValue("title", post.title())
                .hasFieldOrPropertyWithValue("content", post.content())
                .hasFieldOrPropertyWithValue("authorName", post.authorName());
    }

    @DisplayName("존재하지 않는 포스트의 조회를 실패한다.")
    @Test
    void testFindByIdNonExistentId() {
        // given
        long id = Math.abs(faker.number().randomDigitNotZero());

        // when
        ThrowingCallable target = () -> postService.findById(id);

        // then
        assertThatExceptionOfType(NoSuchElementException.class).isThrownBy(target);
    }

    @DisplayName("포스트의 제목과 내용을 수정한다.")
    @Test
    void testUpdateById() {
        // given
        User user = generateAuthor();

        userRepository.save(user);

        CreatePostRequest createPostRequest = generateCreateRequest(user.getId());

        postService.create(createPostRequest);

        UpdatePostRequest expected = generateUpdateRequest();
        Post post = postRepository.findAll()
                .stream()
                .findFirst()
                .orElseThrow();

        // when
        PostDetailResponse actual = postService.updateById(post.getId(), expected);

        // then
        assertThat(actual)
                .hasFieldOrPropertyWithValue("title", expected.title())
                .hasFieldOrPropertyWithValue("content", expected.content());
    }

    @DisplayName("포스트가 없을 때 비어있는 페이지를 반환한다.")
    @Test
    void testGetPageEmpty() {
        // given // when
        PageResponse<PostResponse> page = postService.getPage(Pageable.unpaged());

        // then
        assertThat(page.isEmpty()).isTrue();
        assertThat(page.totalPages()).isEqualTo(1);
        assertThat(page.totalElements()).isEqualTo(0);
        assertThat(page.content()).isEmpty();
    }

    @DisplayName("포스트를 페이징 조회한다.")
    @Test
    void testGetPage() {
        // given
        int totalCount = 30;
        int pageSize = 10;

        savePosts(totalCount);

        // when
        PageRequest pageRequest = PageRequest.ofSize(pageSize);
        PageResponse<PostResponse> page = postService.getPage(pageRequest);

        // then
        int expectedPages = (int) Math.ceil((double) totalCount / pageSize);

        assertThat(page.isEmpty()).isFalse();
        assertThat(page.totalPages()).isEqualTo(expectedPages);
        assertThat(page.totalElements()).isEqualTo(totalCount);
        assertThat(page.content()).hasSize(pageSize);
    }

    private CreatePostRequest generateCreateRequest(Long userId) {
        String title = faker.book().title();
        String content = faker.shakespeare().hamletQuote();

        return new CreatePostRequest(title, content, userId);
    }

    private UpdatePostRequest generateUpdateRequest() {
        String title = faker.book().title();
        String content = faker.shakespeare().hamletQuote();

        return new UpdatePostRequest(title, content);
    }

    private User generateAuthor() {
        long id = Math.abs(faker.number().randomDigitNotZero());
        String name = faker.name().firstName();
        int age = faker.number().numberBetween(0, 120);
        String hobby = faker.esports().game();

        return User.builder()
                .id(id)
                .name(name)
                .age(age)
                .hobby(hobby)
                .build();
    }

    private void savePosts(int count) {
        for (int i = 0; i < count; i++) {
            savePost();
        }
    }

    private Post savePost() {
        User author = generateAuthor();
        userRepository.save(author);
        String title = faker.book().title();
        String content = faker.shakespeare().asYouLikeItQuote();
        Post post = Post.builder()
                .title(title)
                .content(content)
                .user(author)
                .build();

        return postRepository.save(post);
    }
}
