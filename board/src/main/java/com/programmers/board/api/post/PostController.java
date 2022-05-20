package com.programmers.board.api.post;

import com.programmers.board.api.common.ApiResponse;
import com.programmers.board.core.post.application.PostService;
import com.programmers.board.core.post.application.dto.CreateRequestPost;
import com.programmers.board.core.post.application.dto.ResponsePost;
import com.programmers.board.core.post.application.dto.UpdateRequestPost;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RestControllerAdvice
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    @GetMapping
    public ApiResponse<Page<ResponsePost>> getAll(Pageable pageable){
        return ApiResponse.ok(postService.findPosts(pageable));
    }

    @PostMapping
    public ApiResponse<ResponsePost> save(@RequestBody CreateRequestPost createRequestPost){
        return ApiResponse.ok(postService.save(createRequestPost));
    }

    @GetMapping("/{id}")
    public ApiResponse<ResponsePost> getOne(@PathVariable Long id){
        return ApiResponse.ok(postService.findOne(id));
    }

    @PutMapping("/{id}")
    public ApiResponse<ResponsePost> update(
            @PathVariable Long id,
            @RequestBody UpdateRequestPost postDto
    ){
        return ApiResponse.ok(postService.update(id, postDto));
    }

}
