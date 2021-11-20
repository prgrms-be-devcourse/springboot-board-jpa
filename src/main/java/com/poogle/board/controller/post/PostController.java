package com.poogle.board.controller.post;

import com.poogle.board.controller.ApiResult;
import com.poogle.board.converter.Converter;
import com.poogle.board.error.NotFoundException;
import com.poogle.board.model.post.Post;
import com.poogle.board.service.post.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import static com.poogle.board.controller.ApiResult.ok;

@RestController
@RequestMapping("api/posts")
public class PostController {

    private final PostService postService;
    private final Converter converter;

    public PostController(PostService postService, Converter converter) {
        this.postService = postService;
        this.converter = converter;
    }

    @PostMapping
    public ApiResult<PostResponse> posting(@RequestBody PostRequest request) {
        return ok(converter.convertPostDto(postService.write(request.newPost())));
    }

    @GetMapping
    public ApiResult<Page<PostResponse>> list(Pageable pageable) {
        return ok(postService.findPosts(pageable)
                .map(converter::convertPostDto));
    }

    @GetMapping("/{id}")
    public ApiResult<PostResponse> getPost(@PathVariable Long id) {
        return ok(postService.findPost(id)
                .map(converter::convertPostDto)
                .orElseThrow(() -> new NotFoundException(Post.class, id)));
    }

    @PutMapping("/{id}")
    public ApiResult<PostResponse> update(
            @PathVariable Long id,
            @RequestBody PostRequest request) {
        return ok(converter.convertPostDto(postService.modify(id, request)));
    }
}
