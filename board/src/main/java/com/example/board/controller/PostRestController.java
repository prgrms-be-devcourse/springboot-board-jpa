package com.example.board.controller;
import com.example.board.dto.PostDto;
import com.example.board.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostRestController {

    private final PostService service;

    @GetMapping
    public ResponseEntity<Page<PostDto.Response>> findAll(@PageableDefault(page=0,size = 10) Pageable pageable) {
        Page<PostDto.Response> responses = service.findAll(pageable);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDto.Response> findById(@PathVariable Long id) {
        PostDto.Response response = service.findById(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<PostDto.Response> save(@RequestBody PostDto.Request request) {
        PostDto.Response response = service.save(request);
        return ResponseEntity.status(201).body(response);
    }

    @PostMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id,
                                                   @RequestBody PostDto.Request request) {
        service.update(id, request);
        return ResponseEntity.ok().build();
    }
}
