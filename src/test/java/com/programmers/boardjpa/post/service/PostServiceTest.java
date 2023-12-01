package com.programmers.boardjpa.post.service;

import com.programmers.boardjpa.post.dto.PostInsertRequestDto;
import com.programmers.boardjpa.post.dto.PostResponseDto;
import com.programmers.boardjpa.post.dto.PostUpdateRequestDto;
import com.programmers.boardjpa.post.entity.Post;
import com.programmers.boardjpa.post.exception.PostErrorCode;
import com.programmers.boardjpa.post.exception.PostException;
import com.programmers.boardjpa.post.repository.PostRepository;
import com.programmers.boardjpa.user.entity.User;
import com.programmers.boardjpa.user.exception.UserErrorCode;
import com.programmers.boardjpa.user.exception.UserException;
import com.programmers.boardjpa.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PostServiceTest {
    @Mock
    private PostRepository postRepository;
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private PostService postService;
    private final Long postId = 1L;

    @Test
    @DisplayName("DB에서 특정 Post를 가져올 수 있다.")
    void getAPostInDB() throws Exception {
        // given
        User user = User.builder()
                .name("user")
                .hobby("GAME")
                .age(10)
                .build();

        Post post = Post.builder().title("title").content("content").user(user).build();

        var idField = post.getClass().getDeclaredField("id");
        idField.setAccessible(true);
        idField.set(post, postId);

        when(postRepository.findById(postId))
                .thenReturn(Optional.of(post));

        // when
        PostResponseDto retrievedPost = postService.getPost(postId);

        // then
        assertThat(retrievedPost.title()).isEqualTo("title");
        assertThat(retrievedPost.content()).isEqualTo("content");

        verify(postRepository).findById(postId);
    }

    @Test
    @DisplayName("새로운 Post를 생성하여 DB에 넣어줄 수 있다.")
    void insertAPostToDB() throws Exception {
        // given
        long userId = 1L;

        PostInsertRequestDto insertRequestDto = new PostInsertRequestDto("title", "content", userId);
        Post post = Post.builder()
                        .title("title")
                        .content("content")
                        .user(new User("hyogu", 29, "sleep"))
                        .build();

        when(postRepository.save(any(Post.class)))
                .thenReturn(post);
        when(userRepository.findById(userId))
                .thenReturn(Optional.of(new User("hyogu", 29, "sleep")));

        // when
        PostResponseDto retrievedPost = postService.insertPost(insertRequestDto);

        // then
        assertThat(retrievedPost.title()).isEqualTo("title");
        assertThat(retrievedPost.content()).isEqualTo("content");

        verify(postRepository).save(any(Post.class));
    }

    @Test
    @DisplayName("DB 내 특정 Post를 업데이트 할 수 있다.")
    void updateAPostInDB() {
        // given
        long postId = 1L;

        var updateRequestDto = new PostUpdateRequestDto("ogu", "ori neoguri");

        when(postRepository.findById(postId))
                .thenReturn(Optional.of(new Post("ogu", "ori neoguri", new User("hyogu", 10, "sleep"))));

        // when
        var postResponseDto = postService.updatePost(postId, updateRequestDto);

        // then
        assertThat(postResponseDto.title()).isEqualTo("ogu");
        assertThat(postResponseDto.content()).isEqualTo("ori neoguri");

        verify(postRepository).findById(postId);
    }

    @Test
    @DisplayName("DB에서 모든 Posts를 가져올 수 있다.")
    void getAllPostsInDB() {
        PageRequest pageRequest = PageRequest.of(1, 10);

        when(postRepository.findAllWithUser(pageRequest))
                .thenReturn(new PageImpl<>(List.of(new Post("ogu", "oguogu", new User("oogu", 10, "sleep")))));

        var pageResponseDto = postService.getPosts(pageRequest);

        assertThat(pageResponseDto.data()).hasSize(1);

        verify(postRepository).findAllWithUser(pageRequest);

    }

    @Test
    @DisplayName("존재하지 않는 Post에 대한 게시글 조회는 예외를 발생시킨다.")
    void getAPostInDBThrowPostNotFoundException() throws Exception {
        // given
        long newPostId = 2L;

        when(postRepository.findById(newPostId))
                .thenThrow(new PostException(PostErrorCode.POST_NOT_FOUND, newPostId));

        // when - then
        assertThatThrownBy(() -> postService.getPost(newPostId))
                .isInstanceOf(PostException.class)
                .hasMessageContaining("해당하는 Post를 찾을 수 없습니다. : " + newPostId);
    }

    @Test
    @DisplayName("존재하지 않는 User에 대한 게시글 작성은 예외를 발생시킨다.")
    void insertAPostToDBThrowUserNotFoundException() throws Exception {
        // given
        long userId = 1L;

        PostInsertRequestDto insertRequestDto = new PostInsertRequestDto("title", "content", userId);

        when(userRepository.findById(userId))
                .thenThrow(new UserException(UserErrorCode.USER_NOT_FOUND, userId));
        
        // when - then
        assertThatThrownBy(() -> postService.insertPost(insertRequestDto))
                .isInstanceOf(UserException.class)
                .hasMessageContaining("해당하는 User를 찾을 수 없습니다. : " + userId);
    }

    @Test
    @DisplayName("존재하지 않는 Post를 업데이트할 때 예외가 발생한다.")
    void updateAPostInDBThrowPostNotFoundException() throws Exception {
        // given
        long postId = 1L;

        when(postRepository.findById(postId))
                        .thenThrow(new PostException(PostErrorCode.POST_NOT_FOUND, postId));

        // when - then
        assertThatThrownBy(() -> postService.getPost(postId))
                .isInstanceOf(PostException.class)
                .hasMessageContaining("해당하는 Post를 찾을 수 없습니다. : " + postId);

        verify(postRepository).findById(postId);
    }
}
