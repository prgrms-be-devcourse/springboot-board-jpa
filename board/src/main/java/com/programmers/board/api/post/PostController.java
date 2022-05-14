package com.programmers.board.api.post;

import com.programmers.board.core.post.application.PostService;
import com.programmers.board.core.post.application.dto.PostDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    @GetMapping
    public ResponseEntity<Page<PostDto>> getAll(Pageable pageable){
        return ResponseEntity.ok(postService.findPosts(pageable));
    }

    @PostMapping
    public ResponseEntity<PostDto> save(@RequestBody PostDto postDto){
        return ResponseEntity.ok(postService.save(postDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getOne(@PathVariable Long id){
        return ResponseEntity.ok(postService.findOne(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostDto> update(
            @PathVariable Long id,
            @RequestBody PostDto postDto
    ){
        return ResponseEntity.ok(postService.update(id, postDto));
    }
}
