package com.kdt.api;

import static com.kdt.api.PostApi.POSTS;
import static com.kdt.api.PostApi.PREFIX;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

import com.kdt.post.dto.PostDto;
import com.kdt.post.service.PostService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(PREFIX + POSTS)
public class PostApi {

    protected static final String PREFIX = "/api/v1";
    protected static final String POSTS = "/posts";

    private final PostService postService;

    @PostMapping
    public ResponseEntity addPost(@RequestBody @Valid PostDto postDto) {
        return ResponseEntity.status(CREATED).body(ApiResponse.of(postService.save(postDto)));
    }

    @GetMapping
    public ResponseEntity getPosts(Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.of(postService.findAll(pageable)));
    }

    @GetMapping("/{id}")
    public ResponseEntity getPost(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.of(postService.findOne(id)));
    }

    @PostMapping("/{id}")
    public ResponseEntity updatePost(@RequestBody @Valid PostDto postDto) {
        return ResponseEntity.status(NO_CONTENT).body(ApiResponse.of(postService.update(postDto)));
    }

}
