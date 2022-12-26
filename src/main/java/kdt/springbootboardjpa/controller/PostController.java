package kdt.springbootboardjpa.controller;

import kdt.springbootboardjpa.controller.request.SavePostRequest;
import kdt.springbootboardjpa.controller.response.PostResponse;
import kdt.springbootboardjpa.global.dto.BaseResponse;
import kdt.springbootboardjpa.global.utils.ResponseUtil;
import kdt.springbootboardjpa.respository.entity.Post;
import kdt.springbootboardjpa.service.PostService;
import kdt.springbootboardjpa.service.dto.PostDto;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/posts")
@AllArgsConstructor
public class PostController {
    private final PostService postService;

    @PostMapping
    public ResponseEntity<BaseResponse<PostResponse>> createPost(@RequestBody SavePostRequest request) {
        PostDto newPostDto = PostDto.builder()
                .title(request.title())
                .content(request.content())
                .createdBy(request.createdBy())
                .build();
        Post savedPost = postService.createPost(newPostDto);
        PostResponse response = PostResponse.toPostResponse(savedPost);

        URI createdURI = URI.create("/posts/" + response.id());
        return ResponseUtil.created(response, createdURI);
    }

    @PostMapping("/{id}")
    public BaseResponse<PostResponse> updatePost(@PathVariable Long id, @RequestBody SavePostRequest request) {
        PostDto updatedPostDto = PostDto.builder()
                .title(request.title())
                .content(request.content())
                .createdBy(request.createdBy())
                .build();
        Post savedPost = postService.updatePost(id, updatedPostDto);
        PostResponse response = PostResponse.toPostResponse(savedPost);
        return BaseResponse.success(HttpStatus.OK, response);
    }

    @GetMapping("/{id}")
    public BaseResponse<PostResponse> getPost(@PathVariable Long id) {
        Post returnedPost = postService.getPostById(id);
        PostResponse response = PostResponse.toPostResponse(returnedPost);
        return BaseResponse.success(HttpStatus.OK, response);
    }

    @GetMapping
    public BaseResponse<List<PostResponse>> getAllPosts() {
        List<PostResponse> response = postService.getAllPosts()
                .stream().map(PostResponse::toPostResponse).toList();
        return BaseResponse.success(HttpStatus.OK, response);
    }
}
