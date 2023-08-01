package com.juwoong.springbootboardjpa.post.api;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.juwoong.springbootboardjpa.post.api.model.PostRequest;
import com.juwoong.springbootboardjpa.post.application.PostService;
import com.juwoong.springbootboardjpa.post.application.model.PostDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping("/create")
    public ResponseEntity<PostDto> createPost(@RequestBody PostRequest request){
        PostDto post = postService.createPost(request.getUserId(), request.getPostTitle(), request.getPostContent());

        return ResponseEntity.ok(post);
    }

    @GetMapping("/search/{id}")
    public ResponseEntity<PostDto> searchById(@PathVariable("id") Long id) {
        PostDto post = postService.searchById(id);

        return ResponseEntity.ok(post);
    }

    @GetMapping("/search")
    public Page<PostDto> getAllUsers(@RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "2") int size) {
        PageRequest pageRequest = PageRequest.of(page, size);

        return postService.searchAll(pageRequest);
    }


    @PutMapping("/update")
    public ResponseEntity<PostDto> editPost(@RequestBody PostRequest request){
        PostDto post = postService.editPost(request.getPostId(), request.getPostTitle(), request.getPostContent());

        return ResponseEntity.ok(post);
    }

}
