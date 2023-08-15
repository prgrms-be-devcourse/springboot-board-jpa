package com.jpaboard.post.ui;

import com.jpaboard.post.application.PostService;
import com.jpaboard.post.ui.dto.PageParam;
import com.jpaboard.post.ui.dto.PostDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/posts")
public class PostRestController {
    private final PostService postService;

    public PostRestController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public Page<PostDto.Response> findPostAll(@RequestBody PageParam param) {
        int page = param.page();
        int size = param.size();
        return postService.findPostAll(PageRequest.of(page, size));
    }

    @GetMapping("/{id}")
    public PostDto.Response findPost(@PathVariable long id) {
        PostDto.Response response = postService.findPost(id);
        return response;
    }

    @PostMapping
    public Long createPost(@RequestBody PostDto.Request request) {
        Long postId = postService.createPost(request);
        return postId;
    }

    @PatchMapping("/{id}")
    public Long updatePost(@PathVariable long id,
                           @RequestBody PostDto.PostUpdateRequest postUpdateRequest) {
        Long postId = postService.updatePost(id, postUpdateRequest);
        return postId;
    }
}
