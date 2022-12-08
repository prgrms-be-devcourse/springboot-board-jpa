package com.prgrms.work.controller;

import com.prgrms.work.controller.dto.ApiResponse;
import com.prgrms.work.controller.dto.PostRequest;
import com.prgrms.work.controller.dto.PostResponse;
import com.prgrms.work.controller.dto.PostResponse.Posts;
import com.prgrms.work.post.domain.Post;
import com.prgrms.work.post.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/posts")
public class PostRestController {

    private final PostService postService;

    public PostRestController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public ApiResponse<Page<Posts>> getPosts(Pageable pageable) {
        Page<Posts> posts = this.postService.getPosts(pageable)
            .map(Posts::of);

        return ApiResponse.ok(posts);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<Object> write(@RequestBody @Validated PostRequest.PostCreateDto createDto) {
        return ApiResponse.create(this.postService.create(createDto.toEntity()).getId());
    }

    @GetMapping("/{id}")
    public ApiResponse<PostResponse.PostDetail> getPost(@PathVariable Long id) {
        Post findPost = this.postService.getPost(id);

        return ApiResponse.ok(PostResponse.PostDetail.of(findPost));
    }

    @PutMapping("/{id}")
    public void updatePost(@PathVariable Long id, @RequestBody PostRequest.UpdateDto updateDto) {
        this.postService.update(id, updateDto.getTitle(), updateDto.getContent());
    }

    @DeleteMapping("/{id}")
    public void deletePost(@PathVariable Long id) {
        this.postService.delete(id);
    }

}
