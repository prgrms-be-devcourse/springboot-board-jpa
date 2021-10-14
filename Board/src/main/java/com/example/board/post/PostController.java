package com.example.board.post;

import javassist.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/posts")
public class PostController {

    PostService service;

    public PostController(PostService service) {
        this.service = service;
    }

    @GetMapping
    public List<PostDto> getPosts(){
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable Long id) throws NotFoundException {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto){
        return ResponseEntity.of(Optional.of(service.createPost(postDto)));
    }

    @PostMapping("/{id}")
    public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto) throws NotFoundException{
        return ResponseEntity.of(Optional.of(service.updatePost(postDto)));
    }
}
