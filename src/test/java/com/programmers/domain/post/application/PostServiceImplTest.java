package com.programmers.domain.post.application;

import com.programmers.domain.post.application.converter.PostConverter;
import com.programmers.domain.post.entity.Post;
import com.programmers.domain.post.infra.PostRepository;
import com.programmers.domain.post.ui.dto.PostCreateDto;
import com.programmers.domain.post.ui.dto.PostResponseDto;
import com.programmers.domain.post.ui.dto.PostUpdateDto;
import com.programmers.domain.user.entity.User;
import com.programmers.domain.user.infra.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class PostServiceImplTest {

    private PostService postService;
    private PostConverter postConverter;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PostRepository postRepository;

    @BeforeEach
    void setUp() {
        postConverter = new PostConverter();
        postService = new PostServiceImpl(postRepository, userRepository, postConverter);
    }

    @Test
    void createPost() {
        //given
        final Long expectedPostId = 1l;

        final Long fakeUserId = 1l;
        final Long fakePostId = 1l;

        PostCreateDto postCreateDto = new PostCreateDto("제목", "본문", fakeUserId);
        User user = new User("명한", 1, "운동");
        Post post = new Post("제목", "본문", user);

        Post savedPost = new Post("제목", "본문", user);
        ReflectionTestUtils.setField(savedPost, "id", fakePostId);

        BDDMockito.given(userRepository.findById(fakeUserId)).willReturn(Optional.of(user));
        BDDMockito.given(postRepository.save(post)).willReturn(savedPost);

        //when
        Long savedPostId = postService.createPost(postCreateDto);

        //then
        assertThat(savedPostId).isEqualTo(expectedPostId);
    }

    @Test
    void findPost() {
        //given
        final Long expectedPostId = 1l;

        final Long fakeUserId = 1l;
        final Long fakePostId = 1l;

        User user = new User("명한", 1, "운동");
        ReflectionTestUtils.setField(user, "id", fakeUserId);
        Post post = new Post("제목", "본문", user);
        Post savedPost = new Post("제목", "본문", user);
        ReflectionTestUtils.setField(savedPost, "id", fakePostId);

        BDDMockito.given(postRepository.findById(fakePostId)).willReturn(Optional.of(savedPost));

        //when
        PostResponseDto foundPost = postService.findPost(fakePostId);

        //then
        assertThat(foundPost.postId()).isEqualTo(expectedPostId);
    }

    @Test
    void findAll() {
        // given
        final int expectedCount = 2;

        final Long fakeUserId = 1l;
        final Long fakePostId1 = 1l;
        final Long fakePostId2 = 2l;

        User user = new User("명한", 1, "운동");
        ReflectionTestUtils.setField(user, "id", fakeUserId);
        Post post1 = new Post("제목1", "본문1", user);
        ReflectionTestUtils.setField(post1, "id", fakePostId1);
        Post post2 = new Post("제목2", "본문2", user);
        ReflectionTestUtils.setField(post2, "id", fakePostId2);

        PageRequest pageRequest = PageRequest.of(0, 20, Sort.by(Sort.Direction.DESC, "id"));
        PageImpl<Post> posts = new PageImpl<>(List.of(post1, post2));
        BDDMockito.given(postRepository.findAll(pageRequest)).willReturn(posts);

        // when
        List<PostResponseDto> postList = postService.findAll(pageRequest);

        // then
        assertThat(postList).hasSize(expectedCount);
    }

    @Test
    void updatePost() {
        // given
        final Long fakeUserId = 1l;
        final Long fakePostId = 1l;

        User user = new User("명한", 1, "운동");
        ReflectionTestUtils.setField(user, "id", fakeUserId);
        Post post = new Post("제목", "본문", user);
        ReflectionTestUtils.setField(post, "id", fakePostId);

        PostUpdateDto postUpdateDto = new PostUpdateDto("제목수정", "본문수정");

        BDDMockito.given(postRepository.findById(fakePostId)).willReturn(Optional.of(post));

        // when
        PostResponseDto updatedPost = postService.updatePost(postUpdateDto, fakePostId);

        // then
        assertThat(updatedPost.title()).isEqualTo(postUpdateDto.title());
        assertThat(updatedPost.content()).isEqualTo(postUpdateDto.content());
    }
}
