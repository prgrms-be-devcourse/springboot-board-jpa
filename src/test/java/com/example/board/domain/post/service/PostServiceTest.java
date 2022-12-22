package com.example.board.domain.post.service;

import com.example.board.domain.post.dto.PostDto;
import com.example.board.domain.user.entity.User;
import com.example.board.domain.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase
class PostServiceTest {

    @Autowired
    private PostService postService;

    @Autowired
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    void setUp() {
        user = userRepository.save(new User("박현서", 3));
        createPostRequestsDummy(user).forEach(postService::post);
    }

    @DisplayName("게시글 페이징에 성공한다.")
    @Test
    void paging_posts_success() {
        // given
        final int page = 1;
        final int pageSize = 3;

        // when
        Page<PostDto.SinglePostResponse> singlePostResponses =
                postService.pagingPost(PageRequest.of(page, pageSize));
        List<PostDto.SinglePostResponse> pagedPosts = singlePostResponses.getContent();

        // then
        assertThat(pagedPosts).hasSize(pageSize);
    }

    @DisplayName("게시글 단건 조회에 성공한다.")
    @Test
    void get_post_success() {
        // given & when
        PostDto.SinglePostResponse findPost = postService.getPost(user.getId());

        // then
        assertThat(findPost)
                .hasFieldOrPropertyWithValue("postId", user.getId())
                .hasFieldOrPropertyWithValue("title", "제목1")
                .hasFieldOrPropertyWithValue("content", "내용1");
    }

    @DisplayName("게시글 수정에 성공한다.")
    @Test
    void update_post_success() {
        // given
        PostDto.SinglePostResponse singlePostResponse = postService.post(createPostRequestsDummy(user).get(0));
        PostDto.UpdatePostRequest updatePostRequest = new PostDto.UpdatePostRequest("수정된제목", "수정된내용");

        // when
        PostDto.SinglePostResponse updatedPostResponse = postService.updatePost(singlePostResponse.postId(), updatePostRequest);
        PostDto.SinglePostResponse findUpdatedPostResponse = postService.getPost(updatedPostResponse.postId());

        // then
        assertThat(findUpdatedPostResponse)
                .hasFieldOrPropertyWithValue("postId", singlePostResponse.postId())
                .hasFieldOrPropertyWithValue("title", "수정된제목")
                .hasFieldOrPropertyWithValue("content", "수정된내용");
    }

    private List<PostDto.CreatePostRequest> createPostRequestsDummy(User user) {
        return List.of(
                new PostDto.CreatePostRequest(user.getId(), "제목1", "내용1"),
                new PostDto.CreatePostRequest(user.getId(), "제목2", "내용2"),
                new PostDto.CreatePostRequest(user.getId(), "제목3", "내용3"),
                new PostDto.CreatePostRequest(user.getId(), "제목4", "내용4"),
                new PostDto.CreatePostRequest(user.getId(), "제목5", "내용5"),
                new PostDto.CreatePostRequest(user.getId(), "제목6", "내용6"));
    }
}