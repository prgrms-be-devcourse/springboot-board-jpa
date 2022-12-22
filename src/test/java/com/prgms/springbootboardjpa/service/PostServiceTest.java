package com.prgms.springbootboardjpa.service;

import com.prgms.springbootboardjpa.dto.CreatePostRequest;
import com.prgms.springbootboardjpa.dto.PostDto;
import com.prgms.springbootboardjpa.dto.UpdatePostRequest;
import com.prgms.springbootboardjpa.exception.PostNotFoundException;
import com.prgms.springbootboardjpa.model.Member;
import com.prgms.springbootboardjpa.model.Post;
import com.prgms.springbootboardjpa.repository.PostRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private PostService postService;

    @Test
    @DisplayName("저장 성공")
    void createPost_test() {
        // given
        Long memberId = 1L;
        CreatePostRequest createPostRequest = new CreatePostRequest(
                memberId,
                "title",
                "content"
        );

        Member member = new Member("name", 10, "watching movies");
        long postId = 1L;
        Post post = new Post(postId, "title", "content", member);
        ArgumentCaptor<Post> postArgumentCaptor = ArgumentCaptor.forClass(Post.class);

        // when
        when(postRepository.save(any(Post.class)))
                .thenReturn(post);

        Long savedPostId = postService.createPost(member, createPostRequest.getTitle(), createPostRequest.getContent());

        // then
        assertThat(postId, is(savedPostId));
        assertThat(post.getAuthor(), samePropertyValuesAs(member));
        verify(postRepository, times(1)).save(postArgumentCaptor.capture());

        assertThat(post.getAuthor(), samePropertyValuesAs(postArgumentCaptor.getValue().getAuthor()));
        assertThat(post.getTitle(), is(postArgumentCaptor.getValue().getTitle()));
        assertThat(post.getContent(), is(postArgumentCaptor.getValue().getContent()));
    }


    @Test
    @DisplayName("수정 성공")
    void updatePost_test_success() {
        // given
        Long postId = 1L;

        Member member = new Member("name", 10, "watching movies");
        String updatedTitle = "updated_title";
        String updatedContent = "updated_content";

        UpdatePostRequest updatePostRequest = new UpdatePostRequest(
                updatedTitle,
                updatedContent
        );

        Post post = new Post(postId, "title", "content", member);

        // when
        when(postRepository.findById(postId))
                .thenReturn(Optional.of(post));

        // then
        Long updatePostId = postService.updatePost(postId, updatePostRequest);

        verify(postRepository, times(1)).findById(postId);
        assertThat(postId, is(updatePostId));
        assertThat(updatedTitle, is(post.getTitle()));
        assertThat(updatedContent, is(post.getContent()));
    }

    @Test
    @DisplayName("수정 실패 - postId에 해당하는 post 없음")
    void updatePost_test_fail() {
        // given
        Long postId = 1L;

        String updatedTitle = "updated_title";
        String updatedContent = "updated_content";

        UpdatePostRequest updatePostRequest = new UpdatePostRequest(
                updatedTitle,
                updatedContent
        );

        // when
        when(postRepository.findById(postId))
                .thenReturn(Optional.empty());

        // then
        Assertions.assertThatThrownBy(() -> postService.updatePost(postId, updatePostRequest))
                .isInstanceOf(PostNotFoundException.class);
        verify(postRepository, times(1)).findById(postId);
    }

    @Test
    @DisplayName("Post 조회 - 성공")
    void getPost_test_success() {
        // given
        long postId = 1L;
        Member author = new Member("name", 20, "hobby");
        Post post = new Post(postId, "title", "content", author);

        // when
        when(postRepository.findById(postId))
                .thenReturn(Optional.of(post));

        // then
        PostDto postDto = postService.getPost(postId);
        assertThat(post.getPostId(), is(postDto.getPostId()));
        assertThat(post.getTitle(), is(postDto.getTitle()));
        assertThat(post.getContent(), is(postDto.getContent()));
        verify(postRepository, times(1)).findById(postId);
    }

    @Test
    @DisplayName("Post 조회 실패 - postId에 해당하는 post 없음")
    void getPost_test_fail() {
        // given
        long postId = 1L;

        // when
        when(postRepository.findById(postId))
                .thenReturn(Optional.empty());

        // then
        Assertions.assertThatThrownBy(() -> postService.getPost(postId))
                .isInstanceOf(PostNotFoundException.class);
        verify(postRepository, times(1)).findById(postId);
    }

    @Test
    @DisplayName("PostList 조회 - 성공")
    void getPostList_test_success() {
        long id1 = 1L;
        long id2 = 2L;
        long id3 = 3L;

        Member author1 = new Member("name1", 20, "hobby1");
        Member author2 = new Member("name2", 30, "hobby2");

        Post post1 = new Post(id1, "title1", "content1", author1);
        Post post2 = new Post(id2, "title2", "content2", author2);
        Post post3 = new Post(id3, "title3", "content3", author2);

        when(postRepository.findAll(any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(post1, post2, post3), PageRequest.of(0, 3), 3));

        Page<PostDto> postList = postService.getPostList(PageRequest.of(1, 3));

        Assertions.assertThat(postList)
                .usingRecursiveComparison()
                .isEqualTo(new PageImpl<>(List.of(post1, post2, post3), PageRequest.of(0, 3), 3));

        verify(postRepository, times(1)).findAll(any(Pageable.class));
    }
}