package com.devco.jpaproject.controller;

import com.devco.jpaproject.controller.dto.PostDeleteRequestDto;
import com.devco.jpaproject.controller.dto.PostRequestDto;
import com.devco.jpaproject.controller.dto.PostResponseDto;
import com.devco.jpaproject.controller.dto.PostUpdateResquestDto;
import com.devco.jpaproject.exception.PostNotFoundException;
import com.devco.jpaproject.exception.UserAndPostNotMatchException;
import com.devco.jpaproject.exception.UserNotFoundException;
import com.devco.jpaproject.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class PostController {
    private final PostService postService;

    @GetMapping("/posts")
    public ApiResponse<List<PostResponseDto>> findAll(){
        var allPosts = postService.findAll();
        return ApiResponse.ok(allPosts);
    }

    @GetMapping("/posts/pages")
    public ApiResponse<Page<PostResponseDto>> findAllByPages(Pageable pageable){
        var allPosts = postService.findAllByPages(pageable);
        return ApiResponse.ok(allPosts);
    }

    @GetMapping("/post/{id}")
    public ApiResponse<PostResponseDto> findById(@PathVariable Long id) throws PostNotFoundException {
        var postResponseDto = postService.findById(id);
        return ApiResponse.ok(postResponseDto);
    }

    @PostMapping("/post")
    public ApiResponse<Long> insert(@Valid @RequestBody PostRequestDto dto) throws UserNotFoundException {
        Long postId = postService.insert(dto);

        return ApiResponse.ok(postId);
    }

    @PatchMapping("/post")
    public ResponseEntity update(@Valid @RequestBody PostUpdateResquestDto dto) throws PostNotFoundException {
        postService.update(dto);

        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping("/post")
    public ResponseEntity deleteOne(@Valid @RequestBody PostDeleteRequestDto dto)
            throws PostNotFoundException, UserAndPostNotMatchException {
        postService.deleteOne(dto);

        return new ResponseEntity(HttpStatus.OK);
    }

}
