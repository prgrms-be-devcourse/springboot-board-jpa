package com.example.board.domain.post.api;

import com.example.board.domain.post.service.PostService;
import com.example.board.global.dto.BaseResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static com.example.board.domain.post.dto.PostDto.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    @GetMapping
    public BaseResponse<Page<SinglePostResponse>> pagingPosts(Pageable pageable) {
        return BaseResponse.of(HttpStatus.OK,
                "response.paging-posts.title",
                "response.paging-posts.content",
                postService.pagingPost(pageable));
    }

    @GetMapping("/{id}")
    public BaseResponse<SinglePostResponse> getSinglePost(@PathVariable(name = "id") long postId) {
        return BaseResponse.of(HttpStatus.OK,
                "response.get-single-post.title",
                "response.get-single-post.content",
                postService.getPost(postId));
    }

    @PostMapping
    public BaseResponse<SinglePostResponse> enrollPost(@RequestBody @Valid CreatePostRequest createPostRequest) {
        return BaseResponse.of(HttpStatus.OK,
                "response.enroll-post.title",
                "response.enroll-post.content",
                postService.post(createPostRequest));
    }

    @PatchMapping("/{id}")
    public BaseResponse<SinglePostResponse> updatePost(@PathVariable(name = "id") long postId,
                                                       @RequestBody @Valid UpdatePostRequest updatePostRequest
    ) {
        return BaseResponse.of(HttpStatus.OK,
                "response.update-post.title",
                "response.update-post.content",
                postService.updatePost(postId, updatePostRequest));
    }
}
