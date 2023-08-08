package com.jpaboard.domain.post.application;

import com.jpaboard.domain.post.Post;
import com.jpaboard.domain.post.PostConverter;
import com.jpaboard.domain.post.dto.request.PostCreateRequest;
import com.jpaboard.domain.post.dto.request.PostSearchRequest;
import com.jpaboard.domain.post.dto.request.PostUpdateRequest;
import com.jpaboard.domain.post.dto.response.PostPageResponse;
import com.jpaboard.domain.post.dto.response.PostResponse;
import com.jpaboard.domain.post.infrastructure.PostRepository;
import com.jpaboard.domain.user.User;
import com.jpaboard.domain.user.infrastructure.UserRepository;
import org.instancio.Instancio;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import static org.assertj.core.api.Assertions.*;
import static org.instancio.Select.all;
import static org.instancio.Select.field;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PostServiceImplTest {

    @InjectMocks
    private PostServiceImpl postService;

    @Mock
    private PostRepository postRepository;

    @Mock
    private UserRepository userRepository;

    Random random = new Random();

    @DisplayName("올바른 요청을 통해 게시글을 생성할 수 있다.")
    @Test
    void create_post_test() {
        //given
        User user = Instancio.create(User.class);
        Long userId = user.getId();
        PostCreateRequest request = createPostCreateRequest(userId);
        Post post = createPostByRequest(request, user);
        Long postId = post.getId();

        given(userRepository.findById(userId)).willReturn(Optional.of(user));
        given(postRepository.save(any(Post.class))).willReturn(post);

        //when
        Long expectedId = postService.createPost(request);

        //then
        assertThat(postId).isEqualTo(expectedId);
    }

    @DisplayName("존재하는 게시글을 조회할 수 있다.")
    @Test
    void find_post_by_id_test() {
        //given
        User user = Instancio.create(User.class);
        Post post = createPost(user);
        Long postId = post.getId();
        PostResponse response = createPostResponse(post);

        given(postRepository.findById(postId)).willReturn(Optional.of(post));

        //when
        PostResponse storedPost = postService.findPostById(postId);

        //then
        assertThat(response).isEqualTo(storedPost);
    }

    @DisplayName("모든 게시글을 조회할 수 있다.")
    @Test
    void find_all_posts_test() {
        //이러한 데이터 생성을 beforeEach로 변경해야 하는지
        //어떠한 코드인지 파악이 쉽도록 아래와 같이 작성해야 하는지
        //given
        int numberOfPost = random.nextInt(100);
        int pageNum = 0;
        int pageSize = 10;

        Pageable pageable = PageRequest.of(pageNum, pageSize);
        User user = Instancio.create(User.class);
        List<Post> postList = createPostList(numberOfPost, user);
        Page<Post> pagedPost = new PageImpl<>(postList, pageable, numberOfPost);
        Page<PostResponse> pagedResponse = createPagedResponse(pagedPost);
        PostPageResponse response = PostConverter.convertEntityToPageResponse(pagedResponse);

        given(postRepository.findAll(pageable)).willReturn(pagedPost);

        //when
        PostPageResponse posts = postService.findPosts(pageable);

        //then
        assertThat(posts.totalElements()).isEqualTo(numberOfPost);
        assertThat(posts).isEqualTo(response);
    }

    @DisplayName("검색 조건에 따라 게시글을 조회할 수 있다.")
    @ParameterizedTest
    @CsvSource(value = {"안녕, null, null", "null, 본문, null", "null, null, 안녕", "null, null, 본문"}, nullValues = "null")
    void find_all_posts_by_condition_test(String title, String content, String keyword) {
        //given
        int numberOfPost = random.nextInt(100);
        int pageNum = 0;
        int pageSize = 10;

        Pageable pageable = PageRequest.of(pageNum, pageSize);
        User user = Instancio.create(User.class);
        List<Post> postList = createPostListWithData(numberOfPost, user);
        Page<Post> pagedPost = new PageImpl<>(postList, pageable, numberOfPost);
        Page<PostResponse> pagedResponse = createPagedResponse(pagedPost);
        PostPageResponse response = PostConverter.convertEntityToPageResponse(pagedResponse);

        PostSearchRequest search = new PostSearchRequest(title, content, keyword);

        given(postRepository.findAllByCondition(
                search.title(), search.content(), search.keyword(), pageable
        )).willReturn(pagedPost);

        //when
        PostPageResponse posts = postService.findPostsByCondition(search, pageable);

        //then
        assertThat(posts.totalElements()).isEqualTo(postList.size());
        assertThat(posts).isEqualTo(response);
    }

    @DisplayName("게시글을 수정할 수 있다.")
    @Test
    void update_post_test() {
        //given
        Post post = mock(Post.class);
        Long postId = post.getId();
        PostUpdateRequest request = Instancio.create(PostUpdateRequest.class);

        given(postRepository.findById(postId)).willReturn(Optional.of(post));

        //when
        postService.updatePost(postId, request);

        //then
        verify(post, atMostOnce()).update(request.title(), request.content());
    }

    @DisplayName("게시글을 삭제할 수 있다.")
    @Test
    void delete_post_test() {
        //given
        Post post = mock(Post.class);
        Long postId = post.getId();

        doNothing().when(postRepository).deleteById(postId);

        //when
        postService.deletePostById(postId);

        //then
        verify(postRepository, only()).deleteById(postId);
    }

    private PostCreateRequest createPostCreateRequest(Long userId) {
        return Instancio.of(PostCreateRequest.class)
                .set(field(PostCreateRequest::userId), userId)
                .create();
    }

    private Post createPost(User user) {
        return Instancio.of(Post.class)
                .set(field(Post::getUser), user)
                .create();
    }

    private Post createPostByRequest(PostCreateRequest request, User user) {
        return Instancio.of(Post.class)
                .supply(all(Post.class), gen -> PostConverter.convertRequestToEntity(request, user))
                .create();
    }

    private List<Post> createPostList(int size, User user) {
        return Instancio.ofList(Post.class)
                .size(size)
                .set(field(Post::getUser), user)
                .create();
    }

    private List<Post> createPostListWithData(int size, User user) {
        List<Post> postList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            postList.add(Instancio.of(Post.class)
                    .set(field(Post::getUser), user)
                    .set(field(Post::getTitle), "안녕" + i)
                    .set(field(Post::getTitle), "본문" + i)
                    .create());
        }
        return postList;
    }

    private PostResponse createPostResponse(Post post) {
        return Instancio.of(PostResponse.class)
                .supply(all(PostResponse.class), gen -> PostConverter.convertEntityToResponse(post))
                .create();
    }

    private Page<PostResponse> createPagedResponse(Page<Post> pagedPost) {
        return pagedPost.map(PostConverter::convertEntityToResponse);
    }

}
