package com.example.board.service;

import com.example.board.domain.Post;
import com.example.board.domain.User;
import com.example.board.dto.request.post.*;
import com.example.board.dto.response.ResponseStatus;
import com.example.board.exception.CustomException;
import com.example.board.repository.post.PostRepository;
import com.example.board.repository.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class PostServiceTest {

    private final UserRepository userRepository;
    private final PostRepository postRepository;

    private final UserService userService;
    private final PostService postService;

    public PostServiceTest() {
        this.userRepository = mock(UserRepository.class);
        this.postRepository = mock(PostRepository.class);
        this.userService = mock(UserService.class);
        this.postService = new PostService(postRepository, userService);
    }

    @Test
    void 게시글을_생성한다() {
        // given
        User author = generateAuthor();
        Post post = generatePost();
        CreatePostRequest requestDto = new CreatePostRequest("마라톤 꿀팁", "매일매일 달리세요", author.getId());
        given(userService.getAvailableUser(eq(author.getId()))).willReturn(author);
        given(postRepository.save(any(Post.class))).willReturn(post);

        // when
        postService.createPost(requestDto);

        // then
        verify(postRepository).save(any(Post.class));
    }

    @Test
    void 게시글_목록을_조회할_수_있다() {
        // given
        PageCondition pageCondition = new PageCondition(1, 1);
        Pageable pageable = PageRequest.of(pageCondition.getPage() - 1, pageCondition.getSize());
        PostSearchCondition condition = new PostSearchCondition(null, null, null);
        given(postRepository.findAll(eq(condition), eq(pageable))).willReturn(new PageImpl<>(Collections.emptyList(), pageable, 0));

        // when
        postService.getPosts(condition, pageCondition);

        // then
        verify(postRepository).findAll(eq(condition), eq(pageable));
    }

    @Test
    void 게시글을_상세_조회_할_수_있다() {
        // given
        Post post = generatePost();
        given(postRepository.findById(eq(post.getId()))).willReturn(Optional.ofNullable(post));

        // when
        postService.getPost(post.getId());

        // then
        verify(postRepository).findById(eq(post.getId()));
    }


    @Test
    void 게시글을_수정한다() {
        // given
        Post post = generatePost();
        Post mockPost = mock(Post.class);
        UpdatePostRequest requestDto = new UpdatePostRequest("마라톤 꿀팁", "매일매일 달리고 러닝화도 사세요", post.getAuthor().getId());
        given(postRepository.findByIdWithAuthor(any(Long.class))).willReturn(Optional.ofNullable(mockPost));
        given(mockPost.isSameAuthorId(requestDto.authorId())).willReturn(true);

        // when
        postService.updatePost(post.getId(), requestDto);

        // then
        verify(mockPost).update(requestDto.title(), requestDto.content());
    }

    @Test
    void 작성자만_게시글을_수정할_수_있다() {
        // given
        Post post = generatePost();
        Post mockPost = mock(Post.class);
        UpdatePostRequest requestDto = new UpdatePostRequest("마라톤 꿀팁", "매일매일 달리고 러닝화도 사세요", post.getAuthor().getId());
        given(postRepository.findByIdWithAuthor(any(Long.class))).willReturn(Optional.ofNullable(mockPost));
        given(mockPost.getAuthor()).willReturn(User.builder().id(10L).build());

        // when & then
        assertThatThrownBy(() -> postService.updatePost(post.getId(), requestDto))
                .isInstanceOf(CustomException.class)
                .hasMessage(ResponseStatus.AUTHOR_NOT_MATCH.getMessage());
    }

    @Test
    void 존재하지_않는_게시글을_수정할_수_없다() {
        // given
        UpdatePostRequest requestDto = new UpdatePostRequest("마라톤 꿀팁", "매일매일 달리고 러닝화도 사세요", 1L);
        given(postRepository.findByIdWithAuthor(any(Long.class))).willReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> postService.updatePost(1L, requestDto))
                .isInstanceOf(CustomException.class)
                .hasMessage(ResponseStatus.POST_NOT_FOUND.getMessage());
    }

    @Test
    void 게시글을_삭제한다() {
        // given
        Post post = generatePost();
        DeletePostRequest requestDto = new DeletePostRequest(post.getAuthor().getId());
        given(postRepository.findByIdWithAuthor(any(Long.class))).willReturn(Optional.ofNullable(post));

        // when
        postService.deletePost(post.getId(), requestDto);

        // then
        verify(postRepository).delete(post);
    }

    @Test
    void 존재하지_않는_게시글을_삭제할_수_없다() {
        // given
        DeletePostRequest requestDto = new DeletePostRequest(1L);
        given(postRepository.findByIdWithAuthor(any(Long.class))).willReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> postService.deletePost(1L, requestDto))
                .isInstanceOf(CustomException.class)
                .hasMessage(ResponseStatus.POST_NOT_FOUND.getMessage());
    }

    @Test
    void 작성자만_게시글을_삭제할_수_있다() {
        // given
        Post post = generatePost();
        DeletePostRequest requestDto = new DeletePostRequest(10L);
        given(postRepository.findByIdWithAuthor(any(Long.class))).willReturn(Optional.ofNullable(post));

        // when & then
        assertThatThrownBy(() -> postService.deletePost(post.getId(), requestDto))
                .isInstanceOf(CustomException.class)
                .hasMessage(ResponseStatus.AUTHOR_NOT_MATCH.getMessage());
    }

    User generateAuthor() {
        return User.builder()
                .id(1L)
                .name("빙봉")
                .age(20)
                .hobby("마라톤")
                .build();
    }

    Post generatePost() {
        return Post.builder()
                .id(1L)
                .title("마라톤 꿀팁")
                .author(generateAuthor())
                .content("런닝화를 사세요")
                .build();
    }
}
