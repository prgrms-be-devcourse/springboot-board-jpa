package com.hyunji.jpaboard.web.controller;

import com.hyunji.jpaboard.domain.post.domain.Post;
import com.hyunji.jpaboard.domain.post.service.PostService;
import com.hyunji.jpaboard.domain.user.domain.User;
import com.hyunji.jpaboard.domain.user.domain.UserRepository;
import com.hyunji.jpaboard.web.dto.PostDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/posts")
@RestController
public class PostController {

    private final PostService postService;
    private final UserRepository userRepository;

    @GetMapping
    public ResponseEntity<Page<PostDto.Response>> getPages(@RequestParam("pageNum") int pageNum) {
        Page<PostDto.Response> responsePage = postService.findPage(pageNum).map(PostDto.Response::new);
        return ResponseEntity.ok(responsePage);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDto.Response> findPostByIdWithUser(@PathVariable("id") Long id) {
        Post post = postService.findPostByIdWithUser(id);
        PostDto.Response response = new PostDto.Response(post);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<String> register(@RequestBody @Validated PostDto.Request postDto) {
        User user = userRepository.findUserByName(postDto.getUsername()).orElseThrow(IllegalArgumentException::new);
        postService.save(postDto.toEntity(user));
        return ResponseEntity.ok("post registered");
    }

    @PostMapping("/{id}")
    public ResponseEntity<String> update(
            @PathVariable("id") Long id,
            @RequestBody @Validated PostDto.PostUpdateRequest postDto) {
        Post findPost = postService.findPostByIdWithUser(id);
        findPost.change(postDto.getTitle(), postDto.getContent());
        postService.save(findPost);
        return ResponseEntity.ok("post updated");
    }
}
