package com.jpaboard.post.ui;

import com.jpaboard.post.application.PostService;
import com.jpaboard.post.ui.dto.PageParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/posts")
public class PostRestController {
    private final PostService postService;

    public PostRestController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public Page<PostCommon.Response> findPostAll(@RequestBody PageParam param) {
        Pageable pageable = PageRequest.of(param.page(), param.size());

        return postService.findPostAll(pageable);
    }

    @GetMapping("/{id}")
    public PostCommon.Response findPost(@PathVariable long id) {
        return postService.findPost(id);
    }

    @PostMapping
    public PostCreate.Response createPost(@RequestBody PostCreate.Request request) {
        return postService.createPost(request);
    }

    @PatchMapping("/{id}")
    public PostUpdate.Response updatePost(@PathVariable long id,
                                          @RequestBody PostUpdate.Request postUpdateRequest) {
        return postService.updatePost(id, postUpdateRequest);
    }
}
