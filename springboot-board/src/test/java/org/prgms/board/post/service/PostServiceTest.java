package org.prgms.board.post.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.prgms.board.common.exception.NotFoundException;
import org.prgms.board.domain.entity.Comment;
import org.prgms.board.domain.entity.Post;
import org.prgms.board.domain.entity.User;
import org.prgms.board.domain.repository.PostRepository;
import org.prgms.board.domain.repository.UserRepository;
import org.prgms.board.post.dto.PostRequest;
import org.prgms.board.post.dto.PostResponse;
import org.prgms.board.user.dto.UserIdRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {
    @InjectMocks
    private PostService postService;

    @Mock
    private PostRepository postRepository;

    @Mock
    private UserRepository userRepository;

    private User user;
    private Post post;

    @BeforeEach
    void setUp() {
        user = User.builder()
            .id(1L)
            .name("buhee")
            .age(26)
            .hobby("making")
            .build();

        post = Post.builder()
            .id(1L)
            .title("title")
            .content("content")
            .writer(user)
            .build();
    }

    @DisplayName("게시글 등록 확인")
    @Test
    void addPostTest() {
        PostRequest postRequest = new PostRequest(1L, "title", "content");
        when(userRepository.findByIdAndDeleted(anyLong(), anyBoolean())).thenReturn(Optional.of(user));
        when(postRepository.save(any())).thenReturn(post);

        Long postId = postService.addPost(postRequest);

        assertThat(postId).isEqualTo(post.getId());
    }

    @DisplayName("게시글 수정 확인")
    @Test
    void modifyPostTest() {
        PostRequest postRequest = new PostRequest(1L, "title2", "content2");
        when(userRepository.findByIdAndDeleted(anyLong(), anyBoolean())).thenReturn(Optional.of(user));
        when(postRepository.findById(anyLong())).thenReturn(Optional.of(post));

        postService.modifyPost(post.getId(), postRequest);

        PostResponse retrievedPost = postService.getOnePost(post.getId());
        assertThat(retrievedPost.getTitle()).isEqualTo(postRequest.getTitle());
        assertThat(retrievedPost.getContent()).isEqualTo(postRequest.getContent());
        assertThat(retrievedPost.getAuthor()).isEqualTo(postRequest.getUserId());
    }

    @DisplayName("게시글 삭제 확인")
    @Test
    void removePostTest() {
        UserIdRequest userIdRequest = new UserIdRequest(1L);
        when(userRepository.findByIdAndDeleted(anyLong(), anyBoolean())).thenReturn(Optional.of(user));
        when(postRepository.findById(anyLong())).thenReturn(Optional.of(post));
        when(postRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> postService.removePost(post.getId(), userIdRequest))
            .isInstanceOf(NotFoundException.class)
            .hasMessageContaining("해당 게시글을 찾을 수 없습니다.");
    }

    @DisplayName("특정 사용자의 모든 게시글 조회 확인")
    @Test
    void getAllPostByUserTest() {
        UserIdRequest userIdRequest = new UserIdRequest(1L);
        List<Post> posts = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            posts.add(post);
        }
        Page page = new PageImpl(posts);
        when(userRepository.findByIdAndDeleted(anyLong(), anyBoolean())).thenReturn(Optional.of(user));
        when(postRepository.findAllByWriter(any(Pageable.class), any())).thenReturn(page);

        Pageable pageable = PageRequest.of(0, 2);
        Page<PostResponse> postResponses = postService.getAllPostByUser(pageable, userIdRequest);

        assertThat(postResponses.getTotalPages()).isEqualTo(page.getTotalPages());
        assertThat(postResponses.getTotalElements()).isEqualTo(page.getTotalElements());
    }

    @DisplayName("게시글 상세 조회 확인")
    @Test
    void getOnePostTest() {
        Comment comment1 = Comment.builder()
            .id(1L)
            .content("comment1")
            .post(post)
            .writer(user)
            .build();
        post.addComment(comment1);

        Comment comment2 = Comment.builder()
            .id(2L)
            .content("comment2")
            .post(post)
            .writer(user)
            .build();
        post.addComment(comment2);

        when(postRepository.findById(anyLong())).thenReturn(Optional.of(post));

        PostResponse retrievedPost = postService.getOnePost(post.getId());

        assertThat(retrievedPost.getTitle()).isEqualTo("title");
        assertThat(retrievedPost.getContent()).isEqualTo("content");
        assertThat(retrievedPost.getAuthor()).isEqualTo(1L);
        assertThat(retrievedPost.getComments()).hasSize(2);
        assertThat(retrievedPost.getComments().get(0).getContent()).isEqualTo("comment1");
    }
}