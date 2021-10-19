package org.prgms.board.post.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.prgms.board.common.exception.NotFoundException;
import org.prgms.board.common.exception.NotMatchException;
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
    private static final Long USERID = 1L;
    private static final Long NOT_MATCH_USERID = 2L;
    private static final String REQUEST_TITLE = "제목";
    private static final String REQUEST_CONTENT = "내용";
    private static final String UPDATE_TITLE = "제목 수정";
    private static final String UPDATE_CONTENT = "내용 수정";

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
            .name("김부희")
            .age(26)
            .hobby("만들기")
            .build();

        post = Post.builder()
            .id(1L)
            .title("제목")
            .content("내용")
            .writer(user)
            .build();
    }

    @DisplayName("게시글 등록 확인")
    @Test
    void addPostTest() {
        PostRequest postRequest = new PostRequest(USERID, REQUEST_TITLE, REQUEST_CONTENT);
        when(userRepository.findByIdAndDeleted(anyLong(), anyBoolean())).thenReturn(Optional.of(user));
        when(postRepository.save(any())).thenReturn(post);

        Long postId = postService.writePost(postRequest);

        assertThat(postId).isEqualTo(post.getId());
    }

    @DisplayName("게시글 수정 확인")
    @Test
    void modifyPostTest() {
        PostRequest postRequest = new PostRequest(USERID, UPDATE_TITLE, UPDATE_CONTENT);
        when(userRepository.findByIdAndDeleted(anyLong(), anyBoolean())).thenReturn(Optional.of(user));
        when(postRepository.findById(anyLong())).thenReturn(Optional.of(post));

        postService.modifyPost(post.getId(), postRequest);

        PostResponse retrievedPost = postService.getPost(post.getId());
        assertThat(retrievedPost.getTitle()).isEqualTo(postRequest.getTitle());
        assertThat(retrievedPost.getContent()).isEqualTo(postRequest.getContent());
        assertThat(retrievedPost.getAuthor()).isEqualTo(postRequest.getUserId());
    }

    @DisplayName("게시글 삭제 확인")
    @Test
    void removePostTest() {
        when(userRepository.findByIdAndDeleted(anyLong(), anyBoolean())).thenReturn(Optional.of(user));
        when(postRepository.findById(anyLong())).thenReturn(Optional.of(post));

        postService.removePost(post.getId(), new UserIdRequest(USERID));

        when(postRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThatThrownBy(() -> postService.getPost(post.getId()))
            .isInstanceOf(NotFoundException.class);
    }

    @DisplayName("게시글 작성자가 아닐 때 삭제하려고 한 경우 예외처리 확인")
    @Test
    void notMatchWriterAndUser() {
        User notMatchUser = User.builder()
            .id(2L)
            .name("김부희")
            .age(26)
            .hobby("만들기")
            .build();

        when(userRepository.findByIdAndDeleted(anyLong(), anyBoolean())).thenReturn(Optional.of(notMatchUser));
        when(postRepository.findById(anyLong())).thenReturn(Optional.of(post));

        assertThatThrownBy(() -> postService.removePost(post.getId(), new UserIdRequest(NOT_MATCH_USERID)))
            .isInstanceOf(NotMatchException.class);
    }

    @DisplayName("특정 사용자의 모든 게시글 조회 확인")
    @Test
    void getAllPostByUserTest() {
        int cnt = 2;
        List<Post> posts = new ArrayList<>();
        for (int i = 0; i < cnt; i++) {
            posts.add(post);
        }
        Page page = new PageImpl(posts);
        when(userRepository.findByIdAndDeleted(anyLong(), anyBoolean())).thenReturn(Optional.of(user));
        when(postRepository.findAllByWriter(any(Pageable.class), any())).thenReturn(page);

        Pageable pageable = PageRequest.of(0, cnt);
        Page<PostResponse> postResponses = postService.getPostsByUser(pageable, USERID);

        assertThat(postResponses.getTotalPages()).isEqualTo(page.getTotalPages());
        assertThat(postResponses.getTotalElements()).isEqualTo(page.getTotalElements());
    }

    @DisplayName("게시글 상세 조회 확인")
    @Test
    void getOnePostTest() {
        Comment comment1 = Comment.builder()
            .id(1L)
            .content("댓글1")
            .post(post)
            .writer(user)
            .build();
        post.addComment(comment1);

        Comment comment2 = Comment.builder()
            .id(2L)
            .content("댓글2")
            .post(post)
            .writer(user)
            .build();
        post.addComment(comment2);

        when(postRepository.findById(anyLong())).thenReturn(Optional.of(post));

        PostResponse retrievedPost = postService.getPost(post.getId());

        assertThat(retrievedPost.getTitle()).isEqualTo(post.getTitle());
        assertThat(retrievedPost.getContent()).isEqualTo(post.getContent());
        assertThat(retrievedPost.getAuthor()).isEqualTo(post.getWriter().getId());
        assertThat(retrievedPost.getComments()).hasSize(2);
    }
}