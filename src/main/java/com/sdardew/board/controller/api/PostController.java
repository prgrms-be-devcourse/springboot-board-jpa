package com.sdardew.board.controller.api;

import com.sdardew.board.domain.post.CreatePostDto;
import com.sdardew.board.domain.post.Post;
import com.sdardew.board.domain.post.PostDto;
import com.sdardew.board.service.PostService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {

  PostService postService;

  public PostController(PostService postService) {
    this.postService = postService;
  }

  @GetMapping
  public List<Post> getPosts() {
    return postService.getPosts();
  }

  @PostMapping
  public PostDto createPost(@RequestBody CreatePostDto createPostDto) {
    return postService.createPost(createPostDto);
  }

}
