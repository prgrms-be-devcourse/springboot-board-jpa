package com.kdt.boardMission.controller;

import com.kdt.boardMission.dto.CreatePostDto;
import com.kdt.boardMission.dto.PostDto;
import com.kdt.boardMission.dto.UserDto;
import com.kdt.boardMission.service.PostService;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class PostRestController {

    private final PostService postService;

    @GetMapping("/posts")
    public ResponseEntity<Page<PostDto>> findAll(Pageable pageable) {
        Page<PostDto> postDtoPage = postService.findAll(pageable);
        return ResponseEntity.ok(postDtoPage);
    }

    @GetMapping("/posts/{id}")
    public ResponseEntity<PostDto> findOne(@PathVariable("id") long id) throws NotFoundException {
        PostDto postDto = postService.findById(id);
        return ResponseEntity.ok(postDto);
    }

    @PostMapping("/posts")
    public ResponseEntity<Long> doPost(@RequestBody CreatePostDto createPostDto) throws NotFoundException {
        PostDto postDto = createPostDto.getPostDto();
        UserDto userDto = createPostDto.getUserDto();

        long postId = postService.savePost(postDto, userDto);
        return ResponseEntity.ok(postId);
    }

    @PostMapping("/posts/{id}")
    public ResponseEntity<PostDto> editPost(@PathVariable("id") Long id,
                                         @RequestBody PostDto postDto) throws NotFoundException {
        postDto.setId(id);
        postService.updatePost(postDto);
        return ResponseEntity.ok(postDto);
    }
}
