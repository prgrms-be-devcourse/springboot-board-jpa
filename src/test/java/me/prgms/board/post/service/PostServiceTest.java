package me.prgms.board.post.service;

import me.prgms.board.domain.post.Post;
import me.prgms.board.domain.User;
import me.prgms.board.post.converter.PostConverter;
import me.prgms.board.post.dto.CreatePostDto;
import me.prgms.board.post.dto.ResponsePostDto;
import me.prgms.board.post.dto.UpdatePostDto;
import me.prgms.board.post.repository.PostRepository;
import me.prgms.board.user.repository.UserRepository;
import me.prgms.board.user.dto.UserDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@Import({PostService.class, PostConverter.class})
class PostServiceTest {

    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    private User user;
    private UserDto userDto;

    @BeforeEach
    void setup() {
        user = new User("yanju", 27, "exercise");
        userRepository.save(user);
        userDto = new UserDto(user.getId(), user.getName(), user.getAge(), user.getHobby());
    }

    @AfterEach
    void clean() {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("게시글 저장")
    void createTest() {
        CreatePostDto createDto = new CreatePostDto("test-title", "test-content", userDto);

        Long id = postService.create(createDto);

        Optional<Post> findPost = postRepository.findById(id);
        assertThat(findPost).isPresent();
    }

    @Test
    @DisplayName("유저가 없으면 게시글 저장 실패")
    void createFailTest() {
        userRepository.deleteAll();

        CreatePostDto createDto = new CreatePostDto("test-title", "test-content", userDto);
        assertThrows(IllegalArgumentException.class, () -> {
            postService.create(createDto);
        });
    }

    @Test
    @DisplayName("제목이 없으면 게시글 저장 실패")
    void createFailByNoTitleTest() {
        CreatePostDto createDto = new CreatePostDto("", "test-content", userDto);
        assertThrows(IllegalArgumentException.class, () -> {
            postService.create(createDto);
        });
    }

    @Test
    @DisplayName("제목이 50글자를 넘으면 게시글 저장 실패")
    void createFailByLongTitleTest() {
        CreatePostDto createDto =
                new CreatePostDto(
                        "test-titletest-titletest-titletest-titletest-titletest-titletest-titletest-titletest-titletest-titletest-titletest-title",
                        "test-content",
                        userDto);
        assertThrows(IllegalArgumentException.class, () -> {
            postService.create(createDto);
        });
    }

    @Test
    @DisplayName("내용이 없으면 게시글 저장 실패")
    void createFailByNoContentTest() {
        CreatePostDto createDto = new CreatePostDto("test-title", "", userDto);

        assertThrows(IllegalArgumentException.class, () -> {
            postService.create(createDto);
        });
    }

    @Test
    @DisplayName("게시글 내용 수정")
    void updateTest() {
        CreatePostDto createDto = new CreatePostDto("test-title", "test-content", userDto);
        Long id = postService.create(createDto);

        UpdatePostDto updateDto = new UpdatePostDto("update-title", "update-content");
        postService.update(id, updateDto);
        Optional<Post> updatePost = postRepository.findById(id);

        assertAll(
                () -> assertThat(updatePost).isPresent(),
                () -> assertThat(id).isEqualTo(updatePost.get().getId()),
                () -> assertThat(updatePost.get().getTitle()).isEqualTo(updateDto.getTitle()),
                () -> assertThat(updatePost.get().getContent()).isEqualTo(updateDto.getContent())
        );
    }

    @Test
    @DisplayName("게시글이 없는 경우 수정 불가능")
    void updateFailTest() {
        UpdatePostDto updateDto = new UpdatePostDto("update-title", "update-content");

        assertThrows(IllegalArgumentException.class, () -> {
            postService.update(1L, updateDto);
        });
    }

    @Test
    @DisplayName("유저가 없으면 수정 불가능")
    void updateFailByNoUserTest() {
        CreatePostDto createDto = new CreatePostDto("test-title", "test-content", userDto);
        Long id = postService.create(createDto);

        userRepository.deleteAll();

        UpdatePostDto updateDto = new UpdatePostDto("update-title", "update-content");

        assertThrows(IllegalArgumentException.class, () -> {
            postService.update(id, updateDto);
        });
    }

    @Test
    @DisplayName("ID로 찾기")
    void findByIdTest() {
        Post post = new Post("test-title", "test-content", user);
        Post savePost = postRepository.save(post);

        ResponsePostDto responseDto = postService.findPostById(savePost.getId());

        assertAll(
                () -> assertThat(responseDto.getId()).isEqualTo(post.getId()),
                () -> assertThat(responseDto.getTitle()).isEqualTo(post.getTitle()),
                () -> assertThat(responseDto.getContent()).isEqualTo(post.getContent())
        );
    }

    @Test
    @DisplayName("페이징 조회")
    void findPageable() {
        postRepository.save(new Post("test-title", "test-content", user));
        postRepository.save(new Post("test-title2", "test-content2", user));
        postRepository.save(new Post("test-title3", "test-content3", user));
        postRepository.save(new Post("test-title4", "test-content4", user));
        postRepository.save(new Post("test-title5", "test-content5", user));

        Pageable pageable = PageRequest.of(0, 3);

        Page<ResponsePostDto> orders = postService.findPosts(pageable);

        assertAll(
                () -> assertThat(orders.getTotalElements()).isEqualTo(5),
                () -> assertThat(orders.getSize()).isEqualTo(3),
                () -> assertThat(orders.getTotalPages()).isEqualTo(2),
                () -> assertTrue(orders.hasNext()),
                () -> assertThat(orders.nextPageable().getPageNumber()).isEqualTo(1)
        );
    }

}