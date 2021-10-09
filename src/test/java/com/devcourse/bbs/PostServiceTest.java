package com.devcourse.bbs;

import com.devcourse.bbs.controller.bind.PostCreateRequest;
import com.devcourse.bbs.domain.post.Post;
import com.devcourse.bbs.domain.user.User;
import com.devcourse.bbs.repository.post.PostRepository;
import com.devcourse.bbs.repository.user.UserRepository;
import com.devcourse.bbs.service.post.BasicPostService;
import com.devcourse.bbs.service.post.PostService;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.Mockito.*;

class PostServiceTest {
    @Test
    void createPostTest() {
        UserRepository userRepository = mock(UserRepository.class);
        User user = User.builder()
                .id(1L)
                .age(25)
                .name("NAME")
                .hobby("HOBBY")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        PostRepository postRepository = mock(PostRepository.class);
        Post post = Post.builder()
                .id(1L)
                .title("TITLE")
                .content("CONTENT")
                .user(user)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now()).build();
        when(postRepository.save(any(Post.class))).thenReturn(post);

        PostService postService = new BasicPostService(userRepository, postRepository);
        PostCreateRequest request = new PostCreateRequest();
        request.setUser(1L);
        request.setTitle("TITLE");
        request.setContent("CONTENT");
        postService.createPost(request);

        verify(postRepository).save(any(Post.class));
    }

    @Test
    void findPostByIdTest() {
        PostRepository postRepository = mock(PostRepository.class);
        when(postRepository.findById(anyLong())).thenReturn(Optional.of(Post.builder()
                .id(2L)
                .title("TITLE")
                .content("CONTENT")
                .user(User.builder()
                        .id(2L)
                        .age(25)
                        .name("NAME")
                        .hobby("HOBBY")
                        .createdAt(LocalDateTime.now())
                        .updatedAt(LocalDateTime.now())
                        .build())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build()));

        PostService postService = new BasicPostService(mock(UserRepository.class), postRepository);
        postService.findPostById(2L);
        verify(postRepository).findById(2L);
    }

    @Test
    void findPostsByPageTest() {
        PostRepository postRepository = mock(PostRepository.class);
        when(postRepository.findAll(any(Pageable.class))).thenReturn(Page.empty());
        PostService postService = new BasicPostService(mock(UserRepository.class), postRepository);
        postService.findPostsByPage(1, 10);
        verify(postRepository).findAll(PageRequest.of(1, 10));
    }

    @Test
    void postDeleteTest() {
        PostRepository postRepository = mock(PostRepository.class);
        PostService postService = new BasicPostService(mock(UserRepository.class), postRepository);
        postService.deletePost(3L);
        verify(postRepository).deleteById(3L);
    }
}
