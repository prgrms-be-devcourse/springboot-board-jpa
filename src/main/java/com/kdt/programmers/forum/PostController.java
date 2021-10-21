package com.kdt.programmers.forum;

import com.kdt.programmers.forum.exception.NotFoundException;
import com.kdt.programmers.forum.exception.PostNotFoundException;
import com.kdt.programmers.forum.transfer.PageWrapper;
import com.kdt.programmers.forum.transfer.request.PostRequest;
import com.kdt.programmers.forum.transfer.response.ApiResponse;
import com.kdt.programmers.forum.transfer.PostWrapper;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/posts")
@AllArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping
    public ApiResponse<PageWrapper> getPosts(Pageable pageable) {
        Page<PostWrapper> posts = postService.findPostsByPage(pageable);
        PageWrapper dto = new PageWrapper(posts.getContent(), posts.getTotalPages(), posts.getTotalElements());
        return ApiResponse.response(dto);
    }

    @GetMapping("/{postId}")
    public ApiResponse<PostWrapper> getPost(@PathVariable Long postId) {
        try {
            PostWrapper post = postService.findPostById(postId);
            return ApiResponse.response(post);
        } catch (PostNotFoundException e) {
            throw new NotFoundException("requested post was not found", e);
        }
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ApiResponse<PostWrapper> savePost(@RequestBody final PostRequest postRequest) {
        PostWrapper post = postService.savePost(postRequest);
        return ApiResponse.response(post);
    }

    @PutMapping("/{postId}")
    public ApiResponse<PostWrapper> updatePost(
        @PathVariable Long postId,
        @RequestBody final PostRequest postRequest
    ) {
        PostWrapper post = postService.updatePost(postId, postRequest);
        return ApiResponse.response(post);
    }
}
