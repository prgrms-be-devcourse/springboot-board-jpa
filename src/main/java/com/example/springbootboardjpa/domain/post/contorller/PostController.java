package com.example.springbootboardjpa.domain.post.contorller;

import com.example.springbootboardjpa.domain.post.dto.request.PostSaveRequestDto;
import com.example.springbootboardjpa.domain.post.dto.request.PostUpdateRequestDto;
import com.example.springbootboardjpa.domain.post.dto.response.PostFindAllResponseDto;
import com.example.springbootboardjpa.domain.post.dto.response.PostFindByIdResponseDto;
import com.example.springbootboardjpa.domain.post.dto.response.PostSaveResponseDto;
import com.example.springbootboardjpa.domain.post.dto.response.PostUpdateResponseDto;
import com.example.springbootboardjpa.domain.post.entity.Post;
import com.example.springbootboardjpa.domain.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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
      PostFindAllResponseDto postFindAllResponseDto = new PostFindAllResponseDto(
          post.getId(),
          post.getTitle(),
          post.getCreatedAt(),
          post.getCreatedBy()
      );

      postFindAllResponseDtos.add(postFindAllResponseDto);
    });

    return ResponseEntity.ok(postFindAllResponseDtos);
  }

  @GetMapping("/{postId}")
  public ResponseEntity<PostFindByIdResponseDto> findById(@PathVariable long postId) {
    Post post = postService.findById(postId);
    PostFindByIdResponseDto postFindByIdResponseDto = new PostFindByIdResponseDto(
        post.getId(),
        post.getTitle(),
        post.getContent(),
        post.getCreatedBy(),
        post.getCreatedAt()
    );

    return ResponseEntity.ok(postFindByIdResponseDto);
  }

  @PostMapping()
  public ResponseEntity<PostSaveResponseDto> save(
      @RequestBody PostSaveRequestDto postSaveRequestDto) {
    Post post = postService.save(
        postSaveRequestDto.getTitle(),
        postSaveRequestDto.getContent(),
        postSaveRequestDto.getMemberId()
    );

    PostSaveResponseDto postSaveResponseDto = new PostSaveResponseDto(
        post.getId(),
        post.getTitle(),
        post.getContent(),
        post.getCreatedAt(),
        post.getCreatedBy()
    );

    return ResponseEntity.ok(postSaveResponseDto);
  }

  @PostMapping("/{postId}")
  public ResponseEntity<PostUpdateResponseDto> update(@PathVariable long postId,
      @RequestBody PostUpdateRequestDto postUpdateRequestDto) {
    Post post = postService.update(
        postId,
        postUpdateRequestDto.getTitle(),
        postUpdateRequestDto.getContent(),
        postUpdateRequestDto.getMemberId()
    );

    PostUpdateResponseDto postUpdateResponseDto = new PostUpdateResponseDto(
        post.getId(),
        post.getTitle(),
        post.getContent(),
        post.getCreatedBy(),
        post.getCreatedAt()
    );

    return ResponseEntity.ok(postUpdateResponseDto);
  }
}
