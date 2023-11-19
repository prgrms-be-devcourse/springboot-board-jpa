package com.example.board.controller;
import com.example.board.dto.PostDto;
import com.example.board.service.PostService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostRestController {

    private final PostService service;

    @GetMapping("/")
    public ResponseEntity<List<PostDto.Response>> findAll() {
        response = service.findAll();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDto.Response> findById(@PathVariable Long id) {
        service.findById();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/")
    public ResponseEntity<PostDto.Response> save(@RequestBody PostDto.Request request) {
        response = service.save(request);
        return ResponseEntity.status(201).body(response);
    }

    @PostMapping("/{id}")
    public ResponseEntity<PostDto.Response> update(@PathVariable Long id,
                                                   @RequestBody PostDto.Request request) {
        response = service.update();
        return ResponseEntity.ok(response);
    }
}
