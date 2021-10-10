package org.programmers.project_board.controller;

import lombok.RequiredArgsConstructor;
import org.programmers.project_board.dto.PostDto;
import org.programmers.project_board.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/posts")
@RequiredArgsConstructor
@RestController
public class PostController {

    private final PostService postService;

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public List<PostDto> getAllPosts() {
        return postService.getAllPosts();
    }

    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PostDto getPost(@PathVariable Long id) {
        return postService.getPost(id);
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public Long createPost(@RequestBody PostDto postDto) {
        return postService.savePost(postDto);
    }

}
