package com.jpa.board.post.controller;

import com.jpa.board.post.dto.PostCreateDto;
import com.jpa.board.post.dto.PostUpdateDto;
import com.jpa.board.post.service.PostService;
import com.jpa.board.post.dto.PostReadDto;
import javassist.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping("/posts")
    public ResponseEntity<Page<PostReadDto>> getAll(
        Pageable pageable
    ){
        Page<PostReadDto> posts = postService.findAll(pageable);
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/posts/{id}")
    public ResponseEntity<PostReadDto> getAllById(
            @PathVariable Long id
    )throws NotFoundException {
        PostReadDto post = postService.findById(id);
        return ResponseEntity.ok(post);
    }

    @PostMapping("/posts")
    public ResponseEntity<String> save (
            @RequestBody PostCreateDto postCreateDto
    )throws NotFoundException {
        //작성 잘 됬을 때, 해당 userId, 게시글 title 정보 string 으로 반환해주었습니다;
        return ResponseEntity.ok(postService.save(postCreateDto));
    }

    @PostMapping("/posts/{id}")
    public ResponseEntity<String> update (
            @RequestBody PostUpdateDto postUpdateDto,
            @PathVariable Long id
    )throws NotFoundException {
        return ResponseEntity.ok(postService.update(postUpdateDto, id));
    }
}
