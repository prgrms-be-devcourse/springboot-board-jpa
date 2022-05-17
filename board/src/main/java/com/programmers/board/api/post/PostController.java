package com.programmers.board.api.post;

import com.programmers.board.core.post.application.PostService;
import com.programmers.board.core.post.application.dto.CreateRequestPost;
import com.programmers.board.core.post.application.dto.ResponsePost;
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
    public ResponseEntity<Page<ResponsePost>> getAll(Pageable pageable){
        return ResponseEntity.ok(postService.findPosts(pageable));
    }

    @PostMapping
    public ResponseEntity<ResponsePost> save(@RequestBody CreateRequestPost createRequestPost){
        return ResponseEntity.ok(postService.save(createRequestPost));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponsePost> getOne(@PathVariable Long id){
        return ResponseEntity.ok(postService.findOne(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponsePost> update(
            @PathVariable Long id,
            @RequestBody CreateRequestPost postDto
    ){
        return ResponseEntity.ok(postService.update(id, postDto));
    }
}
