package com.misson.jpa_board.controller;

import com.misson.jpa_board.dto.PostCreateRequest;
import com.misson.jpa_board.dto.PostDto;
import com.misson.jpa_board.service.PostService;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.NoSuchElementException;

@Slf4j
@RequestMapping("/posts")
@RestController
public class PostRestController {

    private final PostService postService;

    public PostRestController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    public ResponseEntity<Long> save(@RequestBody PostCreateRequest PostCreateRequest) throws NoSuchElementException {
        log.info("게시글 작성");
        Long savedId = postService.save(PostCreateRequest);
        URI uri = URI.create("/posts");
        return ResponseEntity.created(uri).body(savedId);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostDto> changePost(@PathVariable Long id, @RequestBody PostDto postDto) {
        log.info("게시글 수정");
        return ResponseEntity.ok(postService.postChange(id, postDto));
    }

    @GetMapping
    public ResponseEntity<Page<PostDto>> getPage(Pageable pageable) {
        log.info("게시글 페이지 조회");
        return ResponseEntity.ok(postService.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDto> postFindById(@PathVariable(value = "id") Long id) throws NotFoundException {
        log.info("게시글 단건 조회");
        return ResponseEntity.ok(postService.postFindById(id));
    }

}
