package com.prgrms.board.service;

import com.prgrms.board.domain.Post;
import com.prgrms.board.domain.Users;
import com.prgrms.board.dto.post.PostResponse;
import com.prgrms.board.dto.post.PostSaveRequest;
import com.prgrms.board.dto.post.PostUpdateRequest;
import com.prgrms.board.dto.post.SimplePostResponse;
import com.prgrms.board.repository.PostRepository;
import com.prgrms.board.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class PostServiceTest {

    private UserRepository userRepository;
    private PostRepository postRepository;
    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = factory.getValidator();
    private PostService postService;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        postRepository = mock(PostRepository.class);
        postService = new PostService(postRepository, userRepository);
    }

    @Test
    @DisplayName("게시글 작성 테스트")
    void createPost() {
        // Given
        Users user = new Users("lee@test.com","lee", 10);
        PostSaveRequest saveRequest = new PostSaveRequest(1L, "Test Title", "Test Content"); // id는 무시
        when(userRepository.findById(1L)).thenReturn(Optional.of(user)); // findById 메서드에 대한 Mock 설정
        when(postRepository.save(any(Post.class))).thenReturn(
                new Post(user, saveRequest.getTitle(), saveRequest.getContent())
        );
        // When
        postService.createPost(saveRequest);
        // Then
        verify(userRepository, times(1)).findById(1L);
        verify(postRepository, times(1)).save(any(Post.class));
    }

    @Test
    @DisplayName("게시글 페이징 조회 테스트")
    void getPagingPosts() {
        // Given
        int page = 0;
        Users user1 = new Users("lee@test.com", "User 1", 10);
        Users user2 = new Users("lee@test.com", "User 2", 10);
        List<Post> postList = List.of(
                new Post(user1, "Post 1", "Content 1"),
                new Post(user2, "Post 2", "Content 2")
        );
        Page<Post> pageResult = new PageImpl<>(postList);
        when(postRepository.findAllWithUser(any(Pageable.class))).thenReturn(pageResult);
        // When
        List<SimplePostResponse> postResponses = postService.findPostsByCriteria(page, null);
        // Then
        assertThat(postResponses).hasSize(postList.size());
    }

    @Test
    @DisplayName("게시글 단건 조회 테스트")
    void findPostById() {
        // Given
        long postId = 1L;
        Post post = new Post(new Users("lee@test.com",  "User 1", 10), "Test Post", "Test Content");
        when(postRepository.findById(postId)).thenReturn(Optional.of(post));
        // When
        PostResponse postResponse = postService.findPostById(postId);
        // Then
        assertThat(postResponse).isNotNull();
        assertThat(postResponse.getTitle()).isEqualTo(post.getTitle());
        assertThat(postResponse.getContent()).isEqualTo(post.getContent());
    }

    @Test
    @DisplayName("게시글 수정 테스트")
    void updatePost() {
        // Given
        long postId = 1L;
        Users savedUser = new Users("lee@test.com", "lee", 10);
        Post savedPost = new Post(savedUser, "Test Post", "Test Content");
        PostUpdateRequest updateRequest = new PostUpdateRequest(
                1L, savedPost.getTitle(), savedPost.getContent()
        );
        when(postRepository.findById(postId)).thenReturn(Optional.of(savedPost));
        // When
        PostResponse updatedPostResponse = postService.updatePost(updateRequest, postId);
        // Then
        assertThat(updatedPostResponse).isNotNull();
        assertThat(updatedPostResponse.getTitle()).isEqualTo(updateRequest.getTitle());
        assertThat(updatedPostResponse.getContent()).isEqualTo(updateRequest.getContent());
    }

    @Test
    @DisplayName("게시글 삭제 테스트")
    void deletePost() {
        // Given
        long postId = 1L;
        // When
        postService.deletePostById(postId);
        // Then
        verify(postRepository, times(1)).deleteById(postId);
    }



    // DTO 유효성 검사 //
    @DisplayName("유효한 PostSaveRequest 객체 ")
    @Test
    void createValidPostSaveRequest() {
        // Given
        Long userId = 1L;
        String title = "테스트 제목";
        String content = "테스트 내용";
        // When
        PostSaveRequest request = new PostSaveRequest(userId, title, content);
        // Then
        assertThat(request).isNotNull();
        Set<ConstraintViolation<PostSaveRequest>> violations = validator.validate(request);
        assertThat(violations).isEmpty();
    }

    @DisplayName("유효하지 않은 PostSaveRequest: null 값")
    @Test
    void nullRequestPostSaveRequest() {
        // Given
        Long userId = null;
        String title = null;
        String content = null;
        // When
        PostSaveRequest request = new PostSaveRequest(userId, title, content);
        // Then
        assertThat(request).isNotNull();
        assertThat(request.getUserId()).isNull();
        assertThat(request.getTitle()).isNull();
        assertThat(request.getContent()).isNull();
        Set<ConstraintViolation<PostSaveRequest>> violations = validator.validate(request);
        assertThat(violations).hasSize(3);
    }

    @DisplayName("유효하지 않은 PostSaveRequest: 빈 값")
    @Test
    void emptyRequestPostSaveRequest() {
        // Given
        Long userId = 0L;
        String title = "";
        String content = "";
        // When
        PostSaveRequest request = new PostSaveRequest(userId, title, content);
        assertThat(request).isNotNull();
        // Then
        Set<ConstraintViolation<PostSaveRequest>> violations = validator.validate(request);
        assertThat(violations).hasSize(3);
    }
}