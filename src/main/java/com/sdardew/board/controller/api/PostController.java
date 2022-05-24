package com.sdardew.board.controller.api;

import com.sdardew.board.domain.post.CreatePostDto;
import com.sdardew.board.domain.post.PostDto;
import com.sdardew.board.domain.post.UpdatePostDto;
import com.sdardew.board.service.PostService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {

  private final PostService postService;

  public PostController(PostService postService) {
    this.postService = postService;
  }

  @GetMapping
  public List<PostDto> getPosts() {
    return postService.getPosts();
  }

  @GetMapping("/{id}")
  public PostDto getPost(@PathVariable("id") Long id) {
    return postService.getPost(id);
  }

  @PostMapping
  public PostDto createPost(@RequestBody CreatePostDto createPostDto) {
    return postService.createPost(createPostDto);
  }

  @PostMapping("/{id}")
  public PostDto updatePost(@PathVariable("id") Long id, @RequestBody UpdatePostDto updatePostDto) {
    return postService.updatePost(id, updatePostDto);
  }
}
