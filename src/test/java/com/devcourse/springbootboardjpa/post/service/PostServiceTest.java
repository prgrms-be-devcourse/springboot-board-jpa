package com.devcourse.springbootboardjpa.post.service;

import com.devcourse.springbootboardjpa.common.dto.page.PageDTO;
import com.devcourse.springbootboardjpa.common.exception.post.PostNotFoundException;
import com.devcourse.springbootboardjpa.common.exception.user.UserNotFoundException;
import com.devcourse.springbootboardjpa.post.domain.Post;
import com.devcourse.springbootboardjpa.post.domain.dto.PostDTO;
import com.devcourse.springbootboardjpa.post.repository.UserRepository;
import com.devcourse.springbootboardjpa.user.domain.User;
import com.devcourse.springbootboardjpa.user.repository.PostRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @InjectMocks
    PostService postService;

    @Mock
    PostRepository postRepository;

    @Mock
    UserRepository userRepository;

    @Test
    @DisplayName("게시글을 저장 성공")
    void shouldSavePost() {
        // given
        User user = createUser(1L , "name", 10, "hobby");
        PostDTO.SaveRequest postSaveRequest = PostDTO.SaveRequest.builder()
                .title("title")
                .content("content")
                .userId(user.getId())
                .build();
        Post post = createPost(1L, postSaveRequest.getTitle(), postSaveRequest.getContent(), user);

        when(userRepository.findById(user.getId()))
                .thenReturn(Optional.of(user));
        when(postRepository.save(any(Post.class)))
                .thenReturn(post);

        // when
        Long savedPostId = postService.savePost(postSaveRequest);

        // then
        assertThat(savedPostId).isEqualTo(post.getId());
    }

    @Test
    @DisplayName("게시물 저장 시 회원이 존재하지 않는 경우 예외 처리")
    void savePostWhenUserNotFound() {
        // given
        User user = createUser(1L , "name", 10, "hobby");
        PostDTO.SaveRequest postSaveRequest = PostDTO.SaveRequest.builder()
                .title("title")
                .content("content")
                .userId(user.getId())
                .build();
        Post post = createPost(1L, postSaveRequest.getTitle(), postSaveRequest.getContent(), user);

        when(userRepository.findById(user.getId()))
                .thenThrow(UserNotFoundException.class);

        // when. then
        assertThrows(UserNotFoundException.class,
                () -> postService.savePost(postSaveRequest));
    }

    @Test
    @DisplayName("게시물 단건 조회 성공")
    void shouldFindPost() {
        // given
        User user = createUser(1L , "name", 10, "hobby");
        PostDTO.SaveRequest postSaveRequest = PostDTO.SaveRequest.builder()
                .title("title")
                .content("content")
                .userId(user.getId())
                .build();
        Post post = createPost(1L, postSaveRequest.getTitle(), postSaveRequest.getContent(), user);

        when(postRepository.findById(post.getId()))
                .thenReturn(Optional.of(post));

        // when
        PostDTO.FindResponse findPostResponse = postService.findPost(post.getId());

        // then
        assertThat(post.getId()).isEqualTo(findPostResponse.getId());
        assertThat(post.getTitle()).isEqualTo(findPostResponse.getTitle());
        assertThat(post.getContent()).isEqualTo(findPostResponse.getContent());
        assertThat(user.getId()).isEqualTo(findPostResponse.getUserId());
        assertThat(user.getName()).isEqualTo(findPostResponse.getUserName());
    }

    @Test
    @DisplayName("게시물 단건 조회시 게시물이 존재하지 않는 경우 에러")
    void findPostWhenPostNotFound() {
        // given
        User user = createUser(1L , "name", 10, "hobby");
        PostDTO.SaveRequest postSaveRequest = PostDTO.SaveRequest.builder()
                .title("title")
                .content("content")
                .userId(user.getId())
                .build();
        Post post = createPost(1L, postSaveRequest.getTitle(), postSaveRequest.getContent(), user);

        when(postRepository.findById(post.getId()))
                .thenThrow(PostNotFoundException.class);

        // when, then
        assertThrows(PostNotFoundException.class, () -> postService.findPost(post.getId()));
    }

    @Test
    @DisplayName("게시물 페이징 조회 성공")
    void shouldFindPosts() {
        // given
        User user = createUser(1L , "name", 10, "hobby");
        PageDTO.Request request = new PageDTO.Request(0, 5);
        List<Post> postList = new ArrayList<>();
        for (long i = 1; i <= 5; i++) {
            postList.add(new Post(i, "title " + i, "content " + i, user));
        }
        Page<Post> postPage = new PageImpl<>(postList);

        when(postRepository.findAll(any(Pageable.class)))
                .thenReturn(postPage);

        // when
        PageDTO.Response<Post, PostDTO.FindResponse> result = postService.findAllPostsPage(request);

        // then
        assertThat(result.getData())
                .extracting("id", "title", "content", "userId", "userName")
                .contains(tuple(postList.get(0).getId(), postList.get(0).getTitle(), postList.get(0).getContent(), postList.get(0).getUser().getId(), postList.get(0).getUser().getName()))
                .contains(tuple(postList.get(1).getId(), postList.get(1).getTitle(), postList.get(1).getContent(), postList.get(1).getUser().getId(), postList.get(1).getUser().getName()))
                .contains(tuple(postList.get(2).getId(), postList.get(2).getTitle(), postList.get(2).getContent(), postList.get(2).getUser().getId(), postList.get(2).getUser().getName()))
                .contains(tuple(postList.get(3).getId(), postList.get(3).getTitle(), postList.get(3).getContent(), postList.get(3).getUser().getId(), postList.get(3).getUser().getName()))
                .contains(tuple(postList.get(4).getId(), postList.get(4).getTitle(), postList.get(4).getContent(), postList.get(4).getUser().getId(), postList.get(4).getUser().getName()));
    }

    @Test
    @DisplayName("게시글 수정 성공")
    void shouldUpdatePost() {
        // given
        User user = createUser(1L , "name", 10, "hobby");
        PostDTO.SaveRequest postSaveRequest = PostDTO.SaveRequest.builder()
                .title("title")
                .content("content")
                .userId(user.getId())
                .build();
        Post post = createPost(1L, postSaveRequest.getTitle(), postSaveRequest.getContent(), user);
        PostDTO.UpdateRequest updatePostRequest = PostDTO.UpdateRequest.builder()
                .title("change title")
                .content("change content")
                .build();

        when(postRepository.findById(post.getId()))
                .thenReturn(Optional.of(post));

        // when
        Long updatedPostId = postService.updatePost(post.getId(), updatePostRequest);

        // then
        assertThat(post.getId()).isEqualTo(updatedPostId);
    }

    private User createUser(Long id, String name, int age, String hobby) {
        return User.builder()
                .id(id)
                .name(name)
                .age(age)
                .hobby(hobby)
                .build();
    }

    private Post createPost(Long id, String title, String content, User user) {
        return Post.builder()
                .id(id)
                .title(title)
                .content(content)
                .user(user)
                .build();
    }
}