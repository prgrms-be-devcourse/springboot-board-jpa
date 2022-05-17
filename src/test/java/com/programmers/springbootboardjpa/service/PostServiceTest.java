package com.programmers.springbootboardjpa.service;

import com.programmers.springbootboardjpa.domain.post.Post;
import com.programmers.springbootboardjpa.domain.user.User;
import com.programmers.springbootboardjpa.dto.post.request.PostCreationRequest;
import com.programmers.springbootboardjpa.dto.post.request.PostUpdateRequest;
import com.programmers.springbootboardjpa.dto.post.response.PostResponse;
import com.programmers.springbootboardjpa.repository.PostRepository;
import com.programmers.springbootboardjpa.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @InjectMocks
    private PostService postService;

    @Mock
    private PostRepository postRepository;

    @Mock
    private UserRepository userRepository;

    private User user = new User("user1", LocalDateTime.now(), "user1", 22L, "hobby1");
    private Post post = new Post("user1", LocalDateTime.now(), "title1", "This is the content of the first post.");

    @AfterEach
    void tearDown() {
        postRepository.deleteAll();
    }

    @Test
    @DisplayName("게시물 생성 테스트")
    void savePostTest() {
        PostCreationRequest request = new PostCreationRequest(1L, "title1", "This is the content of the first post.");
        Post post = request.toEntity();

        doReturn(Optional.of(user)).when(userRepository).findById(request.getUserId());
        doReturn(post).when(postRepository).save(any(Post.class));


        postService.savePost(request);


        verify(userRepository).findById(anyLong());
        verify(postRepository).save(any(Post.class));
    }

    @Test
    @DisplayName("게시물 단건 조회 테스트")
    void findPostByIdTest() {
        this.post.setUser(user);
        doReturn(Optional.of(this.post)).when(postRepository).findByIdWithUser(1L);

        postService.findPostById(1L);

        verify(postRepository).findByIdWithUser(1L);
    }

    @Test
    @DisplayName("게시물 다건 조회 테스트")
    void findAllPosts() {
        List<Post> postList = new ArrayList<>();
        postList.add(new Post("name1", LocalDateTime.now(), "title1", "content1"));
        postList.add(new Post("name2", LocalDateTime.now(), "title2", "content2"));
        postList.add(new Post("name3", LocalDateTime.now(), "title3", "content3"));
        postList.add(new Post("name4", LocalDateTime.now(), "title4", "content4"));
        postList.add(new Post("name5", LocalDateTime.now(), "title5", "content5"));
        postList.add(new Post("name6", LocalDateTime.now(), "title6", "content6"));
        postList.add(new Post("name7", LocalDateTime.now(), "title7", "content7"));
        postList.add(new Post("name8", LocalDateTime.now(), "title8", "content8"));
        postList.add(new Post("name9", LocalDateTime.now(), "title9", "content9"));
        postList.add(new Post("name10", LocalDateTime.now(), "title10", "content10"));
        postList.add(new Post("name11", LocalDateTime.now(), "title11", "content11"));
        postList.add(new Post("name12", LocalDateTime.now(), "title12", "content12"));
        postList.add(new Post("name13", LocalDateTime.now(), "title13", "content13"));
        postList.add(new Post("name14", LocalDateTime.now(), "title14", "content14"));
        postList.add(new Post("name15", LocalDateTime.now(), "title15", "content15"));
        postList.add(new Post("name16", LocalDateTime.now(), "title16", "content16"));
        postList.add(new Post("name17", LocalDateTime.now(), "title17", "content17"));
        postList.add(new Post("name18", LocalDateTime.now(), "title18", "content18"));
        postList.add(new Post("name19", LocalDateTime.now(), "title19", "content19"));
        postList.add(new Post("name20", LocalDateTime.now(), "title20", "content20"));
        postList.forEach(postElement -> postElement.setUser(user));
        Page<Post> responsePages = new PageImpl<>(postList);

        Pageable pageable = PageRequest.of(0, 20);

        doReturn(responsePages).when(postRepository).findAllFetchJoinWithPaging(pageable);


        Page<PostResponse> allPosts = postService.findAllPosts(pageable);


        verify(postRepository).findAllFetchJoinWithPaging(pageable);
        assertThat(allPosts.getContent().get(0).getTitle(), samePropertyValuesAs(postList.get(0).toPostResponse().getTitle()));
        assertThat(allPosts.getContent().get(10).getTitle(), samePropertyValuesAs(postList.get(10).toPostResponse().getTitle()));
    }

    @Test
    @DisplayName("게시물 업데이트 테스트")
    void updatePostTest() {
        PostUpdateRequest request = new PostUpdateRequest("updatedTitle", "updatedContent");
        post.setUser(user);
        doReturn(Optional.of(post)).when(postRepository).findById(1L);

        postService.updatePost(1L, request);

        verify(postRepository).findById(anyLong());
        assertThat(post.getTitle(), is(request.getTitle()));
        assertThat(post.getContent(), is(request.getContent()));
    }
}