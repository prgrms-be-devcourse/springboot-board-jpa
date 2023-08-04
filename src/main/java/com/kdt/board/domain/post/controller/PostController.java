package com.kdt.board.domain.post.controller;

import com.kdt.board.domain.post.dto.PostCreateRequestDto;
import com.kdt.board.domain.post.dto.PostResponseDto;
import com.kdt.board.domain.post.dto.PostUpdateRequestDto;
import com.kdt.board.domain.post.entity.Post;
import com.kdt.board.domain.post.service.PostService;
import com.kdt.board.domain.user.entity.User;
import com.kdt.board.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<PostResponseDto> savePost(@RequestBody PostCreateRequestDto requestDto) {
        User user = userService.findById(requestDto.getUserId());
        Post post = PostCreateRequestDto.from(requestDto, user);
        Post savedPost = postService.save(post);

        return ResponseEntity.ok(new PostResponseDto(savedPost));
    }

    @GetMapping
    public ResponseEntity<Page<PostResponseDto>> getAllPosts(Pageable pageable) {
        Page<Post> posts = postService.findAll(pageable);
        Page<PostResponseDto> responseDtoPage = posts.map(PostResponseDto::new);
        return ResponseEntity.ok(responseDtoPage);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponseDto> getPost(@PathVariable Long id) {
        Post post = postService.findById(id);
        return ResponseEntity.ok(new PostResponseDto(post));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<PostResponseDto> updatePost(@PathVariable Long id, @RequestBody PostUpdateRequestDto requestDto) {
        Post post = PostUpdateRequestDto.from(requestDto);
        Post updatedPost = postService.updatePost(id, post);
        return ResponseEntity.ok(new PostResponseDto(updatedPost));
    }

}
