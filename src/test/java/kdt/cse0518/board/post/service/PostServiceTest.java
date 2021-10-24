package kdt.cse0518.board.post.service;

import kdt.cse0518.board.post.dto.PostDto;
import kdt.cse0518.board.post.dto.ResponseDto;
import kdt.cse0518.board.post.entity.Post;
import kdt.cse0518.board.post.factory.PostFactory;
import kdt.cse0518.board.post.repository.PostRepository;
import kdt.cse0518.board.user.converter.UserConverter;
import kdt.cse0518.board.user.dto.UserDto;
import kdt.cse0518.board.user.entity.User;
import kdt.cse0518.board.user.factory.UserFactory;
import kdt.cse0518.board.user.repository.UserRepository;
import kdt.cse0518.board.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import javax.transaction.Transactional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class PostServiceTest {

    final private Pageable pageable = PageRequest.of(0, 5);
    @Autowired
    UserRepository userRepository;
    @Autowired
    PostRepository postRepository;
    private User newUser1;
    private User newUser2;
    private Post newPost1;
    private Post newPost2;
    private Post newPost3;
    @Autowired
    private UserConverter userConverter;
    @Autowired
    private UserService userService;
    @Autowired
    private PostService postService;
    @Autowired
    private UserFactory userFactory;
    @Autowired
    private PostFactory postFactory;

    @BeforeEach
    void setUp() {
        newUser1 = userFactory.createUser("최승은", 26, "weight training");
        newPost1 = postFactory.createPost("제목1", "내용1", newUser1);
    }

    @Test
    @Order(1)
    @DisplayName("postId로 Post를 조회할 수 있다.")
    @Transactional
    void testFindById() {
        final PostDto finded = postService.findById(newPost1.getPostId());

        assertThat(finded.getTitle(), is("제목1"));
        assertThat(finded.getContent(), is("내용1"));
    }

    @Test
    @Order(2)
    @DisplayName("해당 User가 작성한 Posts를 전체 조회할 수 있다.")
    @Transactional
    void testFindAllByUser() {
        // Given
        newUser2 = userFactory.createUser("최승은2", 26, "취미2");
        newPost2 = postFactory.createPost("제목2", "내용2", newUser1);
        newPost3 = postFactory.createPost("제목3", "내용3", newUser2);

        // When
        final UserDto findedUserDto = userService.findById(newUser1.getUserId());
        final Page<PostDto> allPostsByUser = postService.findAllByUser(findedUserDto, pageable);
        final UserDto exceptionUserDto = UserDto.builder().userId(100L).build();

        // Then
        assertThat(allPostsByUser.getContent().size(), is(2));
        assertThat(allPostsByUser.getContent().get(0).getTitle(), is("제목1"));
        assertThat(allPostsByUser.getContent().get(1).getTitle(), is("제목2"));
        assertThrows(NullPointerException.class, () -> postService.findAllByUser(exceptionUserDto, pageable));
    }

    @Test
    @Order(3)
    @DisplayName("모든 post를 조회할 수 있다.")
    @Transactional
    void testFindAll() {
        // Given
        newUser2 = userFactory.createUser("최승은2", 26, "취미2");
        newPost2 = postFactory.createPost("제목2", "내용2", newUser1);
        newPost3 = postFactory.createPost("제목3", "내용3", newUser2);

        // When
        final Page<PostDto> allPostDto = postService.findAll(pageable);

        // Then
        assertThat(allPostDto.getContent().size(), is(3));
        assertThat(allPostDto.getContent().get(0).getTitle(), is("제목1"));
        assertThat(allPostDto.getContent().get(1).getTitle(), is("제목2"));
        assertThat(allPostDto.getContent().get(2).getTitle(), is("제목3"));
    }

    @Test
    @Order(4)
    @DisplayName("post를 수정할 수 있다.")
    @Transactional
    void testUpdate() {
        // Given
        final PostDto findedPostDto = postService.findById(newPost1.getPostId());

        // When
        findedPostDto.update("바뀐 제목1", "바뀐 내용1");
        postService.update(findedPostDto);

        // Then
        assertThat(postService.findById(newPost1.getPostId()).getTitle(), is("바뀐 제목1"));
    }

    @Test
    @Order(5)
    @DisplayName("client의 새로운 post 생성 요청을 처리할 수 있다.")
    @Transactional
    void testNewRequestDtoSave() {
        // Given
        final ResponseDto res = ResponseDto.builder()
                .title("제목 요청")
                .content("내용 요청")
                .userId(newUser1.getUserId())
                .build();

        // When
        postService.newPostSave(res);

        // Then
        assertThat(
                postService.findAllByUser(userConverter.toUserDto(newUser1), pageable)
                        .getContent()
                        .get(1)
                        .getTitle(),
                is("제목 요청"));
    }
}