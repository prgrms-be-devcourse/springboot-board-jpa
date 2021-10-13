package com.programmers.springbootboard.controller;

import com.programmers.springbootboard.dto.ApiResponse;
import com.programmers.springbootboard.dto.PostRequestDto;
import com.programmers.springbootboard.dto.PostResponseDto;
import com.programmers.springbootboard.entity.Post;
import com.programmers.springbootboard.entity.User;
import com.programmers.springbootboard.service.PostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest
class PostControllerTest {

    @InjectMocks
    private PostController postController;

    @Mock
    private PostService postService;

    User user;

    @BeforeEach
    void setup() {
        user = User.builder().id(1L).name("user").age(10).hobby("coding").build();
    }

    @Test
    @DisplayName("포스트 조회 리스폰스 테스트")
    void testGetPost() {
        // Given
        long id = 1L;
        Post post = Post.builder().id(id).title("title").content("content").user(user).build();
        PostResponseDto resDto = PostResponseDto.of(post);
        when(postService.readPost(id)).thenReturn(resDto);

        // When
        ApiResponse<PostResponseDto> response = postController.getPost(id);

        // Then
        assertThat(response.getSuccess()).isTrue();
        assertThat(response.getHttpMethod()).isEqualTo(HttpMethod.GET.name());
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getData()).usingRecursiveComparison().isEqualTo(resDto);
    }

    @Test
    @DisplayName("포스트 페이지 조회 리스폰스 테스트")
    void testGetPosts() {
        // Given
        List<Post> postList = new ArrayList<>();
        for (long i = 0; i < 10; i++) {
            postList.add(Post.builder().id(i).title("title" + i).content("content" + i).user(user).build());
        }

        Pageable pageable = PageRequest.of(2, 3, Sort.by(Sort.Direction.ASC, "title"));
        int start = (int)pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), postList.size());
        Page<Post> page = new PageImpl<>(postList.subList(start, end), pageable, postList.size());
        when(postService.readPostPage(pageable)).thenReturn(page.map(PostResponseDto::of));

        // When
        ApiResponse<Page<PostResponseDto>> response = postController.getPosts(pageable);

        // Then
        assertThat(response.getSuccess()).isTrue();
        assertThat(response.getHttpMethod()).isEqualTo(HttpMethod.GET.name());
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getData().getContent())
                .usingRecursiveComparison()
                .isEqualTo(postList.subList(6, 9).stream().map(PostResponseDto::of).toList());
    }

    @Test
    @DisplayName("포스트 생성 리스폰스 테스트")
    void testCreatePost() {
        // Given
        PostRequestDto reqDto = PostRequestDto.builder().title("title").content("content").username(user.getName()).build();
        PostResponseDto resDto = PostResponseDto.of(Post.builder().id(1L).title("title").content("content").user(user).build());
        when(postService.createPost(reqDto)).thenReturn(resDto);

        // When
        ApiResponse<PostResponseDto> response = postController.createPost(reqDto);

        // Then
        assertThat(response.getSuccess()).isTrue();
        assertThat(response.getHttpMethod()).isEqualTo(HttpMethod.POST.name());
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getData()).usingRecursiveComparison().isEqualTo(resDto);
    }

    @Test
    @DisplayName("포스트 수정 리스폰스 테스트")
    void testUpdatePost() {
        // Given
        long id = 1L;
        PostRequestDto reqDto = PostRequestDto.builder().title("title2").content("content2").username(user.getName()).build();
        PostResponseDto resDto = PostResponseDto.of(Post.builder().id(id).title("title2").content("content2").user(user).build());
        when(postService.updatePost(reqDto)).thenReturn(resDto);

        // When
        ApiResponse<PostResponseDto> response = postController.updatePost(reqDto, id);

        // Then
        assertThat(response.getSuccess()).isTrue();
        assertThat(response.getHttpMethod()).isEqualTo(HttpMethod.PUT.name());
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getData()).usingRecursiveComparison().isEqualTo(resDto);
    }
}