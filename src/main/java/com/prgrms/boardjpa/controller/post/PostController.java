package com.prgrms.boardjpa.controller.post;

import com.prgrms.boardjpa.domain.post.dto.PostCreateRequestDto;
import com.prgrms.boardjpa.domain.post.dto.PostResponseDto;
import com.prgrms.boardjpa.domain.post.dto.PostUpdateRequestDto;
import com.prgrms.boardjpa.domain.post.dto.PostsResponseDto;
import com.prgrms.boardjpa.domain.post.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@AllArgsConstructor
@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    @GetMapping
    public ResponseEntity<PostsResponseDto> findAllPostWithPagination(Pageable pageable) {
        PostsResponseDto posts = PostsResponseDto.from(postService.findAllPost(pageable));
        return ResponseEntity.ok(posts);
    }

    @PostMapping
    public ResponseEntity<PostResponseDto> createPost(@RequestBody PostCreateRequestDto postCreateRequestDto) {
        PostResponseDto createdPost = postService.createPost(postCreateRequestDto);
        return new ResponseEntity<>(createdPost, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostResponseDto> modifyPost(@PathVariable("id") long id, @RequestBody PostUpdateRequestDto postUpdateRequestDto) {
        PostResponseDto updatedPost = postService.updatePost(id, postUpdateRequestDto);
        return ResponseEntity.ok(updatedPost);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponseDto> findPostById(@PathVariable("id") long id) {
        PostResponseDto post = postService.findPostById(id);
        return ResponseEntity.ok(post);
    }
}
