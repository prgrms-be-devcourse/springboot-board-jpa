package me.kimihiqq.springbootboardjpa.post.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import me.kimihiqq.springbootboardjpa.post.dto.request.PostCreateRequest;
import me.kimihiqq.springbootboardjpa.post.dto.request.PostUpdateRequest;
import me.kimihiqq.springbootboardjpa.post.dto.response.PostResponse;
import me.kimihiqq.springbootboardjpa.post.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class PostApiController {

    private final PostService postService;

    @GetMapping
    public ResponseEntity<Page<PostResponse>> getPosts(@PageableDefault(sort = "id", size = 10, direction = Sort.Direction.DESC) Pageable pageable) {
        Page<PostResponse> responseDto = postService.getPosts(pageable);
        return ResponseEntity.ok().body(responseDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getPost(@PathVariable Long id) {
        PostResponse responseDto = postService.getPost(id);
        return ResponseEntity.ok().body(responseDto);
    }

    @PostMapping
    public ResponseEntity<PostResponse> createPost(@RequestBody @Valid PostCreateRequest requestDto) {
        PostResponse reponse = postService.save(requestDto);
        return ResponseEntity.ok().body(reponse);
    }


    @PutMapping("/{id}")
    public ResponseEntity<PostResponse> updatePost(@PathVariable Long id, @RequestBody @Valid PostUpdateRequest requestDto) {
        PostResponse reponse = postService.update(id, requestDto);
        return ResponseEntity.ok().body(reponse);
    }
}

