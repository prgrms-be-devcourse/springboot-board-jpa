package com.kdt.apis;

import static com.kdt.apis.PostApi.POSTS;
import static org.springframework.http.HttpStatus.CREATED;

import com.kdt.post.dto.PostDto;
import com.kdt.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(POSTS)
public class PostApi {

    protected static final String POSTS = "/posts";

    private final PostService postService;

    @PostMapping
    public ResponseEntity addPost(@RequestBody PostDto postDto) {
        return ResponseEntity.status(CREATED).body(ApiResponse.of(postService.save(postDto)));
    }

    @GetMapping
    public ResponseEntity getPosts(Pageable pageable) {
        return ResponseEntity.ok(postService.findAll(pageable));
    }

}
