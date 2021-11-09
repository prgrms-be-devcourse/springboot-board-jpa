//package com.example.boardbackend.service;
//
//import com.example.boardbackend.common.converter.DtoConverter;
//import com.example.boardbackend.common.error.exception.NotFoundException;
//import com.example.boardbackend.dto.PostDto;
//import com.example.boardbackend.dto.UserDto;
//import com.example.boardbackend.repository.PostRepository;
//
//import com.example.boardbackend.repository.UserRepository;
//import org.junit.jupiter.api.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Sort;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
//
//import static org.hamcrest.MatcherAssert.assertThat;
//import static org.hamcrest.Matchers.hasSize;
//import static org.hamcrest.Matchers.is;
//import static org.junit.jupiter.api.Assertions.*;
//
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//@Transactional
//@SpringBootTest
//class PostServiceTest {
//
//    @Autowired
//    PostService postService;
//    @Autowired
//    UserService userService;
//    @Autowired
//    PostRepository postRepository;
//    @Autowired
//    UserRepository userRepository;
//    @Autowired
//    DtoConverter dtoConverter;
//
//    Long userId;
//
//    private PostDto createPost(String title, String content) {
//        UserDto userDto = UserDto.builder()
//                .id(userId)
//                .email("test@mail.com")
//                .password("1234")
//                .name("test")
//                .age(20)
//                .hobby("코딩")
//                .build();
//        PostDto postDto = PostDto.builder()
//                .title(title)
//                .content(content)
//                .userDto(userDto)
//                .build();
//        return postService.savePost(postDto);
//    }
//
//    @BeforeAll
//    void setUp(){
//        // 유저를 하나 생성해놓고 시작 (테스트동안 불변)
//        UserDto userDto = UserDto.builder()
//                .email("test@mail.com")
//                .password("1234")
//                .name("test")
//                .age(20)
//                .hobby("코딩")
//                .build();
//        userId = userService.saveUser(userDto).getId();
//    }
//
//    @AfterEach
//    void tearDown() {
//        postRepository.deleteAll();
//    }
//
//    @AfterAll
//    void cleanUp(){
//        userRepository.deleteAll();
//    }
//
////    ------------------------------------------------------------------------------------
//
//    @Test
//    @DisplayName("게시글을 저장할 수 있다")
//    void savePost_test() {
//        // Given
//        createPost("1", "1");
//        UserDto userDto = UserDto.builder()
//                .id(userId)
//                .email("test@mail.com")
//                .password("1234")
//                .name("test")
//                .age(20)
//                .hobby("코딩")
//                .build();
//        PostDto newPostDto = PostDto.builder()
//                .title("2")
//                .content("2")
//                .userDto(userDto)
//                .build();
//
//        // When
//        PostDto postDto = postService.savePost(newPostDto);
//
//        // Then
//        long count = postRepository.count();
//        assertThat(count, is(2L));
//    }
//
//    @Test
//    @DisplayName("모든 포스팅을 size=10, id를 기준으로 내림차순하여 조회할 수 있다.")
//    void findPostsAll_test() {
//        // Given
//        List<Long> postId = new ArrayList<>();
//        for(int i = 0; i < 10; i++){
//            postId.add(createPost(String.valueOf(i), String.valueOf(i)).getId());
//        }
//        PageRequest pageable = PageRequest.of(0, 10, Sort.by("id").descending());
//
//        // When
//        Page<PostDto> postsAll = postService.findPostsAll(pageable);
//
//        // Then
//        Collections.reverse(postId);
//        List<PostDto> postsAllContent = postsAll.getContent();
//        assertThat(postsAllContent, hasSize(10));
//        for(int i = 0; i < 10; i++){
//            assertThat(postsAllContent.get(i).getId(), is(postId.get(i)));
//        }
//    }
//
//    @Test
//    @DisplayName("모든 게시물의 수를 조회할 수 있다.")
//    void countPostsAll_test() {
//        // Given
//        int postNum = 5;
//        for(int i = 0; i < postNum; i++){
//            createPost(String.valueOf(i), String.valueOf(i));
//        }
//
//        // When
//        Long count = postService.countPostsAll();
//
//        // Then
//        assertThat(count, is((long)postNum));
//    }
//
//    @Test
//    @DisplayName("해당 userId의 사용자가 생성한 모든 게시물을 조회할 수 있다.")
//    void findPostsByUserId_test() {
//        // Given
//        PostDto postDto = createPost("1", "1");
//        Long userId = postDto.getUserDto().getId();
//
//        // When
//        List<PostDto> postsByUserId = postService.findPostsByUserId(userId);
//
//        // Then
//        assertThat(postsByUserId, hasSize(1));
//        assertThat(postsByUserId.get(0).getUserDto().getId(), is(userId));
//        assertThat(postsByUserId.get(0).getId(), is(postDto.getId()));
//    }
//
//    @Test
//    @DisplayName("postId로 게시물을 조회할 수 있다.")
//    void findPostById_test() {
//        // Given
//        PostDto postDto = createPost("1", "1");
//        Long postId = postDto.getId();
//
//        // When
//        PostDto postById = postService.findPostById(postId);
//
//        // Then
//        assertThat(postById.getId(), is(postId));
//        // 그냥 한번 다 맞춰보기
//        assertThat(postById.getTitle(), is(postDto.getTitle()));
//        assertThat(postById.getContent(), is(postDto.getContent()));
//        assertThat(postById.getCreatedAt(), is(postDto.getCreatedAt()));
//    }
//
//    @Test
//    @DisplayName("postId로 게시물의 제목 및 내용을 수정할 수 있다.")
//    void updatePostById_test() {
//        // Given
//        PostDto postDto = createPost("1", "1");
//        Long postId = postDto.getId();
//        String newTitle = "new";
//        String newContent = "new";
//
//        // When
//        PostDto updatedPost = postService.updatePostById(postId, newTitle, newContent);
//
//        // Then
//        assertThat(updatedPost.getId(), is(postId));
//        assertThat(updatedPost.getTitle(), is(newTitle));
//        assertThat(updatedPost.getContent(), is(newContent));
//    }
//
//    @Test
//    @DisplayName("postId로 게시물의 조회수를 수정할 수 있다.")
//    void updateViewById_test() {
//        // Given
//        PostDto postDto = createPost("1", "1");
//        Long postId = postDto.getId();
//        Long newView = 1L;
//
//        // When
//        Long updatedView = postService.updateViewById(postId, newView);
//
//        // Then
//        assertThat(updatedView, is(newView));
//    }
//
//    @Test
//    void deletePostById_test() {
//        // Given
//        PostDto postDto = createPost("1", "1");
//        Long postId = postDto.getId();
//
//        // When
//        postService.deletePostById(postId);
//
//        // Then
//        assertThat(postRepository.findAll(), hasSize(0));
//        assertThrows(NotFoundException.class, () -> postService.findPostById(postId));
//    }
//}