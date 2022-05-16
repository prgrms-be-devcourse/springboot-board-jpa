package com.prgrms.springboard.post.service;

import static com.prgrms.springboard.TestUtils.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.prgrms.springboard.post.domain.Post;
import com.prgrms.springboard.post.domain.PostRepository;
import com.prgrms.springboard.post.domain.vo.Content;
import com.prgrms.springboard.post.domain.vo.Title;
import com.prgrms.springboard.post.dto.CreatePostRequest;
import com.prgrms.springboard.post.dto.ModifyPostRequest;
import com.prgrms.springboard.post.dto.PostResponse;
import com.prgrms.springboard.post.exception.PostNotFoundExcpetion;
import com.prgrms.springboard.post.exception.UserNotHavePermission;
import com.prgrms.springboard.user.domain.User;
import com.prgrms.springboard.user.domain.UserRepository;
import com.prgrms.springboard.user.domain.vo.Age;
import com.prgrms.springboard.user.domain.vo.Hobby;
import com.prgrms.springboard.user.domain.vo.Name;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @Mock
    private PostRepository postRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private PostService postService;

    @DisplayName("게시글을 게시한다.")
    @Test
    void post() {
        // given
        CreatePostRequest postRequest = new CreatePostRequest(1L, "제목입니다.", "내용입니다.");
        given(userRepository.findById(1L)).willReturn(Optional.of(createUser()));
        given(postRepository.save(any(Post.class))).willReturn(createPost());

        // when
        postService.createPost(postRequest);

        // then
        then(userRepository).should(times(1)).findById(1L);
        then(postRepository).should(times(1)).save(any(Post.class));
    }

    @DisplayName("게시글을 단건 조회한다.")
    @Test
    void findOne() {
        // given
        Long id = 1L;
        given(postRepository.findById(id)).willReturn(Optional.of(createPost()));

        // when
        PostResponse post = postService.findOne(id);

        // then
        assertThat(post.getId()).isEqualTo(id);
        then(postRepository).should(times(1)).findById(id);
    }

    @DisplayName("저장된 id가 아닐 경우 예외를 발생한다.")
    @Test
    void findOne_NotSavedId() {
        // given
        Long id = 10L;
        given(postRepository.findById(id)).willReturn(Optional.empty());

        // when
        // then
        assertThatThrownBy(() -> postService.findOne(id))
            .isInstanceOf(PostNotFoundExcpetion.class)
            .hasMessage("ID가 10인 게시글은 없습니다.");
    }

    @DisplayName("게시글을 페이징 조회한다.")
    @Test
    void findAll_Page() {
        // given
        Pageable pageable = PageRequest.of(0, 10);
        Page<Post> posts = new PageImpl<>(createPosts());
        given(postRepository.findAll(pageable)).willReturn(posts);

        // when
        Page<PostResponse> postResponses = postService.findAll(pageable);

        // then
        assertThat(postResponses).hasSize(2)
            .extracting("id")
            .containsExactly(1L, 2L);
    }

    @DisplayName("게시글을 수정한다.")
    @Test
    void modifyPost() {
        // given
        Long postId = 1L;
        ModifyPostRequest postRequest = new ModifyPostRequest(1L, "제목", "내용");
        given(postRepository.findById(postId)).willReturn(Optional.of(createPost()));
        given(userRepository.findById(postRequest.getUserId())).willReturn(Optional.of(createUser()));

        // when
        postService.modifyPost(postId, postRequest);

        // then
        then(postRepository).should(times(1)).findById(1L);
        then(userRepository).should(times(1)).findById(1L);
    }

    @DisplayName("해당 글을 쓴 회원이 아니라면 수정 시 예외가 발생한다.")
    @Test
    void modifyPost_NotWrittenUser() {
        // given
        Long postId = 1L;
        ModifyPostRequest postRequest = new ModifyPostRequest(3L, "제목", "내용");
        given(postRepository.findById(postId)).willReturn(Optional.of(createPost()));
        given(userRepository.findById(postRequest.getUserId())).willReturn(Optional.of(createOtherUser()));

        // when
        // then
        assertThatThrownBy(() -> postService.modifyPost(postId, postRequest))
            .isInstanceOf(UserNotHavePermission.class)
            .hasMessage("ID가 3인 회원은 해당 글을 수정할 권한이 없습니다.");

    }

    private User createOtherUser() {
        return new User("아만드", LocalDateTime.now(), 3L, new Name("아만드"), new Age(26), new Hobby("카페"));
    }

    private Post createPost() {
        return new Post("유민환", LocalDateTime.now(), 1L, new Title("제목입니다."), new Content("내용입니다."), createUser());
    }

    private List<Post> createPosts() {
        return List.of(
            new Post("유민환", LocalDateTime.now(), 1L, new Title("제목입니다."), new Content("내용입니다."), createUser()),
            new Post("유민환", LocalDateTime.now(), 2L, new Title("제목2입니다."), new Content("내용2입니다."), createUser())
        );
    }

}
