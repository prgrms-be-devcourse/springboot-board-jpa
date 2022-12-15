package com.prgrms.be.app.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PostServiceTest {

    private final PostRepository postRepository = mock(PostRepository.class);
    private final UserRepository userRepository = mock(UserRepository.class);
    private final PostConverter postConverter = mock(PostConverter.class);
    private final PostService postService = new PostService(postRepository, userRepository, postConverter);
//    private static MockedStatic<PostConverter> instance = mockStatic(PostConverter.class);

    @Test
    @DisplayName("Post를 생성할 수 있다.")
    void shouldCreatePostTest() {
        // given
        PostDTO.CreateRequest postCreateDTO = new PostDTO.CreateRequest("title", "content", 1L);
        User user = new User(postCreateDTO.userId(), "user", 25, "농구");
        Post post = new Post( 1L,"title", "content", user);

        when(postConverter.covertToPost(postCreateDTO,user))
                .thenReturn(post);
        when(postRepository.save(post))
                .thenReturn(post);
        when(userRepository.findById(1L))
                .thenReturn(Optional.of(user));

        // when
        Long postId = postService.createPost(postCreateDTO);

        // then
        assertThat(postId).isEqualTo(post.getId());
    }

    @Test
    @DisplayName("Post를 페이지 단위로 조회할 수 있다.")
    void shouldFindPostsInPageTest() {
        // given
        Pageable pageable = PageRequest.of(0, 5);
        List<Post> postList = new ArrayList<>();
        for(long i = 0; i < 20; i++) {
            PostDTO.CreateRequest postCreateDTO = new PostDTO.CreateRequest("title", "content", i);
            User user = new User(postCreateDTO.userId(), "user", 25, "농구");
            Post post = new Post(i, postCreateDTO.title(), postCreateDTO.content(), user);
            postList.add(post);
        }
        Page<Post> pageResult = new PageImpl<>(postList, pageable, 0);

        when(postRepository.findAll(pageable)).thenReturn(pageResult);

        // when
//        List<Post> posts = postService.findAll(pageable);

        // then
    }
}