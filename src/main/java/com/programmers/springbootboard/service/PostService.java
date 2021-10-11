package com.programmers.springbootboard.service;

import com.programmers.springbootboard.dto.ApiResponse;
import com.programmers.springbootboard.dto.PostRequestDto;
import com.programmers.springbootboard.dto.PostResponseDto;
import com.programmers.springbootboard.entity.Post;
import com.programmers.springbootboard.entity.User;
import com.programmers.springbootboard.handler.NotFoundException;
import com.programmers.springbootboard.repository.PostRepository;
import com.programmers.springbootboard.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public ApiResponse<PostResponseDto> createPost(PostRequestDto dto) {
        Optional<User> userResult = userRepository.findById(dto.getUserId());
        if (userResult.isEmpty()) {
            throw new NotFoundException("User not found with user id: " + dto.getUserId());
        }
        User user = userResult.get();
        Post post = Post.builder().title(dto.getTitle()).content(dto.getContent()).user(user).build();
        Post savedPost = postRepository.save(post);
        PostResponseDto postResponseDto = PostResponseDto
                .builder()
                .postId(savedPost.getId())
                .title(savedPost.getTitle())
                .content(savedPost.getContent())
                .userId(user.getId())
                .build();
        return ApiResponse.<PostResponseDto>builder()
                .httpMethod("POST")
                .statusCode(200)
                .data(postResponseDto)
                .build();
    }

    public ApiResponse<PostResponseDto> readPost(Long id) {
        Optional<Post> result = postRepository.findPostWithUserById(id);
        if (result.isEmpty()) {
            throw new NotFoundException("No Post found with id: " + id);
        }
        Post post = result.get();
        return ApiResponse
                .<PostResponseDto>builder()
                .httpMethod("GET")
                .statusCode(200)
                .data(converter(post))
                .build();
    }

    public ApiResponse<List<PostResponseDto>> readAllPost() {
        List<Post> postList = postRepository.findPostWithUserAll();
        if (postList.size() == 0) throw new NotFoundException("No Post found");

        return ApiResponse.<List<PostResponseDto>>builder()
                .httpMethod("GET")
                .statusCode(200)
                .data(postList.stream().map(PostService::converter).toList())
                .build();
    }

    public ApiResponse<PostResponseDto> updatePost(PostRequestDto dto) {
        if (dto.getId() == null) {
            throw new IllegalArgumentException("Post Id is required on Dto.");
        }
        Optional<Post> found = postRepository.findById(dto.getId());
        if (found.isEmpty()) {
            throw new NotFoundException("No Post found with id: " + dto.getId());
        }
        Post post = found.get();
        post.setTitle(dto.getTitle());
        post.setContent(dto.getContent());
        PostResponseDto postResponseDto = PostResponseDto.builder()
                .postId(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .userId(dto.getUserId())
                .build();
        return ApiResponse.<PostResponseDto>builder()
                .statusCode(200)
                .httpMethod(HttpMethod.POST.toString())
                .data(postResponseDto)
                .build();
    }

    public static PostResponseDto converter(Post post) {
        return PostResponseDto.builder()
                .postId(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .userId(post.getUser().getId())
                .build();
    }
}
