package kdt.springbootboardjpa.controller;

import kdt.springbootboardjpa.controller.request.SavePostRequest;
import kdt.springbootboardjpa.controller.response.PostResponse;
import kdt.springbootboardjpa.respository.entity.Post;
import kdt.springbootboardjpa.service.PostService;
import kdt.springbootboardjpa.service.dto.PostDto;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@Controller
@RequestMapping("/posts")
@AllArgsConstructor
public class PostController {
    private final PostService postService;

    @PostMapping
    public ResponseEntity<PostResponse> createPost(@RequestBody SavePostRequest request) {
        PostDto newPostDto = PostDto.builder()
                .title(request.title())
                .content(request.content())
                .createdBy(request.createdBy())
                .build();
        Post savedPost = postService.createPost(newPostDto);
        PostResponse response = PostResponse.toPostResponse(savedPost);
        URI createdURI = URI.create("/posts/" + response.id());
        return ResponseEntity.created(createdURI).body(response);
    }

    @PostMapping("/{id}")
    public ResponseEntity<PostResponse> updatePost(@PathVariable Long id, @RequestBody SavePostRequest request) {
        PostDto updatedPostDto = PostDto.builder()
                .title(request.title())
                .content(request.content())
                .createdBy(request.createdBy())
                .build();
        Post savedPost = postService.updatePost(id, updatedPostDto);
        PostResponse response = PostResponse.toPostResponse(savedPost);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getPost(@PathVariable Long id) {
        Post returnedPost = postService.getPostById(id);
        PostResponse response = PostResponse.toPostResponse(returnedPost);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<PostResponse>> getAllPosts() {
        List<PostResponse> response = postService.getAllPosts()
                .stream().map(PostResponse::toPostResponse).toList();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
