package com.pppp0722.boardjpa.web.api;

import com.pppp0722.boardjpa.service.post.PostService;
import com.pppp0722.boardjpa.web.dto.PostRequestDto;
import com.pppp0722.boardjpa.web.dto.PostResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PostApiController {

    private final PostService postService;

    public PostApiController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("/posts")
    public ResponseEntity<PostResponseDto> create(@RequestBody PostRequestDto postRequestDto) {
        PostResponseDto postResponseDto = postService.save(postRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(postResponseDto);
    }

    @GetMapping("/posts/{id}")
    public ResponseEntity<PostResponseDto> getById(@PathVariable Long id) {
        PostResponseDto postResponseDto = postService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(postResponseDto);
    }

    @GetMapping("/posts")
    public ResponseEntity<Page<PostResponseDto>> getAll(Pageable pageable) {
        Page<PostResponseDto> postResponseDtos = postService.findAll(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(postResponseDtos);
    }

    @PutMapping("/posts/{id}")
    public ResponseEntity<PostResponseDto> update(@PathVariable Long id,
        @RequestBody PostRequestDto postRequestDto) {
        PostResponseDto postResponseDto = postService.update(id, postRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body(postResponseDto);
    }

    @GetMapping("/posts/user/{id}")
    public ResponseEntity<Page<PostResponseDto>> getByUserId(@PathVariable Long id,
        Pageable pageable) {
        Page<PostResponseDto> postResponseDtos = postService.findByUserId(id, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(postResponseDtos);
    }

    @DeleteMapping("/posts/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        postService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
