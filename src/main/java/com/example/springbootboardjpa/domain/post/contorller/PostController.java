package com.example.springbootboardjpa.domain.post.contorller;

import com.example.springbootboardjpa.domain.post.dto.request.PostSaveRequestDto;
import com.example.springbootboardjpa.domain.post.dto.request.PostUpdateRequestDto;
import com.example.springbootboardjpa.domain.post.dto.response.PostFindAllResponseDto;
import com.example.springbootboardjpa.domain.post.dto.response.PostFindByIdResponseDto;
import com.example.springbootboardjpa.domain.post.dto.response.PostSaveResponseDto;
import com.example.springbootboardjpa.domain.post.dto.response.PostUpdateResponseDto;
import com.example.springbootboardjpa.domain.post.entity.Post;
import com.example.springbootboardjpa.domain.post.service.PostService;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

  private final PostService postService;

  @GetMapping()
  public ResponseEntity<List<PostFindAllResponseDto>> findAll(@RequestParam int page,
      @RequestParam int size) {
    PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.DESC, "createdAt");
    List<PostFindAllResponseDto> postFindAllResponseDtos = new ArrayList<>();
    Page<Post> posts = postService.findAll(pageRequest);

    posts.forEach(post -> {
      PostFindAllResponseDto postFindAllResponseDto = new PostFindAllResponseDto(post);
      postFindAllResponseDtos.add(postFindAllResponseDto);
    });

    return ResponseEntity.ok(postFindAllResponseDtos);
  }

  @GetMapping("/{postId}")
  public ResponseEntity<PostFindByIdResponseDto> findById(@PathVariable long postId) {
    Post post = postService.findById(postId);
    PostFindByIdResponseDto postFindByIdResponseDto = new PostFindByIdResponseDto(post);

    return ResponseEntity.ok(postFindByIdResponseDto);
  }

  @PostMapping()
  public ResponseEntity<PostSaveResponseDto> save(
      @RequestBody PostSaveRequestDto postSaveRequestDto) {
    Post post = postService.save(postSaveRequestDto);

    PostSaveResponseDto postSaveResponseDto = new PostSaveResponseDto(post);

    return ResponseEntity.ok(postSaveResponseDto);
  }

  @PostMapping("/{postId}")
  public ResponseEntity<PostUpdateResponseDto> update(@PathVariable long postId,
      @RequestBody PostUpdateRequestDto postUpdateRequestDto) {
    Post post = postService.update(postId, postUpdateRequestDto);

    PostUpdateResponseDto postUpdateResponseDto = new PostUpdateResponseDto(post);

    return ResponseEntity.ok(postUpdateResponseDto);
  }
}
